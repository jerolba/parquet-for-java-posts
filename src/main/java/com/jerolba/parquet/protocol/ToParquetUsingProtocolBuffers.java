package com.jerolba.parquet.protocol;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetFileWriter.Mode;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;
import org.apache.parquet.proto.ProtoParquetWriter;
import org.apache.parquet.proto.ProtoWriteSupport;

import com.jerolba.parquet.SampleDataFactory;
import com.jerolba.parquet.SampleDataFactory.Attr;
import com.jerolba.parquet.SampleDataFactory.Org;
import com.jerolba.xbuffers.protocol.Organization;
import com.jerolba.xbuffers.protocol.Organization.Attribute;
import com.jerolba.xbuffers.protocol.Organization.OrganizationType;

public class ToParquetUsingProtocolBuffers {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new SampleDataFactory().getOrganizations(400_000);

        Path path = new Path("/tmp/organizations.parquet");
        OutputFile outputFile = HadoopOutputFile.fromPath(path, new Configuration());
        try (ParquetWriter<Organization> writer = ProtoParquetWriter.<Organization>builder(outputFile)
                .withMessage(Organization.class)
                .withWriteMode(Mode.OVERWRITE)
                .config(ProtoWriteSupport.PB_SPECS_COMPLIANT_WRITE, "true")
                .build()) {
            for (Org org : organizations) {
                var organizationBuilder = Organization.newBuilder()
                        .setName(org.name())
                        .setCategory(org.category())
                        .setCountry(org.country())
                        .setType(OrganizationType.forNumber(org.type().ordinal()));
                for (Attr attr : org.attributes()) {
                    var attribute = Attribute.newBuilder()
                            .setId(attr.id())
                            .setQuantity(attr.quantity())
                            .setAmount(attr.amount())
                            .setActive(attr.active())
                            .setPercent(attr.percent())
                            .setSize(attr.size())
                            .build();
                    organizationBuilder.addAttributes(attribute);
                }
                writer.write(organizationBuilder.build());
            }
        }
    }

}

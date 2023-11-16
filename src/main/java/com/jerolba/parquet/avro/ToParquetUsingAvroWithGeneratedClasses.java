package com.jerolba.parquet.avro;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroWriteSupport;
import org.apache.parquet.hadoop.ParquetFileWriter.Mode;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;

import com.jerolba.parquet.SampleDataFactory;
import com.jerolba.parquet.SampleDataFactory.Org;
import com.jerolba.xbuffers.avro.Attribute;
import com.jerolba.xbuffers.avro.Organization;
import com.jerolba.xbuffers.avro.OrganizationType;

public class ToParquetUsingAvroWithGeneratedClasses {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new SampleDataFactory().getOrganizations(400_000);

        Path path = new Path("/tmp/organizations.parquet");
        OutputFile outputFile = HadoopOutputFile.fromPath(path, new Configuration());
        try (ParquetWriter<Organization> writer = AvroParquetWriter.<Organization>builder(outputFile)
                .withSchema(new Organization().getSchema())
                .withWriteMode(Mode.OVERWRITE)
                .config(AvroWriteSupport.WRITE_OLD_LIST_STRUCTURE, "false")
                .build()) {
            for (var org : organizations) {
                List<Attribute> attrs = org.attributes().stream()
                        .map(a -> Attribute.newBuilder()
                                .setId(a.id())
                                .setQuantity(a.quantity())
                                .setAmount(a.amount())
                                .setSize(a.size())
                                .setPercent(a.percent())
                                .setActive(a.active())
                                .build())
                        .toList();
                Organization organization = Organization.newBuilder()
                        .setName(org.name())
                        .setCategory(org.category())
                        .setCountry(org.country())
                        .setOrganizationType(OrganizationType.valueOf(org.type().name()))
                        .setAttributes(attrs)
                        .build();
                writer.write(organization);
            }
        }
    }

}

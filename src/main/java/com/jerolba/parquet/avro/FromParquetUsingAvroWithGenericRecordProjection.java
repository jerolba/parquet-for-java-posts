package com.jerolba.parquet.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;

import com.jerolba.parquet.SampleDataFactory.Org;

public class FromParquetUsingAvroWithGenericRecordProjection {

    public static void main(String[] args) throws IOException {
        Schema orgsSchema = SchemaBuilder.record("Organizations")
                .fields()
                .requiredString("name")
                .requiredString("category")
                .requiredString("country")
                .endRecord();

        Path path = new Path("/tmp/organizations_avro.parquet");
        InputFile inputFile = HadoopInputFile.fromPath(path, new Configuration());
        Configuration configuration = new Configuration();
        configuration.set(AvroReadSupport.AVRO_REQUESTED_PROJECTION, orgsSchema.toString());
        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(inputFile)
                .withConf(configuration)
                .build()) {
            List<Org> organizations = new ArrayList<>();
            GenericRecord record = null;
            while ((record = reader.read()) != null) {
                Utf8 name = (Utf8) record.get("name");
                Utf8 category = (Utf8) record.get("category");
                Utf8 country = (Utf8) record.get("country");
                organizations.add(new Org(name.toString(), category.toString(), country.toString(), null, null));
            }
            System.out.println(organizations.size());
        }
    }

}

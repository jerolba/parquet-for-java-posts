package com.jerolba.parquet.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;

import com.jerolba.parquet.SampleDataFactory.Attr;
import com.jerolba.parquet.SampleDataFactory.Org;
import com.jerolba.parquet.SampleDataFactory.Type;

public class FromParquetUsingAvroWithGenericRecord {

    public static void main(String[] args) throws IOException {
        Path path = new Path("/tmp/organizations_avro.parquet");
        InputFile inputFile = HadoopInputFile.fromPath(path, new Configuration());
        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(inputFile).build()) {
            List<Org> organizations = new ArrayList<>();
            GenericRecord record = null;
            while ((record = reader.read()) != null) {
                List<GenericRecord> attrsRecords = (List<GenericRecord>) record.get("attributes");
                var attrs = attrsRecords.stream().map(attr -> new Attr(attr.get("id").toString(),
                        ((Integer) attr.get("quantity")).byteValue(),
                        ((Integer) attr.get("amount")).byteValue(),
                        (boolean) attr.get("active"),
                        (double) attr.get("percent"),
                        ((Integer) attr.get("size")).shortValue())).toList();
                Utf8 name = (Utf8) record.get("name");
                Utf8 category = (Utf8) record.get("category");
                Utf8 country = (Utf8) record.get("country");
                Type type = Type.valueOf(record.get("organizationType").toString());
                organizations.add(new Org(name.toString(), category.toString(), country.toString(), type, attrs));
            }
            System.out.println(organizations.size());
        }
    }

}

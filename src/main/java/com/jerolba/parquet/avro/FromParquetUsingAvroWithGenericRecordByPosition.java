package com.jerolba.parquet.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;

import com.jerolba.parquet.SampleDataFactory;
import com.jerolba.parquet.SampleDataFactory.Attr;
import com.jerolba.parquet.SampleDataFactory.Org;
import com.jerolba.parquet.SampleDataFactory.Type;

public class FromParquetUsingAvroWithGenericRecordByPosition {

    public static void main(String[] args) throws IOException {
        Path path = new Path("/tmp/organizations.parquet");
        InputFile inputFile = HadoopInputFile.fromPath(path, new Configuration());
        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(inputFile).build()) {
            List<Org> organizations = new ArrayList<>();
            GenericRecord record = reader.read();

            Schema attributes = record.getSchema().getField("attributes").schema().getElementType();
            int idPos = attributes.getField("id").pos();
            int quantityPos = attributes.getField("quantity").pos();
            int amountPos = attributes.getField("amount").pos();
            int activePos = attributes.getField("active").pos();
            int percentPos = attributes.getField("percent").pos();
            int sizePos = attributes.getField("size").pos();
            Schema orgs = record.getSchema();
            int namePos = orgs.getField("name").pos();
            int categoryPos = orgs.getField("category").pos();
            int countryPos = orgs.getField("country").pos();
            int organizationTypePos = orgs.getField("organizationType").pos();

            while (record != null) {
                List<GenericRecord> attrsRecords = (List<GenericRecord>) record.get("attributes");
                var attrs = attrsRecords.stream().map(attr -> new Attr(attr.get(idPos).toString(),
                        ((Integer) attr.get(quantityPos)).byteValue(),
                        ((Integer) attr.get(amountPos)).byteValue(),
                        (boolean) attr.get(activePos),
                        (double) attr.get(percentPos),
                        ((Integer) attr.get(sizePos)).shortValue())).toList();
                Utf8 name = (Utf8) record.get(namePos);
                Utf8 category = (Utf8) record.get(categoryPos);
                Utf8 country = (Utf8) record.get(countryPos);
                Type type = Type.valueOf(record.get(organizationTypePos).toString());
                organizations.add(new Org(name.toString(), category.toString(), country.toString(), type, attrs));
                record = reader.read();
            }
            System.out.println(organizations.size());
        }
    }

}

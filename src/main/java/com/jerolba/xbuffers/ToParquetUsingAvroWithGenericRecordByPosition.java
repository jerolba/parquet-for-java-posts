package com.jerolba.xbuffers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroWriteSupport;
import org.apache.parquet.hadoop.ParquetFileWriter.Mode;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;

import com.jerolba.xbuffers.SampleDataFactory.Org;
import com.jerolba.xbuffers.SampleDataFactory.Type;

public class ToParquetUsingAvroWithGenericRecordByPosition {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new SampleDataFactory().getOrganizations(400_000);

        Schema attrSchema = SchemaBuilder.record("Attribute")
                .fields()
                .requiredString("id")
                .requiredInt("quantity")
                .requiredInt("amount")
                .requiredInt("size")
                .requiredDouble("percent")
                .requiredBoolean("active")
                .endRecord();
        var enumSymbols = Stream.of(Type.values()).map(Type::name).toArray(String[]::new);
        Schema orgsSchema = SchemaBuilder.record("Organizations")
                .fields()
                .requiredString("name")
                .requiredString("category")
                .requiredString("country")
                .name("organizationType").type().enumeration("organizationType").symbols(enumSymbols).noDefault()
                .name("attributes").type().array().items(attrSchema).noDefault()
                .endRecord();
        var typeField = orgsSchema.getField("organizationType").schema();
        EnumMap<Type, EnumSymbol> enums = new EnumMap<>(Type.class);
        enums.put(Type.BAR, new EnumSymbol(typeField, Type.BAR));
        enums.put(Type.BAZ, new EnumSymbol(typeField, Type.BAZ));
        enums.put(Type.FOO, new EnumSymbol(typeField, Type.FOO));

        int idPos = attrSchema.getField("id").pos();
        int quantityPos = attrSchema.getField("quantity").pos();
        int amountPos = attrSchema.getField("amount").pos();
        int activePos = attrSchema.getField("active").pos();
        int percentPos = attrSchema.getField("percent").pos();
        int sizePos = attrSchema.getField("size").pos();
        int namePos = orgsSchema.getField("name").pos();
        int categoryPos = orgsSchema.getField("category").pos();
        int countryPos = orgsSchema.getField("country").pos();
        int organizationTypePos = orgsSchema.getField("organizationType").pos();
        int attributesPos = orgsSchema.getField("attributes").pos();

        Path path = new Path("/tmp/organizations.parquet");
        OutputFile outputFile = HadoopOutputFile.fromPath(path, new Configuration());
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withSchema(orgsSchema)
                .withWriteMode(Mode.OVERWRITE)
                .config(AvroWriteSupport.WRITE_OLD_LIST_STRUCTURE, "false")
                .build()) {
            for (var org : organizations) {
                List<GenericRecord> attrs = new ArrayList<>();
                for (var attr : org.attributes()) {
                    GenericRecord attrRecord = new GenericData.Record(attrSchema);
                    attrRecord.put(idPos, attr.id());
                    attrRecord.put(quantityPos, attr.quantity());
                    attrRecord.put(amountPos, attr.amount());
                    attrRecord.put(sizePos, attr.size());
                    attrRecord.put(percentPos, attr.percent());
                    attrRecord.put(activePos, attr.active());
                    attrs.add(attrRecord);
                }
                GenericRecord orgRecord = new GenericData.Record(orgsSchema);
                orgRecord.put(namePos, org.name());
                orgRecord.put(categoryPos, org.category());
                orgRecord.put(countryPos, org.country());
                orgRecord.put(organizationTypePos, enums.get(org.type()));
                orgRecord.put(attributesPos, attrs);
                writer.write(orgRecord);
            }
        }
    }
}

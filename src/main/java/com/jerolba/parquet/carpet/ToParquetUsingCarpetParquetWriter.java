package com.jerolba.parquet.carpet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.parquet.hadoop.ParquetFileWriter.Mode;

import com.jerolba.carpet.CarpetParquetWriter;
import com.jerolba.carpet.io.FileSystemOutputFile;
import com.jerolba.parquet.SampleDataFactory;
import com.jerolba.parquet.SampleDataFactory.Org;

public class ToParquetUsingCarpetParquetWriter {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new SampleDataFactory().getOrganizations(400_000);
        File file = new File("/tmp/organizations.parquet");
        try (var writer = CarpetParquetWriter.builder(new FileSystemOutputFile(file), Org.class)
                .withWriteMode(Mode.OVERWRITE)
                .build()) {
            for (Org org : organizations) {
                writer.write(org);
            }
        }
    }

}

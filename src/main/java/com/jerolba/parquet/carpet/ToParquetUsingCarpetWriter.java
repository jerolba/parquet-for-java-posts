package com.jerolba.parquet.carpet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.jerolba.carpet.CarpetWriter;
import com.jerolba.parquet.SampleDataFactory;
import com.jerolba.parquet.SampleDataFactory.Org;

public class ToParquetUsingCarpetWriter {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new SampleDataFactory().getOrganizations(400_000);
        try (var fos = new FileOutputStream("/tmp/organizations.parquet")) {
            try (var writer = new CarpetWriter<>(fos, Org.class)) {
                writer.write(organizations);
            }
        }
    }

}

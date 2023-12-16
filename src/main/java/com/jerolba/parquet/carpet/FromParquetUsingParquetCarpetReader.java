package com.jerolba.parquet.carpet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.io.InputFile;

import com.jerolba.carpet.CarpetParquetReader;
import com.jerolba.carpet.io.FileSystemInputFile;
import com.jerolba.parquet.SampleDataFactory.Org;

public class FromParquetUsingParquetCarpetReader {

    public static void main(String[] args) throws IOException {
        InputFile inputFile = new FileSystemInputFile(new File("/tmp/organizations.parquet"));
        try (ParquetReader<Org> reader = CarpetParquetReader.builder(inputFile, Org.class).build()) {
            List<Org> organizations = new ArrayList<>();
            Org next = null;
            while ((next = reader.read()) != null) {
                organizations.add(next);
            }
            System.out.println(organizations.size());
        }
    }

}

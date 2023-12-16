package com.jerolba.parquet.carpet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jerolba.carpet.CarpetReader;
import com.jerolba.parquet.SampleDataFactory.Org;

public class FromParquetUsingCarpetReader {

    public static void main(String[] args) throws IOException {
        List<Org> organizations = new CarpetReader<>(new File("/tmp/organizations.parquet"), Org.class).toList();
        System.out.println(organizations.size());
    }

}

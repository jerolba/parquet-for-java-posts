package com.jerolba.parquet.carpet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jerolba.carpet.CarpetReader;
import com.jerolba.parquet.SampleDataFactory.Type;

public class FromParquetUsingCarpetReaderProjection {

    public static void main(String[] args) throws IOException {

        record OrgProjection(String name, String category, String country, Type type) {
        }
        List<OrgProjection> organizations = new CarpetReader<>(new File("/tmp/organizations.parquet"),
                OrgProjection.class).toList();
        System.out.println(organizations.size());
    }

}

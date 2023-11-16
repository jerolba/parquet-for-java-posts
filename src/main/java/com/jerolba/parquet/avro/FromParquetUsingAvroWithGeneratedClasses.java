package com.jerolba.parquet.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;

import com.jerolba.xbuffers.avro.Organization;

public class FromParquetUsingAvroWithGeneratedClasses {

    public static void main(String[] args) throws IOException {
        Path path = new Path("/tmp/organizations.parquet");
        InputFile inputFile = HadoopInputFile.fromPath(path, new Configuration());
        try (ParquetReader<Organization> reader = AvroParquetReader.<Organization>builder(inputFile).build()) {
            List<Organization> organizations = new ArrayList<>();
            Organization next = null;
            while ((next = reader.read()) != null) {
                organizations.add(next);
            }
            System.out.println(organizations.size());
        }
    }

}

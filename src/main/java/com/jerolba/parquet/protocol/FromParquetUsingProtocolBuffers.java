package com.jerolba.parquet.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.proto.ProtoParquetReader;

import com.jerolba.xbuffers.protocol.Organization;

public class FromParquetUsingProtocolBuffers {

    public static void main(String[] args) throws IOException {
        Path path = new Path("/tmp/organizations.parquet");
        InputFile inputFile = HadoopInputFile.fromPath(path, new Configuration());
        try (ParquetReader<Organization.Builder> reader = ProtoParquetReader.<Organization.Builder>builder(inputFile)
                .build()) {
            List<Organization> organizations = new ArrayList<>();
            Organization.Builder next = null;
            while ((next = reader.read()) != null) {
                organizations.add(next.build());
            }
            System.out.println(organizations.size());
        }
    }

}

Protocol buffers:

```bash
docker run --rm -v $(pwd):$(pwd) -w $(pwd) znly/protoc --java_out=./src/main/java -I=./src/main/resources ./src/main/resources/organizations.proto
```

Avro:

```bash
java -jar avro-tools-1.11.1.jar compile schema ./src/main/resources/organizations.avsc ./src/main/java/
```

```bash
docker run --rm -v $(pwd)/src:/avro/src kpnnv/avro-tools:1.11.1 compile schema /avro/src/main/resources/organizations.avsc /avro/src/main/java/
```

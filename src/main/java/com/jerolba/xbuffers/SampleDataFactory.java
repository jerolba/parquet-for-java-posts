package com.jerolba.xbuffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javafaker.Faker;

public class SampleDataFactory {

    public static record Org(String name, String category, String country, Type type, List<Attr> attributes) {
    }

    public static record Attr(String id, byte quantity, byte amount, boolean active, double percent, short size) {
    }

    public static enum Type {
        FOO, BAR, BAZ
    }

    private final Random rnd = new Random(1);

    private final Faker faker = new Faker(rnd);
    private final List<String> ids;
    private final Type[] types = Type.values();

    public SampleDataFactory() {
        ids = IntStream.range(0, 400)
                .mapToObj(i -> faker.code().imei() + "-" + faker.code().ean8())
                .collect(Collectors.toList());
    }

    public List<Org> getOrganizations(int number) {
        List<Org> organizations = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            int numberAttrs = rnd.nextInt(30) + 40;
            organizations.add(getOrganization(numberAttrs));
        }
        return organizations;
    }

    public Org getOrganization(int numberAttrs) {
        List<Attr> attrs = new ArrayList<>(numberAttrs);
        for (int j = 0; j < numberAttrs; j++) {
            var id = ids.get(rnd.nextInt(ids.size()));
            var quantity = (byte) rnd.nextInt(128);
            var amount = (byte) rnd.nextInt(128);
            var active = rnd.nextBoolean();
            var percent = rnd.nextDouble() * 100;
            var size = (short) rnd.nextInt(65535);
            attrs.add(new Attr(id, quantity, amount, active, percent, size));
        }
        var name = faker.company().name();
        var category = faker.company().industry();
        var country = faker.country().name();
        var type = types[rnd.nextInt(3)];
        return new Org(name, category, country, type, attrs);
    }

}

package com.auction.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Khong tim thay file config ben client");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            System.err.println("Loi khi tai file cau hinh");
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        String value = get(key);
        if (value == null) {
            throw new IllegalArgumentException("Khong tim thay cau hinh Client cho key: " + key);
        }
        return Integer.parseInt(value.trim());
    }
}

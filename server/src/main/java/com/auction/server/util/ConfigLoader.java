package com.auction.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Ko tim thay file cau hinh ben server");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        String envKey = key.toUpperCase().replace('.', '_');

        String envValue = System.getenv(envKey);
        if (envValue != null) {
            return envValue;
        }

        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        String value = get(key);
        if (value == null) {
            throw new IllegalArgumentException("Không tìm thấy cấu hình cho key: " + key);
        }
        return Integer.parseInt(value.trim());
    }
}


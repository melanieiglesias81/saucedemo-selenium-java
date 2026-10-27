package com.saucedemo.config;

import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties; any value can be overridden with -Dkey=value. */
public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) PROPS.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private Config() {}

    public static String get(String key) {
        String override = System.getProperty(key);
        return (override != null && !override.isBlank() && !override.startsWith("${"))
                ? override : PROPS.getProperty(key);
    }

    public static int timeout() { return Integer.parseInt(get("timeout")); }
    public static boolean headless() { return Boolean.parseBoolean(get("headless")); }
}

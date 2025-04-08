package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream(HexaConstants.DB_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Database Connection Failed!", e);
        }
    }

    public static String getDbUrl() {
        return props.getProperty(HexaConstants.DB_URL);
    }

    public static String getUser() {
        return props.getProperty(HexaConstants.DB_USER);
    }

    public static String getPassword() {
        return props.getProperty(HexaConstants.DB_PASSWORD);
    }

    public static String getDriver() {
        return props.getProperty(HexaConstants.DB_DRIVER);
    }

    public static Properties getProps() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", getUser());
        connectionProps.put("password", getPassword());
        return connectionProps;
    }

}

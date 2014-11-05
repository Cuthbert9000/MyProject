package com.leilo.engine.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for dealing with Property Files
 */
public class PropertyUtils {

    private static PropertyUtils m_instance = null;

    protected PropertyUtils() {
        // Exists only to defeat instantiation.
    }
    public static PropertyUtils getInstance() {
        if(m_instance == null) {
            m_instance = new PropertyUtils();
        }
        return m_instance;
    }

    private Properties loadPropertyFile(String propertyFile) throws Exception {
        InputStream inputSteam = PropertyUtils.class.getResourceAsStream(propertyFile);
        Properties prop = new Properties();
        prop.load(inputSteam);
        inputSteam.close();
        return prop;
    }


}

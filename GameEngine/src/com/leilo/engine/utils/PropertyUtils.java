package com.leilo.engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    public Properties loadPropertyFile(File propertyFile) throws Exception {
        BufferedReader propertyReader = new BufferedReader(new FileReader(propertyFile));
        Properties prop = new Properties();
        prop.load(propertyReader);
        propertyReader.close();
        return prop;
    }


}

package codebaseline_proprocprac;

import java.util.*;
import java.io.*;

public class AppProperties {

    private static AppProperties self = null;
    private Properties properties;

    public static AppProperties getInstance() {
        if (self == null) {
            self = new AppProperties();
        }
        return self;
    }

    // Load the input file name Properties.prop
    private AppProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("Properties.prop"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read property file");
        }
    }

    public Properties getProperties() {
        return properties;
    }
}

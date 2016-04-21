package org.openmrs.module.rulesengine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RulesEngineProperties {

    public static final String RULES_ENGINE_PROP_FILE = "rulesengine-concept.properties";
    private static Log log = LogFactory.getLog(RulesEngineProperties.class);
    private static Properties properties;

    public static void load () {
        File file = new File(OpenmrsUtil.getApplicationDataDirectory(), RULES_ENGINE_PROP_FILE);
        if (!(file.exists() && file.canRead())) {
            log.warn(RULES_ENGINE_PROP_FILE + " does not exist or not readable.");
            return;
        }

        String propertyFile = file.getAbsolutePath();
        log.info(String.format("Reading bahmni properties from : %s", propertyFile));
        try {
            properties = new Properties(System.getProperties());
            properties.load(new FileInputStream(propertyFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void initialize(Properties props) {
        properties = props;
    }

}

package org.openmrs.module.rulesengine.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.extensions.BahmniExtensions;
import org.openmrs.module.rulesengine.engine.RulesEngineImpl;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RulesEngineProperties {

    public static final String RULES_ENGINE_PROP_FILE = "rulesengine-concept.properties";
    private static Log log = LogFactory.getLog(RulesEngineProperties.class);
    private static Properties properties;
    private static final String rulesEngineExtensionPath = "rulesengine"+ File.separator+"rulesengineextension";

    public static void load() {
        properties = new Properties(System.getProperties());
        File file = new File(OpenmrsUtil.getApplicationDataDirectory(), RULES_ENGINE_PROP_FILE);
        if (!(file.exists() && file.canRead())) {
            log.warn(RULES_ENGINE_PROP_FILE + " does not exist or not readable.");
            return;
        }

        String propertyFile = file.getAbsolutePath();
        log.info(String.format("Reading bahmni properties from : %s", propertyFile));
        try {
            properties.load(new FileInputStream(propertyFile));
            RulesEngineImpl engine=new RulesEngineImpl();
            String ruleNames= StringUtils.join(engine.getRulesRegistered(),'|');
            log.info("Following rules are added to rules engine: "+ruleNames);
            properties.setProperty("rules",ruleNames);
        } catch (Exception e) {
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

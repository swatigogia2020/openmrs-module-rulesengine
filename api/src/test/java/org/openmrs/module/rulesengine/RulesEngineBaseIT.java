package org.openmrs.module.rulesengine;

import org.junit.Before;
import org.junit.Ignore;
import org.openmrs.module.rulesengine.util.RulesEngineProperties;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import java.util.Properties;

@Ignore
public class RulesEngineBaseIT extends BaseModuleWebContextSensitiveTest {
    private Properties properties;

    @Before
    public void beforeEach() {
        properties = new Properties();
        RulesEngineProperties.initialize(properties);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void getProperty(String key) {
        properties.getProperty(key);
    }
}

package org.openmrs.module.rulesengine.engine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.BSABasedDosageRule;
import org.openmrs.module.rulesengine.rule.DosageRule;
import org.openmrs.module.rulesengine.util.RulesEngineProperties;
import org.openmrs.util.OpenmrsUtil;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RulesEngineProperties.class, OpenmrsUtil.class})
public class RulesEngineImplTest {

    @Mock
    BSABasedDosageRule bsaBasedDoseRule;

    RulesEngineImpl rulesEngine;

    @Before
    public void setUp() throws Exception {
        mockStatic(RulesEngineProperties.class);
        mockStatic(OpenmrsUtil.class);
        File resourcesDirectory = new File("src/test/resources/");
        when(OpenmrsUtil.getApplicationDataDirectory()).thenReturn(resourcesDirectory.getAbsolutePath()+"/");
        rulesEngine=new RulesEngineImpl();
    }

    @Test
    public void getRuleNamesReturnsRulesFromProperties() {
        when(RulesEngineProperties.getProperty("rules")).thenReturn("mg/kg|mg/m2");

        String[] ruleNames = rulesEngine.getRuleNames();

        Assert.assertNotNull(ruleNames);
        Assert.assertEquals("mg/kg",ruleNames[0]);
        Assert.assertEquals("mg/m2",ruleNames[1]);
    }

    @Test
    public void getRuleObjectFindsTheCorrectRuleObject() throws Exception {

        DosageRule dose = rulesEngine.getRuleObject("testrule");

        Assert.assertNotNull(dose);
        Assert.assertEquals("TestDosageRule",dose.getClass().getSimpleName());
    }

    @Test
    public void getRulesRegisteredReturnsAllRegisteredRuleNames() throws Exception {
        String[] rulesRegistered = rulesEngine.getRulesRegistered();

        Assert.assertNotNull(rulesRegistered);
        List<String> list= Arrays.asList(rulesRegistered);
        Assert.assertTrue(list.contains("testrule"));
        Assert.assertTrue(list.contains("customrule"));
        Assert.assertTrue(list.contains("mg/kg"));
        Assert.assertTrue(list.contains("mg/m2"));
    }

    @Test
    public void rulesEngineFindsTheCorrectRuleObjectAndCalculates() throws Exception {
        DosageRequest request=new DosageRequest("testName","1",10,"mg","custom","testrule", null);
        Dose dose = rulesEngine.calculateDose(request);

        Assert.assertNotNull(dose);
        Assert.assertEquals("testName",dose.getDrugName());
        Assert.assertEquals(100.0,dose.getValue(),0);
    }
}
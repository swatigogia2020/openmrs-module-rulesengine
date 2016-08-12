package org.openmrs.module.rulesengine.engine;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.BSABasedDosageRule;
import org.openmrs.module.rulesengine.rule.DosageRule;
import org.springframework.context.ApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RulesEngineImplTest {

    @Mock
    ApplicationContext applicationContext;

    @Mock
    BSABasedDosageRule bsaBasedDoseRule;

    @InjectMocks
    RulesEngineImpl rulesEngine = new RulesEngineImpl();

    @Test
    public void calculateDoseWithCorrectImplementation() throws Exception {
        Dose actualDose = new Dose("paracetemol", 150, Dose.DoseUnit.mg);
        DosageRequest dosageRequest = new DosageRequest("paracetamol","person_1055_uuid", 10.0, "mg/kg","testorderset","mg/kg");

        when(bsaBasedDoseRule.calculateDose(any(DosageRequest.class))).thenReturn(actualDose);
        when(applicationContext.getBean("mg/kg", DosageRule.class)).thenReturn(bsaBasedDoseRule);

        Dose expectedDose = rulesEngine.calculateDose(dosageRequest);
        Assert.assertNotNull(expectedDose);

        verify(applicationContext).getBean("mg/kg", DosageRule.class);

    }
}
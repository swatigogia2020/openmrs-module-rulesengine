package org.openmrs.module.rulesengine.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.module.rulesengine.service.ObservationService;

public class ValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldThrowExceptionWhenBothWeightAndHeightObservationDoesNotExist() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Please enter " + ObservationService.ConceptRepo.HEIGHT + " and " + ObservationService.ConceptRepo.WEIGHT + " to calculate dose.");
        Validator.validateHeightAndWeight(null, null, ObservationService.ConceptRepo.HEIGHT, ObservationService.ConceptRepo.WEIGHT);
    }

    @Test
    public void shouldThrowExceptionWhenWeightObservationDoesNotExist() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Please enter " + ObservationService.ConceptRepo.HEIGHT + " to calculate dose.");
        Validator.validateHeightAndWeight(null, 80.0, ObservationService.ConceptRepo.HEIGHT, ObservationService.ConceptRepo.WEIGHT);
    }

    @Test
    public void shouldThrowExceptionWhenHeightObservationDoesNotExist() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Please enter " + ObservationService.ConceptRepo.WEIGHT + " to calculate dose.");
        Validator.validateHeightAndWeight(179.0, null, ObservationService.ConceptRepo.HEIGHT, ObservationService.ConceptRepo.WEIGHT);
    }

}

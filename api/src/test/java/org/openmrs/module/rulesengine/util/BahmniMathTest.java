package org.openmrs.module.rulesengine.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class BahmniMathTest {

    @Test
    public void ageInYearsShouldReturnCorrectValue() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = simpleDateFormat.parse("02/10/1993");
        Date today = simpleDateFormat.parse("01/11/2016");

        Integer years = BahmniMath.ageInYears(birthDate, today);
        assertEquals(23,(int)years);

        birthDate = simpleDateFormat.parse("15/08/1947");
        today = simpleDateFormat.parse("15/08/1957");

        years = BahmniMath.ageInYears(birthDate, today);
        assertEquals(10,(int)years);

    }
}
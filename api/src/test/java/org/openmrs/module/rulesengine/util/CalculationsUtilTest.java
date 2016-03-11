package org.openmrs.module.rulesengine.util;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class CalculationsUtilTest {

    @Test
    public void calculateBsaShouldReturnCorrectValue() throws Exception{
        Double bsa = CalculationsUtil.calculateBSA(170.0, 70.0, 23);
        assertEquals(1.809,bsa,0.001);

        bsa = CalculationsUtil.calculateBSA(120.0, 35.0, 10);
        assertEquals(1.080,bsa,0.001);

        bsa = CalculationsUtil.calculateBSA(120.0, 35.0, 20);
        assertEquals(1.047,bsa,0.001);

        bsa = CalculationsUtil.calculateBSA(120.0, 41.0, 15);
        assertEquals(1.119,bsa,0.001);
    }

    @Test
    public void ageInYearsShouldReturnCorrectValue() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = simpleDateFormat.parse("02/10/1993");
        Date today = simpleDateFormat.parse("01/11/2016");

        Integer years = CalculationsUtil.ageInYears(birthDate, today);
        assertEquals(23,(int)years);

        birthDate = simpleDateFormat.parse("15/08/1947");
        today = simpleDateFormat.parse("15/08/1957");

        years = CalculationsUtil.ageInYears(birthDate, today);
        assertEquals(10,(int)years);

    }
}
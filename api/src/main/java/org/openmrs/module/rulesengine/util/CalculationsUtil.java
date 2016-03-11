package org.openmrs.module.rulesengine.util;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.math.BigDecimal;
import java.util.Date;

public class CalculationsUtil {
    public static double getTwoDigitRoundUpValue(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double calculateBSA(Double height, Double weight, Integer patientAgeInYears) {
        if (patientAgeInYears <= 15 && weight <= 40) {
            return Math.sqrt(weight * height / 3600);
        }
        return Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184;
    }

    public static Integer ageInYears(Date birthDate, Date asOfDate) {
        return Years.yearsBetween(new LocalDate(birthDate), new LocalDate(asOfDate)).getYears();
    }
}

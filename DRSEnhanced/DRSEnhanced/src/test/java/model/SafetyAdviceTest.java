package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SafetyAdviceTest {

    @Test
    public void testFireAdvice() {

        SafetyAdvice advice = new SafetyAdvice();

        String result =
                advice.getAdvice("Fire");

        assertTrue(
                result.contains("Evacuate")
        );
    }

    @Test
    public void testEarthquakeAdvice() {

        SafetyAdvice advice = new SafetyAdvice();

        String result =
                advice.getAdvice("Earthquake");

        assertTrue(
                result.contains("Drop")
        );
    }

    @Test
    public void testFloodAdvice() {

        SafetyAdvice advice = new SafetyAdvice();

        String result =
                advice.getAdvice("Flood");

        assertTrue(
                result.contains("higher ground")
        );
    }

    @Test
    public void testHurricaneAdvice() {

        SafetyAdvice advice = new SafetyAdvice();

        String result =
                advice.getAdvice("Hurricane");

        assertTrue(
                result.contains("indoors")
        );
    }
}
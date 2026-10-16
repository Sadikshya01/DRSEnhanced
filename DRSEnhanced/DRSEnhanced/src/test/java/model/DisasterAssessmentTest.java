package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DisasterAssessmentTest {

    @Test
    public void testLowPriority() {

        DisasterAssessment assessment =
                new DisasterAssessment(1, 3, "Minor");

        assertEquals(
                "Low",
                assessment.determinePriority()
        );
    }

    @Test
    public void testMediumPriority() {

        DisasterAssessment assessment =
                new DisasterAssessment(3, 20, "Minor");

        assertEquals(
                "Medium",
                assessment.determinePriority()
        );
    }

    @Test
    public void testHighPriority() {

        DisasterAssessment assessment =
                new DisasterAssessment(4, 20, "Moderate");

        assertEquals(
                "High",
                assessment.determinePriority()
        );
    }

    @Test
    public void testCriticalPriority() {

        DisasterAssessment assessment =
                new DisasterAssessment(5, 60, "Severe");

        assertEquals(
                "Critical",
                assessment.determinePriority()
        );
    }
}
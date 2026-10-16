package model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentRecommendationTest {

    @Test
    public void testFireRecommendation() {

        DepartmentRecommendation recommendation =
                new DepartmentRecommendation();

        List<String> result =
                recommendation.recommendDepartments("Fire");

        assertTrue(
                result.contains("Fire and Emergency Services")
        );

        assertTrue(
                result.contains("Hospital")
        );

        assertTrue(
                result.contains("Law Enforcement")
        );
    }

    @Test
    public void testEarthquakeRecommendation() {

        DepartmentRecommendation recommendation =
                new DepartmentRecommendation();

        List<String> result =
                recommendation.recommendDepartments("Earthquake");

        assertTrue(result.contains("Hospital"));
        assertTrue(result.contains("Electricity"));
        assertTrue(result.contains("Water Supply"));
    }

    @Test
    public void testFloodRecommendation() {

        DepartmentRecommendation recommendation =
                new DepartmentRecommendation();

        List<String> result =
                recommendation.recommendDepartments("Flood");

        assertTrue(result.contains("Water Supply"));
        assertTrue(result.contains("Transportation"));
    }

    @Test
    public void testHurricaneRecommendation() {

        DepartmentRecommendation recommendation =
                new DepartmentRecommendation();

        List<String> result =
                recommendation.recommendDepartments("Hurricane");

        assertTrue(
                result.contains("Fire and Emergency Services")
        );

        assertTrue(result.contains("Hospital"));
        assertTrue(result.contains("Transportation"));
        assertTrue(result.contains("Electricity"));
    }
}
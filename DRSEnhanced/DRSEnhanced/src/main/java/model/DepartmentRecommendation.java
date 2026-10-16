package model;

import java.util.ArrayList;
import java.util.List;

public class DepartmentRecommendation {

    public List<String> recommendDepartments(String disasterType) {

        List<String> departments = new ArrayList<>();

        if (disasterType == null) {
            return departments;
        }

        switch (disasterType.toLowerCase()) {

            case "fire":
                departments.add("Fire and Emergency Services");
                departments.add("Hospital");
                departments.add("Law Enforcement");
                break;

            case "earthquake":
                departments.add("Fire and Emergency Services");
                departments.add("Hospital");
                departments.add("Law Enforcement");
                departments.add("Electricity");
                departments.add("Water Supply");
                break;

            case "flood":
                departments.add("Fire and Emergency Services");
                departments.add("Hospital");
                departments.add("Transportation");
                departments.add("Water Supply");
                break;

            case "hurricane":
                departments.add("Fire and Emergency Services");
                departments.add("Hospital");
                departments.add("Transportation");
                departments.add("Electricity");
                break;

            default:
                departments.add("Fire and Emergency Services");
        }

        return departments;
    }

    @Override
    public String toString() {
        return "Automatic Department Recommendation";
    }
}
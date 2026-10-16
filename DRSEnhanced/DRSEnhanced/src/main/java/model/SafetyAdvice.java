package model;

public class SafetyAdvice {

    public String getAdvice(String disasterType) {

        if (disasterType == null) {
            return "Follow instructions from emergency authorities.";
        }

        switch (disasterType.toLowerCase()) {

            case "fire":
                return "Evacuate the affected area, avoid smoke "
                        + "and follow emergency instructions.";

            case "earthquake":
                return "Drop, cover and hold. Stay away from "
                        + "windows and damaged structures.";

            case "flood":
                return "Move to higher ground and do not enter flood water.";

            case "hurricane":
                return "Stay indoors, keep away from windows "
                        + "and follow evacuation instructions.";

            default:
                return "Move to a safe location and follow "
                        + "instructions from emergency authorities.";
        }
    }

    @Override
    public String toString() {
        return "Disaster Safety Advice";
    }
}
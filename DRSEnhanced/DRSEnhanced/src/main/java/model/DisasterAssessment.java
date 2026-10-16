package model;

public class DisasterAssessment {

    private int severity;
    private int peopleAffected;
    private String infrastructureImpact;
    private int riskScore;
    private String priority;

    public DisasterAssessment() {
    }

    public DisasterAssessment(int severity,
                              int peopleAffected,
                              String infrastructureImpact) {

        this.severity = severity;
        this.peopleAffected = peopleAffected;
        this.infrastructureImpact = infrastructureImpact;
    }

    public int calculateRiskScore() {

        int score = severity;

        if (peopleAffected >= 50) {
            score += 3;
        } else if (peopleAffected >= 20) {
            score += 2;
        } else if (peopleAffected > 0) {
            score += 1;
        }

        if ("Severe".equalsIgnoreCase(infrastructureImpact)) {
            score += 3;
        } else if ("Moderate".equalsIgnoreCase(infrastructureImpact)) {
            score += 2;
        } else if ("Minor".equalsIgnoreCase(infrastructureImpact)) {
            score += 1;
        }

        riskScore = score;

        return riskScore;
    }

    public String determinePriority() {

        int score = calculateRiskScore();

        if (score >= 9) {
            priority = "Critical";
        } else if (score >= 7) {
            priority = "High";
        } else if (score >= 5) {
            priority = "Medium";
        } else {
            priority = "Low";
        }

        return priority;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getPeopleAffected() {
        return peopleAffected;
    }

    public void setPeopleAffected(int peopleAffected) {
        this.peopleAffected = peopleAffected;
    }

    public String getInfrastructureImpact() {
        return infrastructureImpact;
    }

    public void setInfrastructureImpact(String infrastructureImpact) {
        this.infrastructureImpact = infrastructureImpact;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Risk Score: " + riskScore
                + ", Priority: " + priority;
    }
}
package model;

import java.time.LocalDateTime;

public class DisasterReport {

    private int disasterId;
    private String disasterType;
    private String location;
    private LocalDateTime dateTime;
    private String description;
    private int severity;
    private int peopleAffected;
    private String infrastructureImpact;
    private String priority;
    private String status;
    private String assignedDepartment;

    public DisasterReport() {
    }

    public DisasterReport(int disasterId,
                          String disasterType,
                          String location,
                          LocalDateTime dateTime,
                          String description,
                          int severity,
                          int peopleAffected,
                          String infrastructureImpact) {

        this.disasterId = disasterId;
        this.disasterType = disasterType;
        this.location = location;
        this.dateTime = dateTime;
        this.description = description;
        this.severity = severity;
        this.peopleAffected = peopleAffected;
        this.infrastructureImpact = infrastructureImpact;

        this.priority = "Not Assessed";
        this.status = "Reported";
        this.assignedDepartment = "Not Assigned";
    }

    public int getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(int disasterId) {
        this.disasterId = disasterId;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    @Override
    public String toString() {

        return "Disaster ID: " + disasterId
                + ", Type: " + disasterType
                + ", Location: " + location
                + ", Priority: " + priority
                + ", Status: " + status;
    }
}
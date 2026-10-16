package model;

public class ResponseAssignment {

    private int assignmentId;
    private DisasterReport disaster;
    private Department department;
    private String assignmentStatus;

    public ResponseAssignment() {
    }

    public ResponseAssignment(int assignmentId,
                              DisasterReport disaster,
                              Department department) {

        this.assignmentId = assignmentId;
        this.disaster = disaster;
        this.department = department;
        this.assignmentStatus = "Assigned";
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public DisasterReport getDisaster() {
        return disaster;
    }

    public void setDisaster(DisasterReport disaster) {
        this.disaster = disaster;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public void updateStatus(String status) {
        this.assignmentStatus = status;
    }

    @Override
    public String toString() {
        return "Assignment " + assignmentId
                + ": " + department
                + " - " + assignmentStatus;
    }
}
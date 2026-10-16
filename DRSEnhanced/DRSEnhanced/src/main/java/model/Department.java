package model;

public class Department {

    private int departmentId;
    private String departmentName;
    private String contactDetails;
    private boolean available;

    public Department() {
    }

    public Department(int departmentId,
                      String departmentName,
                      String contactDetails,
                      boolean available) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.contactDetails = contactDetails;
        this.available = available;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return departmentName;
    }
}
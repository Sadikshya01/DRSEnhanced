package model;

public class ResponseOfficer extends User {

    public ResponseOfficer() {
    }

    public ResponseOfficer(int userId, String fullName,
                           String username, String password) {

        super(userId, fullName, username,
              password, "Disaster Response Officer");
    }

    @Override
    public String toString() {
        return "Response Officer: " + getFullName();
    }
}
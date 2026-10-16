package model;

public class Administrator extends User {

    public Administrator() {
    }

    public Administrator(int userId, String fullName,
                         String username, String password) {

        super(userId, fullName, username,
              password, "Administrator");
    }

    @Override
    public String toString() {
        return "Administrator: " + getFullName();
    }
}
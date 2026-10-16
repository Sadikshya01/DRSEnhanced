package model;

public class PublicUser extends User {

    public PublicUser() {
    }

    public PublicUser(int userId, String fullName,
                      String username, String password) {

        super(userId, fullName, username,
              password, "Public User");
    }

    @Override
    public String toString() {
        return "Public User: " + getFullName();
    }
}
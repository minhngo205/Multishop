package minh.project.multishop.models;

import java.util.Date;

public class UserProfile {
    private int id;
    private String username;
    private String email;
    private String role;
    private String name;
    private String address;
    private String phone_number;
    private String dob;
    private Date created_at;
    private Date updated_at;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getDob() {
        return dob;
    }
}

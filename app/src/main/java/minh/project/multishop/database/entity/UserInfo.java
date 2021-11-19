package minh.project.multishop.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {
    @PrimaryKey
    private int id;
    @ColumnInfo
    private String username;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String address;
    @ColumnInfo
    private String phone_number;
    @ColumnInfo
    private String dob;
    @ColumnInfo
    private Long updated_at;

    public UserInfo(int id, String username, String email, String name, String address, String phone_number, String dob, Long updated_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.dob = dob;
        this.updated_at = updated_at;
    }

    public UserInfo(String name, String address, String phone_number) {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Long updated_at) {
        this.updated_at = updated_at;
    }
}

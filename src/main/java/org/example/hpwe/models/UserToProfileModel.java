package org.example.hpwe.models;

public class UserToProfileModel {
    private String name;
    private String firstName;
    private String surname;
    private String email;
    private String phone;
    private String role;

    public String getName() {
        return name;
    }

    public UserToProfileModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserToProfileModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserToProfileModel setRole(String role) {
        this.role = role;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserToProfileModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserToProfileModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserToProfileModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }
}

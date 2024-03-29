package org.example.hpwe.forms.profile;

public class PersonalChangeForm {
    private String firstName;
    private String surname;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public PersonalChangeForm setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PersonalChangeForm setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public PersonalChangeForm setSurname(String surname) {
        this.surname = surname;
        return this;
    }
}

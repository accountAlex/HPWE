package org.example.hpwe.forms.profile;

public class AccountChangeForm {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public AccountChangeForm setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountChangeForm setEmail(String email) {
        this.email = email;
        return this;
    }
}

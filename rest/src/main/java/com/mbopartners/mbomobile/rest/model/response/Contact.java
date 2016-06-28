package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class Contact implements Serializable, Validatable {
    private static final String TAG = Contact.class.getSimpleName();

    @SerializedName("firstName")
    private String firstName = null;
    @SerializedName("lastName")
    private String lastName = null;
    @SerializedName("username")
    private String email = null;

    @Override
    public boolean isValid() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Contact {\n");

        sb.append("  firstName: ").append(firstName).append("\n");
        sb.append("  lastName: ").append(lastName).append("\n");
        sb.append("  username: ").append(email).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

}

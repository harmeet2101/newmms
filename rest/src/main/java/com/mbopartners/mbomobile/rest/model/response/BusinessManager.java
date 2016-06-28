package com.mbopartners.mbomobile.rest.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessManager implements Serializable {
    @SerializedName("id")
    private String mboId;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;

    public BusinessManager(String mboId, String firstName, String lastName, String email) {
        this.mboId = mboId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
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
        return "BusinessManager{" +
                "mboId='" + mboId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

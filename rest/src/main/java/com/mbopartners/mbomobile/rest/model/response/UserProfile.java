package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class UserProfile implements Serializable, Validatable{
    private static final String TAG = UserProfile.class.getSimpleName();
    private static boolean DEFAULT_nonbillableAllowed_VALUE = false;

    @SerializedName("id")
    private String mboId;
    @SerializedName("number")
    private String number;
    @SerializedName("status")
    private String status;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("businessManager")
    private BusinessManager businessManager;
    @SerializedName("nonbillableAllowed")
    private Boolean nonbillableAllowed = false;

    @Override
    public boolean isValid() {
        boolean result = mboId != null;

        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + mboId);
        }
        return result;
    }

    public UserProfile(String mboId, String number, String status, String firstName, String lastName, String email, BusinessManager businessManager, Boolean nonbillableAllowed) {
        this.mboId = mboId;
        this.number = number;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.businessManager = businessManager;
        this.nonbillableAllowed = nonbillableAllowed;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public BusinessManager getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(BusinessManager businessManager) {
        this.businessManager = businessManager;
    }

    public Boolean getNonbillableAllowed() {
        return nonbillableAllowed == null ? DEFAULT_nonbillableAllowed_VALUE : nonbillableAllowed;
    }

    public void setNonbillableAllowed(Boolean nonbillableAllowed) {
        this.nonbillableAllowed = nonbillableAllowed == null ? DEFAULT_nonbillableAllowed_VALUE : nonbillableAllowed;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "mboId='" + mboId + '\'' +
                ", number='" + number + '\'' +
                ", status='" + status + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", businessManager=" + businessManager +
                ", nonbillableAllowed=" + nonbillableAllowed +
                '}';
    }
}

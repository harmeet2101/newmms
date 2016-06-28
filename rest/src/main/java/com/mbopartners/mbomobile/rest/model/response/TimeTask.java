package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class TimeTask implements Serializable, Validatable {
    private static final String TAG = TimeTask.class.getSimpleName();

    @SerializedName("id")
    private String id = null;
    @SerializedName("name")
    private String name = null;

    public TimeTask() {
    }

    public TimeTask(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean isValid() {
        boolean result = (id != null && name != null);
        if (! result) {
            Log.e(TAG, "NOT VALID");
        }
        return result;

    }

    // -------- Getters, setters, toString()

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TimeTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

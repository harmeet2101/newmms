package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class TimePeriod implements Serializable, Validatable {
    private static final String TAG = TimePeriod.class.getSimpleName();

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name = null;
    @SerializedName("startDate")
    private Date startDate = null;
    @SerializedName("endDate")
    private Date endDate = null;
    @SerializedName("current")
    private Boolean current = null;
    @SerializedName("submittable")
    private Boolean submittable = null;
    @SerializedName("workOrderId")
    private String workOrderId = null;
    @SerializedName("timeEntries")
    private List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

    public TimePeriod() {
    }

    public TimePeriod(String id, String name, Date startDate, Date endDate, Boolean current, Boolean submittable, String workOrderId, List<TimeEntry> timeEntries) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.current = current;
        this.submittable = submittable;
        this.workOrderId = workOrderId;
        this.timeEntries = timeEntries;
    }

    @Override
    public boolean isValid() {
        boolean result = (
                id != null &&
                name != null &&
                startDate != null &&
                endDate != null &&
                current != null &&
                submittable != null &&
                workOrderId != null &&
                timeEntries != null && ValidationHelper.validAll(timeEntries) &&
                startDate.getTime() < endDate.getTime()
                );
        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + id);
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public Boolean getSubmittable() {
        return submittable;
    }

    public void setSubmittable(Boolean submittable) {
        this.submittable = submittable;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", current=" + current +
                ", submittable=" + submittable +
                ", workOrderId='" + workOrderId + '\'' +
                ", timeEntries=" + timeEntries +
                '}';
    }
}

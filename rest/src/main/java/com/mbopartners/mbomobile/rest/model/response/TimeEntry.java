package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class TimeEntry implements Serializable, Validatable {
    private static final String TAG = TimeEntry.class.getSimpleName();
    public static final double VERSION = 1.0d;

    @SerializedName("id")
    private String id = null;
    @SerializedName("workOrderId")
    private String workOrderId = null;
    @SerializedName("taskId")
    private String taskId = null;
    @SerializedName("hours")
    private Double hours = null;
    @SerializedName("date")
    private Date date = null;
    @SerializedName("note")
    private String note = null;
    @SerializedName("editable")
    private Boolean editable = null;
    @SerializedName("version")
    private Double version = VERSION;

    public TimeEntry() {
    }

    public TimeEntry(String id, String workOrderId, String taskId, Double hours, Date date, String note, Boolean editable, Double version) {
        this.id = id;
        this.workOrderId = workOrderId;
        this.taskId = taskId;
        this.hours = hours;
        this.date = date;
        this.note = note;
        this.editable = editable;
        this.version = version;
    }

    @Override
    public boolean isValid() {
        boolean result = (
            id != null &&
            workOrderId != null &&
            taskId != null &&
            hours != null && hours >= 0.0 &&
            date != null &&
            editable != null
        );
        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + id);
            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("workOrderId", workOrderId);
            screamer.sayIfIsNull("taskId", taskId);
            screamer.sayIfIsNull("hours", hours);
            // todo " && hours >= 0.0 &&
            screamer.sayIfIsNull("date", date);
            screamer.sayIfIsNull("editable", editable);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id='" + id + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", hours=" + hours +
                ", date=" + date +
                ", note='" + note + '\'' +
                ", editable=" + editable +
                ", version=" + version +
                '}';
    }

}

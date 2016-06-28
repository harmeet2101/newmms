package com.mbopartners.mbomobile.ui.activity.helper;

import com.mbopartners.mbomobile.rest.model.response.TimeEntry;

import java.io.Serializable;
import java.util.Date;

public class TimeEntryToSave implements Serializable {

    private TimeEntry timeEntry;
    /**
     * Flag. Mean that attached TimeEntry was NOT received from Server, but generated just now
     */
    private boolean createdTimeEntry;
    private Double originalHours;
    private String originalNote;
    private String timeTaskName;


    public TimeEntryToSave(TimeEntry timeEntry, String timeTaskName) {
        this.timeEntry = timeEntry;
        this.createdTimeEntry = false;
        this.originalHours = timeEntry.getHours();
        this.originalNote = timeEntry.getNote();
        this.timeTaskName = timeTaskName;
    }

    public TimeEntryToSave(String workOrderId, String timeTaskID, String timeTaskName, Date date) {

        TimeEntry timeEntry = new TimeEntry(
                null, workOrderId, timeTaskID,
                0.0D, date, "",
                true, TimeEntry.VERSION);

        this.timeEntry = timeEntry;
        this.createdTimeEntry = true;
        this.originalHours = timeEntry.getHours();
        this.originalNote = timeEntry.getNote();
        this.timeTaskName = timeTaskName;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    public Double getOriginalHours() {
        return originalHours;
    }

    public String getOriginalNote() {
        return originalNote;
    }

    public boolean isCreatedTimeEntry() {
        return createdTimeEntry;
    }

    public String getTimeTaskName() {
        return timeTaskName;
    }

    public boolean isChanged() {
        String noteOrig = originalNote == null ? "" : originalNote;
        String noteCur = timeEntry.getNote() == null ? "" : timeEntry.getNote();
        return  ! (originalHours.equals(timeEntry.getHours()) && noteOrig.equals(noteCur));
    }
}

package com.mbopartners.mbomobile.ui.model;

import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.Date;

public class TimeSheetItem {
    /**
     * From {@link com.mbopartners.mbomobile.rest.model.response.WorkOrder}
     */
    private String workOrderId;
    private String workOrderName;
    private Boolean timeEntryAllowed;

    /**
     * From {@link com.mbopartners.mbomobile.rest.model.response.Company}
     */
    private String companyId;
    private String companyName;

    /**
     * From {@link com.mbopartners.mbomobile.rest.model.response.TimePeriod
     */
    private String timePeriodId;
    private String timePeriodName;
    private Date timePeriodStartDate;
    private Date timePeriodEndDate;
    private Boolean timePeriodCurrent;
    private Boolean timePeriodSubmittable = null;

    /**
     * Calculated Field
     */
    private String totalHours;
    private boolean isViewable;
    private boolean isEditable;

    public TimeSheetItem(String workOrderId, String workOrderName, Boolean timeEntryAllowed, String companyId, String companyName, String timePeriodId, String timePeriodName, Date timePeriodStartDate, Date timePeriodEndDate, Boolean timePeriodCurrent, Boolean timePeriodSubmittable, String totalHours, boolean isViewable, boolean isEditable) {
        this.workOrderId = workOrderId;
        this.workOrderName = workOrderName;
        this.timeEntryAllowed = timeEntryAllowed;
        this.companyId = companyId;
        this.companyName = companyName;
        this.timePeriodId = timePeriodId;
        this.timePeriodName = timePeriodName;
        this.timePeriodStartDate = timePeriodStartDate;
        this.timePeriodEndDate = timePeriodEndDate;
        this.timePeriodCurrent = timePeriodCurrent;
        this.timePeriodSubmittable = timePeriodSubmittable;
        this.totalHours = totalHours;
        this.isViewable = isViewable;
        this.isEditable = isEditable;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public Boolean getTimeEntryAllowed() {
        return timeEntryAllowed;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTimePeriodId() {
        return timePeriodId;
    }

    public String getTimePeriodName() {
        return timePeriodName;
    }

    public Date getTimePeriodStartDate() {
        return timePeriodStartDate;
    }

    public Date getTimePeriodEndDate() {
        return timePeriodEndDate;
    }

    public Boolean getTimePeriodCurrent() {
        return timePeriodCurrent;
    }

    public Boolean getTimePeriodSubmittable() {
        return timePeriodSubmittable;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public boolean isViewable() {
        return isViewable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public String getPeriodAsString() {
        return DateUtil.getFullFormattedPeriodAsString(timePeriodStartDate, timePeriodEndDate);
    }


    public String toString()
    {
        String str="submittable: "+getTimePeriodSubmittable();
        return str;
    }
}

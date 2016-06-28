package com.mbopartners.mbomobile.ui.model;

import java.util.Date;

public class WeeklyTimeSheetItem {
    private final Date date;
    private final Double totalHours;
    private final boolean notInFuture;
    private final boolean dayEditable;
    private final boolean parentTmePeriodEditable;

    public WeeklyTimeSheetItem(Date date, Double totalHours, boolean notInFuture, boolean dayEditable, boolean parentTmePeriodEditable) {
        this.date = date;
        this.totalHours = totalHours;
        this.notInFuture = notInFuture;
        this.dayEditable = dayEditable;
        this.parentTmePeriodEditable = parentTmePeriodEditable;
    }

    public Date getDate() {
        return date;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public boolean isNotInFuture() {
        return notInFuture;
    }

    public boolean isDayEditable() {
        return dayEditable;
    }

    public boolean isParentTmePeriodEditable() {
        return parentTmePeriodEditable;
    }
}

package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import java.io.Serializable;
import java.util.*;

import com.google.gson.annotations.SerializedName;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class WorkOrder implements Serializable, Validatable {
    private static final String TAG = WorkOrder.class.getSimpleName();

    @SerializedName("id")
    private String id = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("company")
    private Company company = null;
    @SerializedName("timeEntryAllowed")
    private Boolean timeEntryAllowed = null;
    @SerializedName("billableExpensesAllowed")
    private Boolean billableExpensesAllowed = null;
    @SerializedName("timePeriods")
    private List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
    @SerializedName("timeTasks")
    private List<TimeTask> timeTasks = new ArrayList<TimeTask>();

    public WorkOrder() {
    }

    public WorkOrder(String id, String name, Company company, Boolean timeEntryAllowed, Boolean billableExpensesAllowed, List<TimePeriod> timePeriods, List<TimeTask> timeTasks) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.timeEntryAllowed = timeEntryAllowed;
        this.billableExpensesAllowed = billableExpensesAllowed;
        this.timePeriods = timePeriods;
        this.timeTasks = timeTasks;
    }

    @Override
    public boolean isValid() {
        boolean result = (
                id != null && id.length() > 0 &&
                name != null &&
                company != null && //company.isValid() &&
                timeEntryAllowed != null &&
                billableExpensesAllowed != null &&
                timePeriods != null && timePeriods.size() > 0 && ValidationHelper.validAll(timePeriods) &&
                        ((timeTasks != null && timeTasks.size() > 0 && ValidationHelper.validAll(timeTasks) && timeEntryAllowed) ||
                                (timeTasks != null && timeTasks.isEmpty() && ! timeEntryAllowed)) &&
                isTimeEntriesHasValidTaskId()
        );

        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + id);
            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            //id.length() > 0 &&
            screamer.sayIfIsNull("name", name);
            screamer.sayIfIsNull("company", company);
            screamer.sayIfIsNull("timeEntryAllowed", timeEntryAllowed);
            screamer.sayIfIsNull("billableExpensesAllowed", billableExpensesAllowed);
            screamer.sayIfIsFalse("(timePeriods != null && timePeriods.size() > 0)", (timePeriods != null && timePeriods.size() > 0));
//            screamer.sayIfIsFalse("(timeTasks != null && timeTasks.size() > 0)", (timeTasks != null && timeTasks.size() > 0));
        }
        return result;
    }

    public boolean isTimeEntriesHasValidTaskId() {
        boolean result = true;
        Set<String> taskIdSet = new HashSet<>(timeTasks.size());
        for (TimeTask timeTask : timeTasks) {
            taskIdSet.add(timeTask.getId());
        }

        for (TimePeriod timePeriod : timePeriods) {
            for (TimeEntry timeEntry : timePeriod.getTimeEntries()) {
                if (!taskIdSet.contains(timeEntry.getTaskId())) {
                    result = false;
                    Log.e(TAG, "TimeEntry id = " + timeEntry.getId() + " has 'taskId' = " + timeEntry.getTaskId() + " that parent WorkOrder does not has in 'timeTasks' array.");
                }
            }
        }
        return result;
    }

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getTimeEntryAllowed() {
        return timeEntryAllowed;
    }

    public void setTimeEntryAllowed(Boolean timeEntryAllowed) {
        this.timeEntryAllowed = timeEntryAllowed;
    }

    public Boolean getBillableExpensesAllowed() {
        return billableExpensesAllowed;
    }

    public void setBillableExpensesAllowed(Boolean billableExpensesAllowed) {
        this.billableExpensesAllowed = billableExpensesAllowed;
    }

    public List<TimePeriod> getTimePeriods() {
        return timePeriods;
    }

    public void setTimePeriods(List<TimePeriod> timePeriods) {
        this.timePeriods = timePeriods;
    }

    public List<TimeTask> getTimeTasks() {
        return timeTasks;
    }

    public void setTimeTasks(List<TimeTask> timeTasks) {
        this.timeTasks = timeTasks;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", timeEntryAllowed=" + timeEntryAllowed +
                ", billableExpensesAllowed=" + billableExpensesAllowed +
                ", timePeriods=" + timePeriods +
                ", timeTasks=" + timeTasks +
                '}';
    }
}

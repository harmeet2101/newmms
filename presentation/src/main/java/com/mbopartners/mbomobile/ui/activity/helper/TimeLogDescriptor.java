package com.mbopartners.mbomobile.ui.activity.helper;

import com.mbopartners.mbomobile.rest.model.response.Company;
import com.mbopartners.mbomobile.rest.model.response.TimePeriod;
import com.mbopartners.mbomobile.rest.model.response.TimeTask;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TimeLogDescriptor implements Serializable {
    private String workOrderId = null;
    private String workOrderName = null;
    private Company company = null;
    private Boolean timeEntryAllowed = null;
    private TimePeriod timePeriod = null;
    private List<TimeTask> timeTasks = null;

    private Date chosenDate = null;
    private Date currentDate = DateUtil.getCurrentDate();
    private String timeTaskId_ForSavingNote;

    private List<TimeEntryToSave> timeEntriesToSave = null;

    public TimeLogDescriptor(List<WorkOrder> workOrders, String workOrderId, String timePeriodId) {
        this(Converter.findWorkOrder(workOrders, workOrderId),
        Converter.findTimePeriod(workOrders, workOrderId, timePeriodId)
        );
    }

    public TimeLogDescriptor(WorkOrder workOrder, TimePeriod timePeriod) {
        this.workOrderId = workOrder.getId();
        this.workOrderName = workOrder.getName();
        this.company = workOrder.getCompany();
        this.timeEntryAllowed = workOrder.getTimeEntryAllowed();
        this.timePeriod = timePeriod;
        this.timeTasks = workOrder.getTimeTasks();
        this.timeEntriesToSave = Converter.fillTimeEntriesToSave(timePeriod, workOrder.getTimeTasks(), currentDate);
        // We hold Note of TimeEntry only in fist TimeTask came from server.
        timeTaskId_ForSavingNote = workOrder.getTimeTasks().get(0).getId();
    }

    public boolean isTimeEntriesChanged() {
        for (TimeEntryToSave toSave : timeEntriesToSave) {
            if (toSave.isChanged()) {
                return true;
            }
        }
        return false;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public Company getCompany() {
        return company;
    }

    public Boolean getTimeEntryAllowed() {
        return timeEntryAllowed;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public List<TimeTask> getTimeTasks() {
        return timeTasks;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public List<TimeEntryToSave> getTimeEntriesToSave() {
        return timeEntriesToSave;
    }

    public String getTimeTaskId_ForSavingNote() {
        return timeTaskId_ForSavingNote;
    }
}

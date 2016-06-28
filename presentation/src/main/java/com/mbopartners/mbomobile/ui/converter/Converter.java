package com.mbopartners.mbomobile.ui.converter;

import android.util.Log;

import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.model.response.TimePeriod;
import com.mbopartners.mbomobile.rest.model.response.TimeTask;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.activity.helper.TimeEntryToSave;
import com.mbopartners.mbomobile.ui.activity.helper.TimeLogDescriptor;
import com.mbopartners.mbomobile.ui.model.TimeSheetItem;
import com.mbopartners.mbomobile.ui.model.WeeklyTimeSheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Converter {
    private static final String TAG = Converter.class.getSimpleName();

    public static final int DAYS_IN_WEEK = 7;

    public static List <TimeSheetItem> workOrder_2_SummaryTimeSheet(WorkOrder[] workOrders) {
        List<WorkOrder> workOrderList = Arrays.asList(workOrders);
        return  workOrder_2_SummaryTimeSheet(workOrderList);
    }

    public static List <TimeSheetItem> workOrder_2_SummaryTimeSheet(List<WorkOrder> workOrderList) {
        if (workOrderList == null) {
            return null;
        }

        List <TimeSheetItem> timeSheet = new ArrayList<>();
        for(WorkOrder workOrder : workOrderList) {
            List<TimePeriod> timePeriodList = workOrder.getTimePeriods();
            for (TimePeriod timePeriod : timePeriodList) {

                Double countHours = countHours(timePeriod.getTimeEntries());
                BigDecimal roundedBigDecimal = BigDecimal.valueOf(countHours).divide(BigDecimal.valueOf(1.0d), 0, BigDecimal.ROUND_HALF_UP);
                String totalHours =roundedBigDecimal.toString();
                boolean isViewable = ! timePeriod.getTimeEntries().isEmpty();
                Boolean isEditable = workOrder.getTimeEntryAllowed() && checkIsEditableTimeEntries(timePeriod.getTimeEntries());

                Log.d("item", timePeriod.toString());
                TimeSheetItem item =
                        new TimeSheetItem(
                                workOrder.getId(),
                                workOrder.getName(),
                                workOrder.getTimeEntryAllowed(),
                                workOrder.getCompany().getId(),
                                workOrder.getCompany().getName(),
                                timePeriod.getId(),
                                timePeriod.getName(),
                                timePeriod.getStartDate(),
                                timePeriod.getEndDate(),
                                timePeriod.getCurrent(),
                                timePeriod.getSubmittable(),
                                totalHours,
                                isViewable,
                                isEditable);

                timeSheet.add(item);
            }
        }
        Collections.sort(timeSheet, new TimePeriodComparatorOnCurrent());
        return timeSheet;
    }

    public static Boolean checkIsEditableTimeEntries(List<TimeEntry> timeEntries) {
        if (timeEntries.size() == 0) {
            return false;
        } else {
            boolean isEditable = false;
            for (TimeEntry timeEntry : timeEntries) {
                isEditable = isEditable || timeEntry.getEditable();
            }
            return isEditable;
        }
    }

    public static boolean checkIsEditableTimeEntries(List<TimeEntry> timeEntries, Date date) {
        List<TimeEntry> dayTimeEntries = new ArrayList<>();
        for (TimeEntry timeEntry : timeEntries) {
            if (timeEntry.getDate().equals(date)) {
                dayTimeEntries.add(timeEntry);
            }
        }
        return checkIsEditableTimeEntries(dayTimeEntries);
    }

    public static Double countHours(List<TimeEntry> timeEntries) {
        Double totalHours = 0.0;
        for (TimeEntry timeEntry : timeEntries) {
            totalHours += timeEntry.getHours();
        }
        return totalHours;
    }

    private static class TimePeriodComparatorOnCurrent implements Comparator<TimeSheetItem> {
        @Override
        public int compare(TimeSheetItem lhs, TimeSheetItem rhs) {
            return (lhs.getTimePeriodCurrent() ? 0 : 1) - (rhs.getTimePeriodCurrent() ? 0 : 1);
        }
    }

    public static TimePeriod findTimePeriod(List<WorkOrder> workOrders, String workOrderId, String timePeriodId) {
        TimePeriod timePeriodResult = null;
        for(WorkOrder workOrder : workOrders) {
            if (workOrder.getId().equals(workOrderId)) {
                for (TimePeriod timePeriod : workOrder.getTimePeriods()) {
                    if (timePeriod.getId().equals(timePeriodId)) {
                        timePeriodResult = timePeriod;
                        return timePeriodResult;
                    }
                }
            }
        }
        return timePeriodResult;
    }

    public static WorkOrder findWorkOrder(List<WorkOrder> workOrders, String workOrderId) {
        for(WorkOrder workOrder : workOrders) {
            if (workOrder.getId().equals(workOrderId)) {
                return workOrder;
            }
        }
        Log.e(TAG, "WorkOrder with Id = " + workOrderId + " DOES NOT EXISTS !");
        return null;
    }

    public static List<WorkOrder> filterAllowedWorkOrders(List<WorkOrder> workOrders) {
        List<WorkOrder> allowedWorkOrders = new ArrayList<>();
        for(WorkOrder workOrder : workOrders) {
            if (workOrder.getTimeEntryAllowed()) {
                allowedWorkOrders.add(workOrder);
            }
        }
        return allowedWorkOrders;
    }

    public static List<WorkOrder> filterBillableExpensesAllowedWorkOrders(List<WorkOrder> workOrders) {
        List<WorkOrder> allowedWorkOrders = new ArrayList<>();
        for(WorkOrder workOrder : workOrders) {
            if (workOrder.getBillableExpensesAllowed()) {
                allowedWorkOrders.add(workOrder);
            }
        }
        return allowedWorkOrders;
    }

    // ================================================================================

    public static List<TimeEntryToSave> fillTimeEntriesToSave(TimePeriod timePeriod, List<TimeTask> timeTasks, Date currentDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(timePeriod.getStartDate());

        Calendar end = Calendar.getInstance();
        end.setTime(timePeriod.getEndDate());

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(currentDate);

        if (end.compareTo(rightNow) > 0) {
            end = rightNow;
        }

        List<TimeEntryToSave> timeEntriesToSave = new ArrayList<>();
        TimeEntryToSave toSave;
        TimeEntry simpleTimeEntry;

        while (!start.after(end)) {
            for (TimeTask timeTask : timeTasks) {
                Date date = start.getTime();
                String taskId = timeTask.getId();
                String taskName = timeTask.getName();
                simpleTimeEntry = getTimeEntry(timePeriod.getTimeEntries(), date, taskId);
                if (simpleTimeEntry != null) {
                    toSave = new TimeEntryToSave(simpleTimeEntry, taskName);
                } else {
                    toSave = new TimeEntryToSave(timePeriod.getWorkOrderId(), taskId, taskName, date);
                }
                timeEntriesToSave.add(toSave);
            }
            start.add(Calendar.DATE, 1);
        }
        return timeEntriesToSave;
    }

    public static TimeEntry getTimeEntry(List<TimeEntry> timeEntries, Date date, String taskId) {
        if (timeEntries == null || date == null || taskId == null) {
            return null;
        }

        for (TimeEntry timeEntry : timeEntries) {
            if (timeEntry.getDate().equals(date) && timeEntry.getTaskId().equals(taskId)) {
                return timeEntry;
            }
        }
        return null;
    }

    public static List<WeeklyTimeSheetItem> timeLogDescriptor_2_WeeklyTimeSheet(TimeLogDescriptor timeLogDescriptor) {
        List<WeeklyTimeSheetItem> weeklyTimeSheetItems = new ArrayList<>();

        Calendar current = Calendar.getInstance();
        current.setTime(timeLogDescriptor.getCurrentDate());

        Calendar working = Calendar.getInstance();
        TimePeriod timePeriod = timeLogDescriptor.getTimePeriod();
        working.setTime(timePeriod.getStartDate());

        Calendar end = Calendar.getInstance();
        end.setTime(timePeriod.getEndDate());

        WeeklyTimeSheetItem item;
        Date date;
        Double dayTotalHours;

        boolean notInFuture;
        boolean dayEditable;
        boolean parentTmePeriodEditable;
        int daysAdded = 0;

        while (!working.after(end)) {
            date = working.getTime();
            dayTotalHours = getDayTotalHours(timeLogDescriptor.getTimeEntriesToSave(), date);
            notInFuture = (! working.after(current));
            dayEditable = checkIsEditableTimeEntries(timePeriod.getTimeEntries(), date);
            parentTmePeriodEditable =  checkIsEditableTimeEntries(timePeriod.getTimeEntries());

            item = new WeeklyTimeSheetItem(date, dayTotalHours, notInFuture, dayEditable, parentTmePeriodEditable);
            weeklyTimeSheetItems.add(item);

            working.add(Calendar.DATE, 1);
            daysAdded++;
        }

        // Add empty "fake" days just to fill week to the end
        for (int i = daysAdded; i < DAYS_IN_WEEK; i++) {
            date = working.getTime();
            notInFuture = current.after(working);
            item = new WeeklyTimeSheetItem(date, 0.0D, notInFuture, false, false);
            weeklyTimeSheetItems.add(item);
            working.add(Calendar.DATE, 1);
        }

        return weeklyTimeSheetItems;
    }

    public static Double getDayTotalHours(TimePeriod timePeriod, Date date) {
        Double dayTotalHours = 0.00;
        for (TimeEntry timeEntry : timePeriod.getTimeEntries()) {
            if (timeEntry.getDate().equals(date)) {
                dayTotalHours += timeEntry.getHours();
            }
        }
        return dayTotalHours;
    }

    public static Double getDayTotalHours(List<TimeEntryToSave> timeEntriesToSave, Date date) {
        Double dayTotalHours = 0.00;
        for (TimeEntryToSave toSave : timeEntriesToSave) {
            if (toSave.getTimeEntry().getDate().equals(date)) {
                dayTotalHours += toSave.getTimeEntry().getHours();
            }
        }
        return dayTotalHours;
    }

    public static List<TimeEntryToSave> getTimeEntriesToSave(List<TimeEntryToSave> timeEntriesToSave, Date date) {
        List<TimeEntryToSave> result = new ArrayList<>();
        for (TimeEntryToSave toSave : timeEntriesToSave) {
            if (toSave.getTimeEntry().getDate().equals(date)) {
                result.add(toSave);
            }
        }
        return result;
    }

    public static List<TimeEntry> getTimeEntries(TimePeriod timePeriod, Date date) {
        List<TimeEntry> timeEntries = new ArrayList<>();
        for (TimeEntry timeEntry : timePeriod.getTimeEntries()) {
            if (timeEntry.getDate().equals(date)) {
                timeEntries.add(timeEntry);
            }
        }
        return timeEntries;
    }

    public static List<TimeEntry> getTimeEntries(List<TimeEntryToSave> entriesToSave) {
        List<TimeEntry> timeEntries = new ArrayList<>();
        for (TimeEntryToSave toSave : entriesToSave) {
            timeEntries.add(toSave.getTimeEntry());
        }
        return timeEntries;
    }

    public static int countAllowedWorkOrders(List<WorkOrder> workOrders) {
        int counter = 0;
        for (WorkOrder wo : workOrders) {
            counter += (wo.getTimeEntryAllowed() ? 1 : 0);
        }
        return counter;
    }

    public static WorkOrder getFirstAllowedWorkOrder(List<WorkOrder> workOrders) {
        for (WorkOrder wo : workOrders) {
            if (wo.getTimeEntryAllowed()) {
                return wo;
            }
        }
        return null;
    }

    public static TimePeriod autoChoseTimePeriod(WorkOrder workOrder) {
        List<TimePeriod> timePeriods = workOrder.getTimePeriods();

        Date rightNow = DateUtil.getCurrentDate();

        for (TimePeriod period : timePeriods) {
            if (DateUtil.isDateInsidePeriod(rightNow, period.getStartDate(), period.getEndDate()) &&
                    checkIsEditableTimeEntries(period.getTimeEntries())) {
                return period;
            }
        }

        TimePeriod mostRecentTimePeriod = Collections.max(timePeriods, new TimePeriodComparator());
        return mostRecentTimePeriod;
    }

    public static boolean isUnderLimit(WorkOrder workOrder,int position)
    {
        List<TimePeriod> timePeriods = workOrder.getTimePeriods();
        TimePeriod timePeriod=null;
        Date rightNow = DateUtil.getCurrentDate();
        try {

            timePeriod=timePeriods.get(position);
            if (DateUtil.isDateInsidePeriod(rightNow, timePeriod.getStartDate(), timePeriod.getEndDate()) &&
                    checkIsEditableTimeEntries(timePeriod.getTimeEntries())) {
                return true;
            }
        }catch (IndexOutOfBoundsException e)
        {
            return false;
        }
        return false;
    }
    public static TimeEntryToSave findTimeEntryToSaveOnTaskId(List<TimeEntryToSave> timeEntriesToSave, String timeTaskIdForSavingNote) {
        for (TimeEntryToSave entryToSave : timeEntriesToSave) {
            if (entryToSave.getTimeEntry().getTaskId().equals(timeTaskIdForSavingNote)) {
                return entryToSave;
            }
        }
        return null;
    }

    public static class TimePeriodComparator implements Comparator<TimePeriod> {
        @Override
        public int compare(TimePeriod lhs, TimePeriod rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            }

            if (lhs != null && rhs != null) {
                return lhs.getEndDate().compareTo(rhs.getEndDate());
            }

            if (lhs == null) {
                return 1;
            }

            if (rhs == null) {
                return -1;
            }

            return 0;
        }
    }


    // ===========================================================================





}

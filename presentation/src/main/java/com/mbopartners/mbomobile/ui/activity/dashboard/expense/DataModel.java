package com.mbopartners.mbomobile.ui.activity.dashboard.expense;

import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataModel {
    private String periodStr;
    private List<ExpenseTimesheetItem> expenseItemList;

    public String getPeriodStr() {
        return periodStr;
    }

    public List<ExpenseTimesheetItem> getExpenseItemList() {
        return expenseItemList;
    }

    public void setExpenseItemList(List<ExpenseTimesheetItem> expenseItemList) {

//       try
//        {
//            Collections.reverse(expenseItemList);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        this.expenseItemList = expenseItemList;
        this.periodStr = generateExpensesPeriodStr(expenseItemList);
    }

    private String generateExpensesPeriodStr(List<ExpenseTimesheetItem> expenseItemList) {
        String period = "";
        if (expenseItemList != null && !expenseItemList.isEmpty()) {
            List<Date> timeEntriesDates = new ArrayList<>();
            for (ExpenseTimesheetItem timesheetItem : expenseItemList) {
                timeEntriesDates.add(timesheetItem.getVirtualDate());
            }

            Date minDate = Collections.min(timeEntriesDates);
            Date maxDate = Collections.max(timeEntriesDates);

            period = DateUtil.getFullFormattedPeriodAsString(minDate, maxDate);
        }

        return period;
    }
}
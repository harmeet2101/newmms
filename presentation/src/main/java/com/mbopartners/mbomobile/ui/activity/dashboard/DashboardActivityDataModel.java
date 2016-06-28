package com.mbopartners.mbomobile.ui.activity.dashboard;

import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DashboardActivityDataModel {
    private List<DashboardField> dashboardData;
    private List<WorkOrder> workOrders;
    private List<ExpenseTimesheetItem> expenses;

    public DashboardActivityDataModel() {
        initModel();
    }

    public void initModel() {
        initDashboardModel();
        initWorkOrderModel();
        initExpensesModel();
    }

    public void onAllDataLoadingFailed() {
        dashboardDataLoadingFailed();
        workOrdersDataLoadingFailed();
        expensesDataLoadingFailed();
    }

    public void onDataLoadingComplete() {

    }

    public void dashboardDataLoadingFailed(){
        dashboardData = new ArrayList<>();
    }

    public void workOrdersDataLoadingFailed(){
        workOrders = new ArrayList<>();
    }

    public void expensesDataLoadingFailed(){
        expenses = new ArrayList<>();
    }

    public void initDashboardModel(){
        dashboardData = null;
    }

    public void initWorkOrderModel(){
        workOrders = null;
    }

    public void initExpensesModel(){
        expenses = null;
    }

    public List<DashboardField> getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(List<DashboardField> fields) {
        if (this.dashboardData != null) {
            this.dashboardData.clear();
            this.dashboardData.addAll(fields);
        } else {
            dashboardData = fields;
        }
    }

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.clear();
            this.workOrders.addAll(workOrders);
        } else {
            this.workOrders = workOrders;
        }
    }

    public void setWorkOrders(WorkOrder[] workOrders) {
        this.workOrders = Arrays.asList(workOrders);
    }

    public List<ExpenseTimesheetItem> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseTimesheetItem> expenses) {
        if (this.expenses != null) {
            this.expenses.clear();
            this.expenses.addAll(expenses);
        } else {
            this.expenses = expenses;
        }
    }
}

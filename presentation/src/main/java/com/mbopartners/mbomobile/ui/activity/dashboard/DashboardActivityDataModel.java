package com.mbopartners.mbomobile.ui.activity.dashboard;

import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DashboardActivityDataModel {
    private List<DashboardField> dashboardData;
    private List<WorkOrder> workOrders;
    private List<ExpenseTimesheetItem> expenses;
    private List<BusinessCenter> businessCenterList;
    private List<PayrollSummary> payrollSummaryList;
    private List<PayrollTransactions> payrollTransactionsList;

    public DashboardActivityDataModel() {
        initModel();
    }

    public void initModel() {
        initDashboardModel();
        initWorkOrderModel();
        initExpensesModel();
        initBusinessModel();
        initPayrollModel();
        initTransactionModel();
    }

    public void onAllDataLoadingFailed() {
        dashboardDataLoadingFailed();
        workOrdersDataLoadingFailed();
        expensesDataLoadingFailed();
        businessCenterDataLoadingFailed();
        payrollSummaryDataLoadingFailed();
        payrollTransactionDataLoadingFailed();
    }

    private void payrollTransactionDataLoadingFailed() {

        payrollTransactionsList=new ArrayList<>();
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

    public void initPayrollModel(){
        payrollSummaryList = null;
    }

    public void initTransactionModel()
    {
        payrollTransactionsList=null;
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

    public void initBusinessModel(){
        businessCenterList=null;
    }
    public void businessCenterDataLoadingFailed()
    {
        businessCenterList=new ArrayList<>();
    }
    public void payrollSummaryDataLoadingFailed()
    {
        payrollSummaryList=new ArrayList<>();
    }

    public List<BusinessCenter> getBusinessCenterList(){
        return businessCenterList;
    }

    public List<PayrollSummary> getPayrollSummaryList(){
        return payrollSummaryList;
    }

    public void     setBusinessData(List<BusinessCenter> fields) {
        if (this.businessCenterList != null) {
            this.businessCenterList.clear();
            this.businessCenterList.addAll(fields);
        } else {
            businessCenterList = fields;
        }
    }


    public void setPayrollSummaryData(List<PayrollSummary> payrollSummaryData) {
        if (this.payrollSummaryList != null) {
            this.payrollSummaryList.clear();
            this.payrollSummaryList.addAll(payrollSummaryData);
        } else {
            payrollSummaryList = payrollSummaryData;
        }    }


    public List<PayrollTransactions> getPayrollTransactionsList(){
        return payrollTransactionsList;
    }

    public void setPayrollTransactionsData(List<PayrollTransactions> payrollTransactionses)
    {
        if (this.payrollTransactionsList != null) {
            this.payrollTransactionsList.clear();
            this.payrollTransactionsList.addAll(payrollTransactionses);
        } else {
            payrollTransactionsList = payrollTransactionses;
        }
    }
}

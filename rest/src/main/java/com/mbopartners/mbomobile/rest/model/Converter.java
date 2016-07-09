package com.mbopartners.mbomobile.rest.model;

import com.mbopartners.mbomobile.data.db.generated.model.TableBusinessManager;
import com.mbopartners.mbomobile.data.db.generated.model.TableCompany;
import com.mbopartners.mbomobile.data.db.generated.model.TableDashboard;
import com.mbopartners.mbomobile.data.db.generated.model.TableDashboardField;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseData;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseField;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseFieldValue;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseType;
import com.mbopartners.mbomobile.data.db.generated.model.TableReceipt;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimeEntry;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimePeriod;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimeTask;
import com.mbopartners.mbomobile.data.db.generated.model.TableUserProfile;
import com.mbopartners.mbomobile.data.db.generated.model.TableWorkOrder;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessCenter;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableNextPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollSummary;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;
import com.mbopartners.mbomobile.rest.model.response.BusinessManager;
import com.mbopartners.mbomobile.rest.model.response.Company;
import com.mbopartners.mbomobile.rest.model.response.Dashboard;
import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.ExpenseData;
import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.rest.model.response.ExpenseFieldValue;
import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.model.response.TimePeriod;
import com.mbopartners.mbomobile.rest.model.response.TimeTask;
import com.mbopartners.mbomobile.rest.model.response.UserProfile;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.NextPayment;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PreviousPayment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;


public class Converter {
    /*
     *
     * WEB to DB entities conversions
     *
     */

    public static TableUserProfile toTable(UserProfile userProfile) {
        TableUserProfile tableUserProfile = new TableUserProfile(
                null,
                userProfile.getMboId(),
                userProfile.getNumber(),
                userProfile.getStatus(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getEmail(),
                userProfile.getNonbillableAllowed());
        return tableUserProfile;
    }

    public static TableBusinessManager toTable(BusinessManager businessManager) {
        TableBusinessManager tableBusinessManager = new TableBusinessManager(
                null,
                businessManager.getMboId(),
                businessManager.getFirstName(),
                businessManager.getLastName(),
                businessManager.getEmail());
        return tableBusinessManager;
    }

    public static TableBusinessCenter toTable_businessCenter(BusinessCenter businessCenter) {
        TableBusinessCenter tableBusinessCenter = new TableBusinessCenter(
                null,
                businessCenter.getId(),
                businessCenter.getName(),
                businessCenter.getMboId(),
                businessCenter.getBalance());
        return tableBusinessCenter;
    }

    public static TablePayrollSummary toTable_payroll_summary(PayrollSummary payrollSummary) {
        TablePayrollSummary tablePayrollSummary = new TablePayrollSummary(
                null,
                payrollSummary.getBalance(),
                payrollSummary.getId(),
                payrollSummary.getMboId(),
                payrollSummary.getName());
        return tablePayrollSummary;
    }

    public static TableNextPayment toTable_payroll_nexPayment(long nextPaymentRowId,NextPayment nextPayment) {
        TableNextPayment tableNextPayment = new TableNextPayment(
                null,
                nextPayment.getAmount(),
                nextPayment.getBusinessCenterId(),
                nextPayment.getCalculationMethod(),
                nextPayment.getEndDate(),
                nextPayment.getStartDate(),
                nextPayment.getFrequency(),
                nextPayment.getId(),
                nextPayment.getMboId(),nextPaymentRowId);
        return tableNextPayment;
    }
    public static TablePreviousPayment toTable_payroll_prevPayment(long previousPaymentRowId,PreviousPayment previousPayment) {
        TablePreviousPayment tablePreviousPayment = new TablePreviousPayment(
                null,
                previousPayment.getBusinessCenterId(),
                previousPayment.getdate(),
                previousPayment.getId(),
                previousPayment.getMboId(),previousPaymentRowId);
        return tablePreviousPayment;
    }
    public static TableDashboard toTable(Dashboard dashboard) {
        TableDashboard  tableDashboard = new TableDashboard(null, dashboard.getPurpose());
        return tableDashboard;
    }
    public static TableDashboardField toTable(long dashboardID, DashboardField dashboardField) {
        TableDashboardField tableDashboardField = new TableDashboardField(
                null,
                dashboardField.getName(),
                dashboardField.getValue(),
                dashboardID
        );
        return tableDashboardField;
    }

    public static TableWorkOrder toTable(WorkOrder workOrder, long companyId) {
        TableWorkOrder tableWorkOrder = new TableWorkOrder(
                null,
                workOrder.getId(),
                workOrder.getName(),
                workOrder.getTimeEntryAllowed(),
                workOrder.getBillableExpensesAllowed(),
                companyId);

        return tableWorkOrder;
    }

    public static TableCompany toTable(Company company) {
        return new TableCompany(null,company.getId(), company.getName());
    }

    public static TableTimeEntry toTable(long timePeriodId, TimeEntry timeEntry) {
        TableTimeEntry tableTimeEntry = new TableTimeEntry(
                null,
                AbstractDbHandler.REC_STATE__CREATE__SUCCESS,
                timeEntry.getId(),
                timeEntry.getWorkOrderId(),
                timeEntry.getTaskId(),
                timeEntry.getHours(),
                timeEntry.getDate(),
                timeEntry.getNote(),
                timeEntry.getEditable(),
                timeEntry.getVersion(),
                timePeriodId);
        return tableTimeEntry;
    }
    
    public static TableTimeTask toTable(long workOrderId, TimeTask timeTask) {
        TableTimeTask tableTimeTask = new TableTimeTask(
                null,
                timeTask.getId(),
                timeTask.getName(),
                workOrderId );
        return tableTimeTask;
    }

    public static TableTimePeriod toTable(long workOrderId, TimePeriod timePeriod) {
        TableTimePeriod tableTimePeriod = new TableTimePeriod(
                null,
                timePeriod.getId(),
                timePeriod.getName(),
                timePeriod.getStartDate(),
                timePeriod.getEndDate(),
                timePeriod.getCurrent(),
                timePeriod.getSubmittable(),
                timePeriod.getWorkOrderId(),
                workOrderId
        );
        return tableTimePeriod;
    }

    public static TableExpense toTable(Expense expense) {
        TableExpense table = new TableExpense(
                null,
                expense.getMboId(),
                expense.getMboWorkOrderId(),
                expense.getDescription(),
                expense.getMboAssociateId(),
                expense.getAmount(),
                expense.getEditable(),
                expense.getBillable(),
                expense.getVersion(),
                expense.getMboExpenseTypeId(),expense.getLastChangedDate()
        );
        return table;
    }

    public static TableExpenseData toTable(long expenseId, ExpenseData expenseData) {
        TableExpenseData table = new TableExpenseData(
                null,
                expenseData.getName(),
                expenseData.getValue(),
                expenseId
        );
        return table;
    }

    public static TableReceipt toTable(long expenseId, Receipt receipt) {
        TableReceipt tableReceipt = new TableReceipt(
                null,
                AbstractDbHandler.REC_STATE__CREATE__SUCCESS,
                receipt.getFilename(),
                receipt.getMboExpenseId(),
                receipt.getCreationDate(),
                receipt.getThumbnailPath(),
                receipt.getVersion(),
                expenseId
        );
        return tableReceipt;
    }

    public static TableExpenseType toTable(ExpenseType expenseType) {
        TableExpenseType table = new TableExpenseType(
                expenseType.getMboId(),
                expenseType.getName()
        );
        return table;
    }

    public static TableExpenseField toTable(ExpenseField expenseField, String expenseTypeId) {
        TableExpenseField table = new TableExpenseField(
          null,
                expenseField.getMboId(),
                expenseField.getType(),
                expenseField.getName(),
                expenseField.getRequired(),
                expenseField.getMaxLength(),
                expenseTypeId
        );
        return table;
    }

    public static TableExpenseFieldValue toTable(ExpenseFieldValue fieldValue, long expenseFieldId) {
        TableExpenseFieldValue table = new TableExpenseFieldValue(
                null,
                fieldValue.getMboId(),
                fieldValue.getValue(),
                expenseFieldId
        );
        return table;
    }

    // ================================================================================
    //
    //
    //
    // ================================================================================

    public static BusinessManager toWeb_BusinessManager(TableBusinessManager table) {
        BusinessManager businessManager = new BusinessManager(
                table.getMboId(),
                table.getFirstName(),
                table.getLastName(),
                table.getEmail());
        return businessManager;
    }

    public static DashboardField toWeb(TableDashboardField table) {
        DashboardField dashboardField = new DashboardField(table.getName(), table.getValue());
        return dashboardField;
    }

    public static BusinessCenter toWeb_businessCenter(TableBusinessCenter table) {
        BusinessCenter businessCenter = new BusinessCenter(table.getBusinessId(),table.getName(),table.getMboId(),table.getBalance());
        return businessCenter;
    }

    public static PayrollSummary toWeb_PayrollSummary(TablePayrollSummary table) {
        PayrollSummary payrollSummary = new PayrollSummary(table.getSummaryId(),table.getName(),table.getMboId(),table.getBalance(),toWeb_nextPayment(table.getNext_payroll()),toWeb_previousPayment(table.getLast_payroll()));
        return payrollSummary;
    }
    public static NextPayment toWeb_nextPayment(TableNextPayment table) {
        NextPayment nextPayment = new NextPayment(table.getAmount(),table.getBusinessCenterId(),table.getCalculationMethod(),table.getEndDate(),table.getStartDate(),
                table.getFrequency(),table.getNextPaymentId(),table.getMboId());
        return nextPayment;
    }
    public static PreviousPayment toWeb_previousPayment(TablePreviousPayment table) {
        PreviousPayment previousPayment = new PreviousPayment(table.getBusinessCenterId(),table.getDate(),
                table.getPreviousPaymentId(),table.getMboId());
        return previousPayment;
    }

    public static WorkOrder toWeb(TableWorkOrder table) {
        WorkOrder workOrder = new WorkOrder(
                table.getMboId(),
                table.getName(),
                toWeb(table.getTableCompany()),
                table.getTimeEntryAllowed(),
                table.getBillableExpensesAllowed(),
                toWeb_TimePeriod(table.getTimePeriods()),
                toWeb_TimeTask(table.getTimeTasks())
                );
        return workOrder;
    }

    public static Company toWeb(TableCompany table) {
        Company company = new Company(table.getMboId(), table.getName());
        return company;
    }

    public static TimePeriod toWeb(TableTimePeriod table) {
        table.resetTimeEntries();
        TimePeriod timePeriod = new TimePeriod(
                table.getMboId(),
                table.getName(),
                table.getStartDate(),
                table.getEndDate(),
                table.getCurrent(),
                table.getSubmittable(),
                table.getMboWorkOrderId(),
                toWeb_TimeEntry(table.getTimeEntries())
        );
        return timePeriod;
    }

    public static TimeEntry toWeb(TableTimeEntry table) {
        TimeEntry timeEntry = new TimeEntry(
                table.getMboId(),
                table.getWorkOrderId(),
                table.getTaskID(),
                table.getHours(),
                table.getDate(),
                table.getNote(),
                table.getEditable(),
                table.getVersion()
        );
        return timeEntry;
    }

    public static TimeTask toWeb(TableTimeTask table) {
        TimeTask timeTask = new TimeTask(
                table.getMboId(),
                table.getName()
        );
        return timeTask;
    }

    public static ExpenseField toWeb(TableExpenseField table) {
        List<ExpenseFieldValue> possibleFieldValues;
        List<TableExpenseFieldValue> fieldValues = table.getValues();
        if (fieldValues == null || fieldValues.isEmpty()) {
            possibleFieldValues = new ArrayList<>();
        } else {
            possibleFieldValues = toWeb_ExpenseFieldValue(fieldValues);
        }

        ExpenseField expenseField = new ExpenseField(
                table.getMboId(),
                table.getType(),
                table.getName(),
                table.getRequired(),
                table.getMaxLength(),
                possibleFieldValues
        );
        return expenseField;
    }

    public static ExpenseType toWeb(TableExpenseType tableRecord) {
        ExpenseType expenseType = new ExpenseType(
                tableRecord.getMboId(),
                tableRecord.getName(),
                toWeb_ExpenseField(tableRecord.getFields())
                );
        return expenseType;
    }

    public static ExpenseFieldValue toWeb(TableExpenseFieldValue table) {
        ExpenseFieldValue expenseFieldValue = new ExpenseFieldValue(
                table.getMboId(),
                table.getValue()
        );
        return expenseFieldValue;
    }

    public static ExpenseData toWeb(TableExpenseData table) {
        ExpenseData expenseData =  new ExpenseData(
                table.getName(),
                table.getValue()
        );
        return expenseData;
    }

    public static Receipt toWeb(TableReceipt table) {
        Receipt receipt = new Receipt(
                table.getFilename(),
                table.getMboExpenseId(),
                table.getThumbnailPath(),
                table.getCreationDate(),
                table.getVersion()
        );
        return receipt;
    }

    // ================================================================================
    //
    // Lists conversion
    //
    // ================================================================================

    public static List<DashboardField> toWeb_DashboardField(List<TableDashboardField> table) {
        List<DashboardField> dashboardFields = new ArrayList<>(table.size());
        for (TableDashboardField tableField : table) {
            dashboardFields.add(toWeb(tableField));
        }
        return dashboardFields;
    }

    public static List<BusinessCenter> toWeb_BusinessCenterField(List<TableBusinessCenter> table) {
        List<BusinessCenter> businessCenter = new ArrayList<>(table.size());
        for (TableBusinessCenter tableField : table) {
            businessCenter.add(toWeb_businessCenter(tableField));
        }
        return businessCenter;
    }

    public static List<PayrollSummary> toWeb_PayrollSummaryField(List<TablePayrollSummary> table) {
        List<PayrollSummary> payrollSummaries = new ArrayList<>(table.size());
        for (TablePayrollSummary tableField : table) {
            payrollSummaries.add(toWeb_PayrollSummary(tableField));

        }
        return payrollSummaries;
    }



    public static List<TimeEntry> toWeb_TimeEntry(List<TableTimeEntry> table) {
        List<TimeEntry> timeEntries = new ArrayList<>(table.size());

        for (TableTimeEntry tableTimeEntry : table) {
            timeEntries.add(toWeb(tableTimeEntry));
        }
        return timeEntries;
    }

    public static List<TimePeriod> toWeb_TimePeriod(List<TableTimePeriod> table) {
        List<TimePeriod> timePeriods = new ArrayList<>(table.size());

        for (TableTimePeriod tableTimePeriod : table) {
            timePeriods.add(toWeb(tableTimePeriod));
        }
        return timePeriods;
    }

    public static List<TimeTask> toWeb_TimeTask(List<TableTimeTask> table) {
        List<TimeTask> timeTasks = new ArrayList<>(table.size());

        for (TableTimeTask tableTimeTask : table) {
            timeTasks.add(toWeb(tableTimeTask));
        }
        return timeTasks;
    }

    public static List<WorkOrder> toWeb_WorkOrder(List<TableWorkOrder> table) {
        List<WorkOrder> workOrders = new ArrayList<>(table.size());

        for (TableWorkOrder tableWorkOrder : table) {
            workOrders.add(toWeb(tableWorkOrder));
        }
        return workOrders;
    }

    public static List<ExpenseType> toWeb_ExpenseType(List<TableExpenseType> table) {
        List<ExpenseType> expenseTypeList = new ArrayList<>(table.size());
        for (TableExpenseType tableRecord: table) {
            expenseTypeList.add(toWeb(tableRecord));
        }
        return expenseTypeList;
    }

    public static List<ExpenseField> toWeb_ExpenseField(List<TableExpenseField> table) {
        List<ExpenseField> expenseFieldList = new ArrayList<>(table.size());
        for (TableExpenseField field : table) {
            expenseFieldList.add(toWeb(field));
        }
        return expenseFieldList;
    }

    public static List<ExpenseFieldValue> toWeb_ExpenseFieldValue(List<TableExpenseFieldValue> table) {
        List<ExpenseFieldValue> expenseFieldValueList = new ArrayList<>(table.size());
        for (TableExpenseFieldValue fieldValue : table) {
            expenseFieldValueList.add(toWeb(fieldValue));
        }
        return expenseFieldValueList;
    }

    public static List<ExpenseData> toWeb_ExpenseData(List<TableExpenseData> table) {
        List<ExpenseData> expenseData = new ArrayList<>(table.size());
        for (TableExpenseData tableExpenseData: table) {
            expenseData.add(toWeb(tableExpenseData));
        }
        return expenseData;
    }

    public static Map<String, String> to_ExpenseData_Map(List<TableExpenseData> table) {
        Map<String, String> expenseDataMap = new HashMap<>(table.size());
        for (TableExpenseData tableExpenseData: table) {
            expenseDataMap.put(tableExpenseData.getName(), tableExpenseData.getValue());
        }
        return expenseDataMap;
    }

    public static List<Receipt> toWeb_Receipts(List<TableReceipt> table ) {
        List<Receipt> receipts = new ArrayList<>(table.size());
        for (TableReceipt tableReceipt : table) {
            receipts.add(toWeb(tableReceipt));
        }
        return receipts;
    }

}

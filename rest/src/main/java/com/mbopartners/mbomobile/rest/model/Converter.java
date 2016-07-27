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
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessExpenses;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessPayrollTaxes;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableExpenseReimbersements;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableNextPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollSummary;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollTransactions;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonDeposits;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonGrossAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonPayrollTaxes;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonalWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableSummaryBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableSummaryPayrollAmount;
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
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessExpenses;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessPayrollTaxes;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessWithHolding;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.ExpenseReimbursement;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.NextPayment;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollAmount;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonDeposits;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonGrossAmount;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonPayrollTaxes;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
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
                /*payrollSummary.getMboId(),*/
                payrollSummary.getName());
        return tablePayrollSummary;
    }

    public static TablePayrollTransactions toTable_payroll_transactions(PayrollTransactions payrollTransactions) {
        TablePayrollTransactions tablePayrollTransactions = new TablePayrollTransactions(
                null,payrollTransactions.getId(),payrollTransactions.getMboId(),payrollTransactions.getBusinessCenterId(),payrollTransactions.getDate());
        return tablePayrollTransactions;
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

    public static TableBusinessWithHolding toTable_payroll_businessWithHolding(long businessWithHoldingRowId,BusinessWithHolding businessWithHolding) {
        TableBusinessWithHolding tableBusinessWithHolding = new TableBusinessWithHolding(
                null,
                businessWithHoldingRowId);
        return tableBusinessWithHolding;
    }
    public static TableSummaryBusinessWithHolding toTable_payroll_summary_businessWithHolding(long businessWithHoldingRowId,BusinessWithHolding businessWithHolding) {
        TableSummaryBusinessWithHolding tableBusinessWithHolding = new TableSummaryBusinessWithHolding(
                null,
                businessWithHoldingRowId);
        return tableBusinessWithHolding;
    }

    public static TablePersonalWithHolding toTable_payroll_personWithHolding(long personWithHoldingRowId,PersonWithHolding personWithHolding) {
        TablePersonalWithHolding tablePersonalWithHolding = new TablePersonalWithHolding(
                null,
                personWithHoldingRowId);
        return tablePersonalWithHolding;
    }

    public static TablePayrollAmount toTable_payroll_business_payrollAmount(long payrollAmountRowId,PayrollAmount payrollAmount) {
        TablePayrollAmount tablePayrollAmount = new TablePayrollAmount(
                null,
                payrollAmount.getAmount(),payrollAmount.getAmountMtd(),payrollAmount.getAmountYtd(),payrollAmount.getName(),
                payrollAmountRowId);
        return tablePayrollAmount;
    }

    public static TableSummaryPayrollAmount toTable_payroll_summary_business_payrollAmount(long payrollAmountRowId,PayrollAmount payrollAmount) {
        TableSummaryPayrollAmount tablePayrollAmount = new TableSummaryPayrollAmount(
                null,
                payrollAmount.getAmount(),payrollAmount.getAmountMtd(),payrollAmount.getAmountYtd(),payrollAmount.getName(),
                payrollAmountRowId);
        return tablePayrollAmount;
    }

    public static TablePersonGrossAmount toTable_payroll_person_grossAmount(long payrollAmountRowId,PersonGrossAmount personGrossAmount) {
        TablePersonGrossAmount tablePersonGrossAmount = new TablePersonGrossAmount(
                null,
                personGrossAmount.getAmount(),personGrossAmount.getAmountMtd(),personGrossAmount.getAmountYtd(),personGrossAmount.getName(),
                payrollAmountRowId);
        return tablePersonGrossAmount;
    }

    public static TableBusinessExpenses toTable_payroll_business_expense(long payrollAmountRowId,BusinessExpenses businessExpenses) {
        TableBusinessExpenses tablePayrollAmount = new TableBusinessExpenses(
                null,
                businessExpenses.getAmount(),businessExpenses.getAmountMtd(),businessExpenses.getAmountYtd(),businessExpenses.getName(),
                payrollAmountRowId);
        return tablePayrollAmount;
    }

    public static TableBusinessPayrollTaxes toTable_payroll_business_payrollTaxes(long payrollAmountRowId,BusinessPayrollTaxes businessPayrollTaxes) {
        TableBusinessPayrollTaxes tableBusinessPayrollTaxes = new TableBusinessPayrollTaxes(
                null,
                businessPayrollTaxes.getAmount(),businessPayrollTaxes.getAmountMtd(),businessPayrollTaxes.getAmountYtd(),businessPayrollTaxes.getName(),
                payrollAmountRowId);
        return tableBusinessPayrollTaxes;
    }
    public static TablePersonPayrollTaxes toTable_payroll_person_payrollTaxes(long payrollAmountRowId,PersonPayrollTaxes personPayrollTaxes) {
        TablePersonPayrollTaxes tablePersonPayrollTaxes = new TablePersonPayrollTaxes(
                null,
                personPayrollTaxes.getAmount(),personPayrollTaxes.getAmountMtd(),personPayrollTaxes.getAmountYtd(),personPayrollTaxes.getName(),
                payrollAmountRowId);
        return tablePersonPayrollTaxes;
    }

    public static TableExpenseReimbersements toTable_payroll_person_expense_reimbersements(long payrollAmountRowId,ExpenseReimbursement expenseReimbursement) {
        TableExpenseReimbersements tableExpenseReimbersements = new TableExpenseReimbersements(
                null,
                expenseReimbursement.getAmount(),expenseReimbursement.getAmountMtd(),expenseReimbursement.getAmountYtd(),expenseReimbursement.getName(),
                payrollAmountRowId);
        return tableExpenseReimbersements;
    }

    public static TablePersonDeposits toTable_payroll_person_deposits(long depositsRowId,PersonDeposits personDeposits) {
        TablePersonDeposits tablePersonDeposits = new TablePersonDeposits(
                null,
                personDeposits.getAmount(),personDeposits.getName(),
                depositsRowId);
        return tablePersonDeposits;
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
        PayrollSummary payrollSummary = new PayrollSummary(table.getSummaryId(),table.getName(),/*table.getMboId(),*/table.getBalance(),toWeb_nextPayment(table.getNext_payroll()),toWeb_previousPayment(table.getLast_payroll()));
        return payrollSummary;
    }
    public static PayrollTransactions toWeb_PayrollTransactions(TablePayrollTransactions table) {
        PayrollTransactions payrollTransactions =
                new PayrollTransactions(table.getTransactionId(),table.getMboId(),table.getBusinessCenterId(),table.getDate(),toWeb_businessWithHolding(table.getBusinessWithholding()),toWeb_personWithHolding(table.getPersonalWithholding()));
        return payrollTransactions;
    }
    public static NextPayment toWeb_nextPayment(TableNextPayment table) {
        NextPayment nextPayment=null;
        if(table!=null) {
            nextPayment = new NextPayment(table.getAmount(), table.getBusinessCenterId(), table.getCalculationMethod(), table.getEndDate(), table.getStartDate(),
                    table.getFrequency(), table.getNextPaymentId(), table.getMboId());
            return nextPayment;
        }else
        return nextPayment;
    }
    public static PreviousPayment toWeb_previousPayment(TablePreviousPayment table) {
        PreviousPayment previousPayment = null;
        if (table != null) {
/*            previousPayment=new PreviousPayment(table.getBusinessCenterId(), table.getDate(),
                    table.getPreviousPaymentId(), table.getMboId(),toWeb_businessWithHolding(table.getBusinessWithholding()),toWeb_personWithHolding(table.getPersonalWithholding()));*/

            previousPayment=new PreviousPayment(table.getBusinessCenterId(), table.getDate(),
                    table.getPreviousPaymentId(), table.getMboId(),toWeb_summarybusinessWithHolding(table.getSummaryBusinessWithHolding()));

            return previousPayment;
        }else
            return previousPayment;
    }

    public static BusinessWithHolding toWeb_businessWithHolding(TableBusinessWithHolding table) {
        BusinessWithHolding businessWithHolding = null;
        if (table != null) {
            businessWithHolding = new BusinessWithHolding(toWeb_payrollPayment(table.getPayrollAmount()),toWeb_BusinessExpenseField(table.getBusinessExpenses()),toWeb_BusinessPayrollTaxesField(table.getPayrollTaxes()));
            return businessWithHolding;
        }else
            return businessWithHolding;
    }

    public static BusinessWithHolding toWeb_summarybusinessWithHolding(TableSummaryBusinessWithHolding table) {
        BusinessWithHolding businessWithHolding = null;
        if (table != null) {
            businessWithHolding = new BusinessWithHolding(toWeb_summarypayrollPayment(table.getPayrollAmount()));
            return businessWithHolding;
        }else
            return businessWithHolding;
    }

    public static PersonWithHolding toWeb_personWithHolding(TablePersonalWithHolding table) {
        PersonWithHolding personWithHolding = null;
        if (table != null) {
            personWithHolding = new PersonWithHolding(toWeb_payrollPersonGrossAmount(table.getGrossAmount()),
                    toWeb_PersonPayrollTaxesField(table.getPayrollTaxes()),toWeb_PersonExpenseReimbersementField(table.getExpenseReimbursements()),toWeb_PersonDepositsField(table.getDeposits()));
            return personWithHolding;
        }else
            return personWithHolding;
    }
    public static PayrollAmount toWeb_payrollPayment(TablePayrollAmount table) {
        PayrollAmount payrollAmount = null;
        if (table != null) {
            payrollAmount = new PayrollAmount(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return payrollAmount;
        }else
            return payrollAmount;
    }

    public static PayrollAmount toWeb_summarypayrollPayment(TableSummaryPayrollAmount table) {
        PayrollAmount payrollAmount = null;
        if (table != null) {
            payrollAmount = new PayrollAmount(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return payrollAmount;
        }else
            return payrollAmount;
    }
    public static PersonPayrollTaxes toWeb_person_payrollPayment(TablePersonPayrollTaxes table) {
        PersonPayrollTaxes personPayrollTaxes = null;
        if (table != null) {
            personPayrollTaxes = new PersonPayrollTaxes(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return personPayrollTaxes;
        }else
            return personPayrollTaxes;
    }

    public static PersonGrossAmount toWeb_payrollPersonGrossAmount(TablePersonGrossAmount table) {
        PersonGrossAmount personGrossAmount = null;
        if (table != null) {
            personGrossAmount = new PersonGrossAmount(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return personGrossAmount;
        }else
            return personGrossAmount;
    }

    public static PersonDeposits toWeb_payrollPersonDeposits(TablePersonDeposits table) {
        PersonDeposits personDeposits = null;
        if (table != null) {
            personDeposits = new PersonDeposits(table.getAmount(), table.getName());
            return personDeposits;
        }else
            return personDeposits;
    }

    public static BusinessExpenses toWeb_businessExpenses(TableBusinessExpenses table) {
        BusinessExpenses businessExpenses = null;
        if (table != null) {
            businessExpenses = new BusinessExpenses(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return businessExpenses;
        }else
            return businessExpenses;
    }

    public static BusinessPayrollTaxes toWeb_businessPayrollTaxes(TableBusinessPayrollTaxes table) {
        BusinessPayrollTaxes businessPayrollTaxes = null;
        if (table != null) {
            businessPayrollTaxes = new BusinessPayrollTaxes(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return businessPayrollTaxes;
        }else
            return businessPayrollTaxes;
    }

    public static ExpenseReimbursement toWeb_person_expenseReimbersements(TableExpenseReimbersements table) {
        ExpenseReimbursement expenseReimbursement = null;
        if (table != null) {
            expenseReimbursement = new ExpenseReimbursement(table.getAmount(), table.getAmountMtd(),
                    table.getAmountYtd(), table.getName());
            return expenseReimbursement;
        }else
            return expenseReimbursement;
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
    public static List<PayrollTransactions> toweb_PayrollTransactionsField(List<TablePayrollTransactions> table)
    {
        List<PayrollTransactions> payrollTransactionses = new ArrayList<>(table.size());
        for (TablePayrollTransactions tableField : table) {
            payrollTransactionses.add(toWeb_PayrollTransactions(tableField));

        }
        return payrollTransactionses;
    }


    public static List<BusinessExpenses> toWeb_BusinessExpenseField(List<TableBusinessExpenses> table) {
        List<BusinessExpenses> businessExpenses = new ArrayList<>(table.size());
        for (TableBusinessExpenses tableField : table) {
            businessExpenses.add(toWeb_businessExpenses(tableField));

        }
        return businessExpenses;
    }

    public static List<BusinessPayrollTaxes> toWeb_BusinessPayrollTaxesField(List<TableBusinessPayrollTaxes> table) {
        List<BusinessPayrollTaxes> businessPayrollTaxes = new ArrayList<>(table.size());
        for (TableBusinessPayrollTaxes tableField : table) {
            businessPayrollTaxes.add(toWeb_businessPayrollTaxes(tableField));

        }
        return businessPayrollTaxes;
    }

    public static List<PersonPayrollTaxes> toWeb_PersonPayrollTaxesField(List<TablePersonPayrollTaxes> table) {
        List<PersonPayrollTaxes> personPayrollTaxes = new ArrayList<>(table.size());
        for (TablePersonPayrollTaxes tableField : table) {
            personPayrollTaxes.add(toWeb_person_payrollPayment(tableField));

        }
        return personPayrollTaxes;
    }

    public static List<ExpenseReimbursement> toWeb_PersonExpenseReimbersementField(List<TableExpenseReimbersements> table) {
        List<ExpenseReimbursement> expenseReimbursements = new ArrayList<>(table.size());
        for (TableExpenseReimbersements tableField : table) {
            expenseReimbursements.add(toWeb_person_expenseReimbersements(tableField));

        }
        return expenseReimbursements;
    }

    public static List<PersonDeposits> toWeb_PersonDepositsField(List<TablePersonDeposits> table) {
        List<PersonDeposits> personDeposits = new ArrayList<>(table.size());
        for (TablePersonDeposits tableField : table) {
            personDeposits.add(toWeb_payrollPersonDeposits(tableField));

        }
        return personDeposits;
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

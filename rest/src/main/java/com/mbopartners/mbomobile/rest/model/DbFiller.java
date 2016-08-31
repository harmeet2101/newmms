package com.mbopartners.mbomobile.rest.model;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.TableBusinessManagerDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableCompanyDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableDashboardDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableDashboardFieldDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDataDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseFieldDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseFieldValueDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseTypeDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableReceiptDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimeEntryDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimePeriodDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimeTaskDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableUserProfileDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableWorkOrderDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessCenterDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessExpensesDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessPayrollTaxesDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableExpenseReimbersementsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableNextPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollSummaryDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollTransactionsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonAfterDeductionsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonDepositsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonGrossAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonNetAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonPayCheckAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonPayrollTaxesDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryPayrollAmountDao;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseData;
import com.mbopartners.mbomobile.data.db.generated.model.TableReceipt;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimeEntry;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimePeriod;
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
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonNetAmount;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonPayCheckAmount;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonPayrollTaxes;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonalDeductions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PreviousPayment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

public class DbFiller {
    private static final String TAG = DbFiller.class.getSimpleName();

    public static void clearTablesForWorkOrders(DaoSession daoSession) {
        TableCompanyDao companyDao =  daoSession.getTableCompanyDao();
        companyDao.deleteAll();

        TableTimeEntryDao timeEntryDao = daoSession.getTableTimeEntryDao();
        timeEntryDao.deleteAll();

        TableTimePeriodDao timePeriodDao = daoSession.getTableTimePeriodDao();
        timePeriodDao.deleteAll();

        TableTimeTaskDao timeTaskDao = daoSession.getTableTimeTaskDao();
        timeTaskDao.deleteAll();

        TableWorkOrderDao workOrderDao = daoSession.getTableWorkOrderDao();
        workOrderDao.deleteAll();
    }

    public static void clearTablesForDashboard(DaoSession daoSession) {
        TableDashboardDao tableDashboardDataDao = daoSession.getTableDashboardDao();
        TableDashboardFieldDao tableDashboardFieldDao = daoSession.getTableDashboardFieldDao();

        tableDashboardDataDao.deleteAll();
        tableDashboardFieldDao.deleteAll();
    }

    public static void clearTablesForPayrollTransaction(DaoSession daoSession)
    {
        TablePayrollTransactionsDao tablePayrollTransactionsDao=daoSession.getTablePayrollTransactionsDao();
        tablePayrollTransactionsDao.deleteAll();
    }
    public static void clearTablesForBusinessCenter(DaoSession daoSession) {
        TableBusinessCenterDao tableBusinessCenterDao = daoSession.getTableBusinessCenterDao();
        tableBusinessCenterDao.deleteAll();

        TableBusinessWithHoldingDao tableBusinessWithHoldingDao=daoSession.getTableBusinessWithHoldingDao();
        TablePayrollAmountDao tablePayrollAmountDao=daoSession.getTablePayrollAmountDao();
        TablePersonWithHoldingDao tablePersonWithHoldingDao=daoSession.getTablePersonWithHoldingDao();
        TablePersonGrossAmountDao tablePersonGrossAmountDao=daoSession.getTablePersonGrossAmountDao();
        TableBusinessPayrollTaxesDao tableBusinessPayrollTaxesDao=daoSession.getTableBusinessPayrollTaxesDao();
        TableBusinessExpensesDao tableBusinessExpensesDao=daoSession.getTableBusinessExpensesDao();
        TablePersonPayrollTaxesDao tablePersonPayrollTaxesDao=daoSession.getTablePersonPayrollTaxesDao();

        tableBusinessWithHoldingDao.deleteAll();
        tablePayrollAmountDao.deleteAll();
        tablePersonWithHoldingDao.deleteAll();
        tablePersonGrossAmountDao.deleteAll();
        tableBusinessPayrollTaxesDao.deleteAll();
        tableBusinessExpensesDao.deleteAll();
        tablePersonPayrollTaxesDao.deleteAll();
    }
    public static void clearTablesForPayrollSummary(DaoSession daoSession) {
        TablePayrollSummaryDao tablePayrollSummaryDao = daoSession.getTablePayrollSummaryDao();
        TableNextPaymentDao tableNextPaymentDao=daoSession.getTableNextPaymentDao();
        TablePreviousPaymentDao tablePreviousPaymentDao=daoSession.getTablePreviousPaymentDao();
        TableBusinessWithHoldingDao tableBusinessWithHoldingDao=daoSession.getTableBusinessWithHoldingDao();
        TablePayrollAmountDao tablePayrollAmountDao=daoSession.getTablePayrollAmountDao();
        TablePersonWithHoldingDao tablePersonWithHoldingDao=daoSession.getTablePersonWithHoldingDao();
        TablePersonGrossAmountDao tablePersonGrossAmountDao=daoSession.getTablePersonGrossAmountDao();
        TableBusinessPayrollTaxesDao tableBusinessPayrollTaxesDao=daoSession.getTableBusinessPayrollTaxesDao();
        TableBusinessExpensesDao tableBusinessExpensesDao=daoSession.getTableBusinessExpensesDao();
        TablePersonPayrollTaxesDao tablePersonPayrollTaxesDao=daoSession.getTablePersonPayrollTaxesDao();
        tablePayrollSummaryDao.deleteAll();
        tableNextPaymentDao.deleteAll();
        tablePreviousPaymentDao.deleteAll();
        tableBusinessWithHoldingDao.deleteAll();
        tablePayrollAmountDao.deleteAll();
        tablePersonWithHoldingDao.deleteAll();
        tablePersonGrossAmountDao.deleteAll();
        tableBusinessPayrollTaxesDao.deleteAll();
        tableBusinessExpensesDao.deleteAll();
        tablePersonPayrollTaxesDao.deleteAll();
    }

    public static void clearTablesForExpenses(DaoSession daoSession) {
        TableExpenseFieldValueDao tableExpenseFieldValueDao = daoSession.getTableExpenseFieldValueDao();
        tableExpenseFieldValueDao.deleteAll();

        TableExpenseFieldDao tableExpenseFieldDao = daoSession.getTableExpenseFieldDao();
        tableExpenseFieldDao.deleteAll();

        TableExpenseDataDao tableExpenseDataDao = daoSession.getTableExpenseDataDao();
        tableExpenseDataDao.deleteAll();

        TableReceiptDao tableReceiptDao = daoSession.getTableReceiptDao();
        tableReceiptDao.deleteAll();

        TableExpenseDao tableExpenseDao = daoSession.getTableExpenseDao();
        tableExpenseDao.deleteAll();

        TableExpenseTypeDao tableExpenseTypeDao = daoSession.getTableExpenseTypeDao();
        tableExpenseTypeDao.deleteAll();
    }

    public static void insertAllDashboards(Dashboard dashboard, DaoSession daoSession) {
        long dashboardId = insertDasboard(dashboard, daoSession);
        for (DashboardField dashboardField : dashboard.getDashboardData()) {
            insertDashboardField(dashboardField, dashboardId, daoSession);
        }
    }

    public static void insertAllBusinessCenter(BusinessCenter[] businessCenter, DaoSession daoSession) {

        for(BusinessCenter businessCenter1:businessCenter)
            insertBusinessCenter(businessCenter1, daoSession);
    }

    public static void insertAllPayrollTransactionsFields(PayrollTransactions[] payrollTransactions,DaoSession daoSession)
    {
            //// TODO: 25/7/16 transaction fields.
        for(PayrollTransactions transactions:payrollTransactions) {
            long id = insertPayrollTransactionField(transactions, daoSession);
            BusinessWithHolding businessWithHolding = transactions.getBusinessWithholding();
            if(businessWithHolding!=null) {
                long businessId = insertBusinessWithHoldingField(businessWithHolding, id, daoSession);
                PayrollAmount payrollAmount = businessWithHolding.getPayrollAmount();
                insertBusiness_payrollAmountField(payrollAmount, businessId, daoSession);
                List<BusinessExpenses> businessExpenses=businessWithHolding.getBusinessExpenses();
                for(int i=0;i<businessExpenses.size();i++)
                {
                    insertBusiness_businessExpenseField(businessExpenses.get(i),businessId,daoSession);
                }
                List<BusinessPayrollTaxes> businessPayrollTaxes=businessWithHolding.getPayrollTaxes();
                for(int i=0;i<businessPayrollTaxes.size();i++)
                {
                    insertBusiness_businessPayrollTaxesField(businessPayrollTaxes.get(i),businessId,daoSession);
                }
            }

            PersonWithHolding personWithHolding=transactions.getPersonalWithholding();
            if(personWithHolding!=null) {
                long personId = insertPersonWithHoldingField(personWithHolding, id, daoSession);
                PersonGrossAmount grossAmount = personWithHolding.getGrossAmount();
                insertPerson_grossAmountField(grossAmount, personId, daoSession);
                List<PersonPayrollTaxes> personPayrollTaxes = personWithHolding.getPayrollTaxes();
                for (int i = 0; i < personPayrollTaxes.size(); i++) {
                    insertperson_payrollTaxesField(personPayrollTaxes.get(i), personId, daoSession);
                }
                List<ExpenseReimbursement> expenseReimbursements = personWithHolding.getExpenseReimbursements();
                for (int i = 0; i < expenseReimbursements.size(); i++) {
                    insertperson_payrollExpenseReimbersementsField(expenseReimbursements.get(i), personId, daoSession);
                }

                List<PersonDeposits> depositsList=personWithHolding.getDeposits();
                for(int i=0;i<depositsList.size();i++)
                {
                    insertperson_payrollDepositsField(depositsList.get(i),personId,daoSession);
                }
                List<PersonalDeductions> deductionsList=personWithHolding.getAfterTaxDeductions();
                for(int i=0;i<deductionsList.size();i++)
                    insertperson_payrollDeductionsField(deductionsList.get(i),personId,daoSession);

                PersonNetAmount netAmount=personWithHolding.getNetAmount();
                insertPerson_netAmountField(netAmount,personId,daoSession);
                PersonPayCheckAmount payCheckAmount=personWithHolding.getPaycheckAmount();
                insertPerson_payCheckAmountField(payCheckAmount,personId,daoSession);
            }
        }

    }
    public static void insertAllPayrollSummaryFields(PayrollSummary payrollSummary, DaoSession daoSession) {

            long nextPaymentId=insertPayrollSummaryField(payrollSummary, daoSession);
            NextPayment nextPayment=payrollSummary.getNext_payroll();


            if (nextPayment!=null) {
                if(nextPayment.isValid())
                insertNextPaymentField(nextPayment, nextPaymentId, daoSession);
            }
        PreviousPayment previousPayment=payrollSummary.getLast_payroll();
        if(previousPayment!=null){
                if(previousPayment.isValid()) {
                    long prevId=insertPreviousPaymentField(previousPayment, nextPaymentId, daoSession);
                    long businessId=0l;
                    BusinessWithHolding businessWithHolding = previousPayment.getBusinessWithholding();
                    if(businessWithHolding!=null) {
                        businessId = insertSummaryBusinessWithHoldingField(businessWithHolding, prevId, daoSession);
                        PayrollAmount payrollAmount = businessWithHolding.getPayrollAmount();
                        insertSummaryBusiness_payrollAmountField(payrollAmount, businessId, daoSession);
                    }
                }
        }

    }

    public static void insertAllWorkOrders(WorkOrder[] workOrders, DaoSession daoSession) {

        long workOrderId;
        for (WorkOrder workOrder : workOrders) {
            long companyId = insertCompany(workOrder.getCompany(), daoSession);
            workOrderId = insertWorkOrder(workOrder, companyId, daoSession);

            insertTimePeriods(workOrderId, workOrder.getTimePeriods(), daoSession);
            insertAllTimeTasks(workOrderId, workOrder.getTimeTasks(), daoSession);
        }
    }

    public static void updateUserProfile(UserProfile userProfile, DaoSession daoSession) {
        TableUserProfileDao userProfileDao =  daoSession.getTableUserProfileDao();
        userProfileDao.deleteAll();

        TableBusinessManagerDao tableBusinessManagerDao =  daoSession.getTableBusinessManagerDao();
        tableBusinessManagerDao.deleteAll();

        insertBusinessManager(userProfile.getBusinessManager(), daoSession);
        insertUserProfile(userProfile, daoSession);
    }

    public static void insertAllExpenseTypes(ExpenseType[] expenseTypesArray, DaoSession daoSession) {
        for (ExpenseType expenseType : expenseTypesArray) {
            insertExpenseType(expenseType, daoSession);
            insertAllExpenseFields(expenseType.getFields(), expenseType.getMboId(), daoSession);
        }
    }

    public static void insertAllExpenses(Expense[] expensesArray, DaoSession daoSession) {
        for (Expense expense : expensesArray) {
            long expenseId = insertExpense(expense, daoSession);
        }
    }

    // ================================================================================
    //
    // Scalar conversions
    //
    // ===============================================================================

    public static long insertDasboard(Dashboard dashboard, DaoSession daoSession) {
        TableDashboardDao dao = daoSession.getTableDashboardDao();
        return dao.insert(Converter.toTable(dashboard));
    }

    public static long insertBusinessCenter(BusinessCenter businessCenter, DaoSession daoSession) {
        TableBusinessCenterDao dao = daoSession.getTableBusinessCenterDao();
        return dao.insert(Converter.toTable_businessCenter(businessCenter));
    }

    public static long insertPayrollSummaryField(PayrollSummary payrollSummary, DaoSession daoSession) {
        TablePayrollSummaryDao dao = daoSession.getTablePayrollSummaryDao();
        return dao.insert(Converter.toTable_payroll_summary(payrollSummary));
    }

    public static long insertPayrollTransactionField(PayrollTransactions payrollTransactions,DaoSession daoSession)
    {
        TablePayrollTransactionsDao dao = daoSession.getTablePayrollTransactionsDao();
        long id=dao.insert(Converter.toTable_payroll_transactions(payrollTransactions));
        return id;
    }
    public static long insertNextPaymentField(NextPayment nextPayment, long nextPaymentId,DaoSession daoSession) {
        TableNextPaymentDao dao = daoSession.getTableNextPaymentDao();
        long id=dao.insert(Converter.toTable_payroll_nexPayment(nextPaymentId, nextPayment));
        return id;
    }
    public static long insertPreviousPaymentField(PreviousPayment previousPayment, long previousPaymentId,DaoSession daoSession) {
        TablePreviousPaymentDao dao = daoSession.getTablePreviousPaymentDao();
        long id=dao.insert(Converter.toTable_payroll_prevPayment(previousPaymentId, previousPayment));
        return id;
    }
    public static long insertBusinessWithHoldingField(BusinessWithHolding businessWithHolding, long prevPaymentId,DaoSession daoSession) {
        TableBusinessWithHoldingDao dao = daoSession.getTableBusinessWithHoldingDao();
        return dao.insert(Converter.toTable_payroll_businessWithHolding(prevPaymentId, businessWithHolding));
    }

    public static long insertSummaryBusinessWithHoldingField(BusinessWithHolding businessWithHolding, long prevPaymentId,DaoSession daoSession) {
        TableSummaryBusinessWithHoldingDao dao = daoSession.getTableSummaryBusinessWithHoldingDao();
        return dao.insert(Converter.toTable_payroll_summary_businessWithHolding(prevPaymentId, businessWithHolding));
    }

    public static long insertPersonWithHoldingField(PersonWithHolding personWithHolding, long prevPaymentId,DaoSession daoSession) {
        TablePersonWithHoldingDao dao = daoSession.getTablePersonWithHoldingDao();
        return dao.insert(Converter.toTable_payroll_personWithHolding(prevPaymentId, personWithHolding));
    }

    public static long insertBusiness_payrollAmountField(PayrollAmount payrollAmount, long nextPaymentId,DaoSession daoSession) {
        TablePayrollAmountDao dao = daoSession.getTablePayrollAmountDao();
        return dao.insert(Converter.toTable_payroll_business_payrollAmount(nextPaymentId, payrollAmount));
    }

    public static long insertSummaryBusiness_payrollAmountField(PayrollAmount payrollAmount, long nextPaymentId,DaoSession daoSession) {
        TableSummaryPayrollAmountDao dao = daoSession.getTableSummaryPayrollAmountDao();
        return dao.insert(Converter.toTable_payroll_summary_business_payrollAmount(nextPaymentId, payrollAmount));
    }

    public static long insertPerson_grossAmountField(PersonGrossAmount grossAmount, long nextPaymentId,DaoSession daoSession) {
        TablePersonGrossAmountDao dao = daoSession.getTablePersonGrossAmountDao();
        return dao.insert(Converter.toTable_payroll_person_grossAmount(nextPaymentId, grossAmount));
    }

    public static long insertPerson_netAmountField(PersonNetAmount netAmount, long nextPaymentId,DaoSession daoSession) {
        TablePersonNetAmountDao dao = daoSession.getTablePersonNetAmountDao();
        return dao.insert(Converter.toTable_payroll_person_netAmount(nextPaymentId, netAmount));
    }

    public static long insertPerson_payCheckAmountField(PersonPayCheckAmount payCheckAmount, long nextPaymentId,DaoSession daoSession) {
        TablePersonPayCheckAmountDao dao = daoSession.getTablePersonPayCheckAmountDao();
        return dao.insert(Converter.toTable_payroll_person_payCheckAmount(nextPaymentId, payCheckAmount));
    }


    public static long insertBusiness_businessExpenseField(BusinessExpenses businessExpenses, long Id,DaoSession daoSession) {
        TableBusinessExpensesDao dao = daoSession.getTableBusinessExpensesDao();
        return dao.insert(Converter.toTable_payroll_business_expense(Id, businessExpenses));
    }
    public static long insertBusiness_businessPayrollTaxesField(BusinessPayrollTaxes businessPayrollTaxes, long Id,DaoSession daoSession) {
        TableBusinessPayrollTaxesDao dao = daoSession.getTableBusinessPayrollTaxesDao();
        return dao.insert(Converter.toTable_payroll_business_payrollTaxes(Id, businessPayrollTaxes));
    }

    public static long insertperson_payrollTaxesField(PersonPayrollTaxes personPayrollTaxes, long Id,DaoSession daoSession) {
        TablePersonPayrollTaxesDao dao = daoSession.getTablePersonPayrollTaxesDao();
        return dao.insert(Converter.toTable_payroll_person_payrollTaxes(Id, personPayrollTaxes));
    }
    public static long insertperson_payrollExpenseReimbersementsField(ExpenseReimbursement expenseReimbursement, long Id,DaoSession daoSession) {
        TableExpenseReimbersementsDao dao = daoSession.getTableExpenseReimbersementsDao();
        return dao.insert(Converter.toTable_payroll_person_expense_reimbersements(Id, expenseReimbursement));
    }

    public static long insertperson_payrollDepositsField(PersonDeposits personDeposits, long Id,DaoSession daoSession) {
        TablePersonDepositsDao dao = daoSession.getTablePersonDepositsDao();
        return dao.insert(Converter.toTable_payroll_person_deposits(Id, personDeposits));
    }

    public static long insertperson_payrollDeductionsField(PersonalDeductions personalDeductions, long Id,DaoSession daoSession) {
        TablePersonAfterDeductionsDao dao = daoSession.getTablePersonAfterDeductionsDao();
        return dao.insert(Converter.toTable_payroll_person_deductions(Id, personalDeductions));
    }

    public static long insertDashboardField(DashboardField dashboardField, long dashboardId, DaoSession daoSession) {
        TableDashboardFieldDao dao = daoSession.getTableDashboardFieldDao();
        return dao.insert(Converter.toTable(dashboardId, dashboardField));
    }

    public static long insertWorkOrder(WorkOrder workOrder, long companyId, DaoSession daoSession) {
        TableWorkOrderDao dao = daoSession.getTableWorkOrderDao();
        return dao.insert(Converter.toTable(workOrder, companyId));
    }

    public static long insertCompany(Company company, DaoSession daoSession) {
        TableCompanyDao dao =  daoSession.getTableCompanyDao();
        return dao.insert(Converter.toTable(company));
    }

    public static long insertBusinessManager(BusinessManager businessManager, DaoSession daoSession) {
        TableBusinessManagerDao dao =  daoSession.getTableBusinessManagerDao();
        return dao.insert(Converter.toTable(businessManager));
    }

    public static long insertUserProfile(UserProfile userProfile, DaoSession daoSession) {
        TableUserProfileDao userProfileDao =  daoSession.getTableUserProfileDao();
        return userProfileDao.insert(Converter.toTable(userProfile));
    }

    public static long insertExpense(Expense expense, DaoSession daoSession) {
        TableExpenseDao dao = daoSession.getTableExpenseDao();
        long expenseId = dao.insert(Converter.toTable(expense));
        insertAllExpenseData(expenseId, expense.getExpenseData(), daoSession);
        insertAllReceipts(expenseId, expense.getReceipts(), daoSession);
        return expenseId;
    }

    public static void removeExpense_Cascaded(String mboId, DaoSession daoSession) {
        TableExpenseDao dao = daoSession.getTableExpenseDao();
        TableExpense expense = dao.queryBuilder().where(TableExpenseDao.Properties.MboId.eq(mboId)).unique();

        expense.resetReceipts();
        expense.resetExpenseData();

        if (expense != null) {
            for (TableExpenseData dataItem : expense.getExpenseData() ) {
                deleteExpenseData(dataItem.getId(), daoSession);
            }
            for (TableReceipt receiptItem : expense.getReceipts()) {
                deleteReceipt(receiptItem.getId(), daoSession);
            }
            daoSession.delete(expense);
        }
    }

    public static void updateExpense_Cascaded(Expense expense, DaoSession daoSession) {
        TableExpenseDao tableExpenseDao = daoSession.getTableExpenseDao();
        TableExpense expenseTableRecord = tableExpenseDao.queryBuilder().where(TableExpenseDao.Properties.MboId.eq(expense.getMboId())).unique();

        if (expenseTableRecord != null) {
            for (TableExpenseData dataItem : expenseTableRecord.getExpenseData() ) {
                deleteExpenseData(dataItem.getId(), daoSession);
            }
            for (TableReceipt receiptItem : expenseTableRecord.getReceipts()) {
                deleteReceipt(receiptItem.getId(), daoSession);
            }

            updateValues(expenseTableRecord, expense);
            expenseTableRecord.update();
            insertAllReceipts(expenseTableRecord.getId(), expense.getReceipts(), daoSession);
            insertAllExpenseData(expenseTableRecord.getId(), expense.getExpenseData(), daoSession);

            expenseTableRecord.resetReceipts();
            expenseTableRecord.resetExpenseData();
        }
    }

    public static long insertExpenseData(long expenseId, ExpenseData expenseData, DaoSession daoSession) {
        TableExpenseDataDao dao = daoSession.getTableExpenseDataDao();
        return dao.insert(Converter.toTable(expenseId, expenseData));
    }

    public static void deleteExpenseData(long ExpenseId, DaoSession daoSession) {
        TableExpenseDataDao dao = daoSession.getTableExpenseDataDao();
        daoSession.delete(dao.load(ExpenseId));
    }

    public static long insertReceipt(long expenseId, Receipt receipt, DaoSession daoSession) {
        TableReceiptDao dao = daoSession.getTableReceiptDao();
        return dao.insert(Converter.toTable(expenseId, receipt));
    }

    public static void deleteReceipt (long receiptId, DaoSession daoSession) {
        TableReceiptDao dao = daoSession.getTableReceiptDao();
        daoSession.delete(dao.load(receiptId));
    }

    public static void markReceiptsToDelete(String mboExpenseId, String receiptFilename, DaoSession daoSession) {
        updateReceiptRecState(mboExpenseId, receiptFilename, AbstractDbHandler.REC_STATE__DELETE__REGISTERED, daoSession);
    }

    public static void markReceiptsToCreated(DaoSession daoSession) {
        TableReceiptDao dao = daoSession.getTableReceiptDao();
        List<TableReceipt> receipts = dao.queryBuilder()
                .where(TableReceiptDao.Properties.RecState.eq(AbstractDbHandler.REC_STATE__DELETE__REGISTERED))
                .list();
        if (receipts != null) {
            for (TableReceipt receipt : receipts) {
                receipt.setRecState(AbstractDbHandler.REC_STATE__CREATE__SUCCESS);
                receipt.update();
            }
        }
    }

    public static void markTimeEntriesForUpdate(String mboWorkOrderId, String mboTimePeriodId, DaoSession daoSession) {
        TableTimeEntryDao dao = daoSession.getTableTimeEntryDao();
        TableTimePeriodDao tableTimePeriodDao = daoSession.getTableTimePeriodDao();

        TableTimePeriod tableTimePeriod = tableTimePeriodDao.queryBuilder()
                .where(TableTimePeriodDao.Properties.MboWorkOrderId.eq(mboWorkOrderId),
                        TableTimePeriodDao.Properties.MboId.eq(mboTimePeriodId)).unique();

        List<TableTimeEntry> timeEntries = dao.queryBuilder()
                .where(
                        TableTimeEntryDao.Properties.TimePeriodId.eq(tableTimePeriod.getId()))
                .list();
        if (timeEntries != null) {
            for (TableTimeEntry tableTimeEntry : timeEntries) {
                tableTimeEntry.setRecState(AbstractDbHandler.REC_STATE__UPDATE__REGISTERED);
                tableTimeEntry.update();
            }
        }
    }

    public static void unMarkTimeEntriesForUpdate(DaoSession daoSession) {
        TableTimeEntryDao dao = daoSession.getTableTimeEntryDao();
        List<TableTimeEntry> tableTimeEntries = dao.queryBuilder()
                .where(TableTimeEntryDao.Properties.RecState.eq(AbstractDbHandler.REC_STATE__UPDATE__REGISTERED))
                .list();

        for (TableTimeEntry record : tableTimeEntries) {
            record.setRecState(AbstractDbHandler.REC_STATE__NORMAL);
            record.update();
        }
    }

    public static void replaceTimeEntriesToUpdate(TimeEntry[] timeEntries, DaoSession daoSession) {
        TableTimeEntryDao dao = daoSession.getTableTimeEntryDao();
        List<TableTimeEntry> tableTimeEntries = dao.queryBuilder()
                .where(TableTimeEntryDao.Properties.RecState.eq(AbstractDbHandler.REC_STATE__UPDATE__REGISTERED))
                .list();

        if (tableTimeEntries != null && ! tableTimeEntries.isEmpty()) {

            // detect timePeriodId for previously marked TimeEntries
            long timePeriodId = tableTimeEntries.get(0).getTimePeriodId();
            // Delete marred timeEntries
            for (TableTimeEntry tableTimeEntry : tableTimeEntries) {
                tableTimeEntry.delete();
            }

            insertTimeEntries(timePeriodId, timeEntries, daoSession);

            TableTimePeriodDao tableTimePeriodDao =  daoSession.getTableTimePeriodDao();
            TableTimePeriod timePeriodTableRecord = tableTimePeriodDao.load(timePeriodId);
            timePeriodTableRecord.resetTimeEntries();
        }
    }

    public static void updateReceiptRecState(String mboExpenseId, String receiptFilename, int recState, DaoSession daoSession) {
        TableReceiptDao dao = daoSession.getTableReceiptDao();
        TableReceipt receiptTable =
                dao.queryBuilder()
                .where(
                        TableReceiptDao.Properties.Filename.eq(receiptFilename),
                        TableReceiptDao.Properties.MboExpenseId.eq(mboExpenseId))
                .unique();
        receiptTable.setRecState(recState);
        dao.update(receiptTable);
    }

    public static void deleteMarkedReceipt(DaoSession daoSession) {
        TableReceiptDao dao = daoSession.getTableReceiptDao();
        List<TableReceipt> receipts = dao.queryBuilder()
                .where(TableReceiptDao.Properties.RecState.eq(AbstractDbHandler.REC_STATE__DELETE__REGISTERED))
                .list();
        if (receipts != null) {
            for (TableReceipt receipt : receipts) {
                receipt.delete();
            }
        }
    }

    public static void insertExpenseType(ExpenseType expenseType, DaoSession daoSession) {
        TableExpenseTypeDao dao = daoSession.getTableExpenseTypeDao();
        dao.insert(Converter.toTable(expenseType));
        return;
    }

    public static long insertExpenseField(ExpenseField expenseField, String expenseTypeId, DaoSession daoSession) {
        TableExpenseFieldDao dao = daoSession.getTableExpenseFieldDao();
        return dao.insert(Converter.toTable(expenseField, expenseTypeId));
    }

    public static long insertExpenseFieldValue(ExpenseFieldValue fieldValue, long expenseFieldId, DaoSession daoSession) {
        TableExpenseFieldValueDao dao = daoSession.getTableExpenseFieldValueDao();
        return dao.insert(Converter.toTable(fieldValue, expenseFieldId));
    }

    // ================================================================================
    //
    // Massive conversions
    //
    // ================================================================================
    public static void insertAllTimeTasks(long workOrderId, List<TimeTask> timeTasks, DaoSession daoSession) {
        TableTimeTaskDao dao = daoSession.getTableTimeTaskDao();
        for (TimeTask timeTask : timeTasks) {
            dao.insert(Converter.toTable(workOrderId, timeTask));
        }
    }

    public static void insertTimePeriods(long workOrderId, List<TimePeriod> timePeriods, DaoSession daoSession) {
        TableTimePeriodDao dao = daoSession.getTableTimePeriodDao();
        long timePeriodId;
        for (TimePeriod timePeriod : timePeriods) {
            timePeriodId = dao.insert(Converter.toTable(workOrderId, timePeriod));
            insertTimeEntries(timePeriodId, timePeriod.getTimeEntries(), daoSession);
        }
    }

    public static void insertTimeEntries(long periodId, TimeEntry[] timeEntries, DaoSession daoSession) {
        List<TimeEntry> timeEntriesList = Arrays.asList(timeEntries);
        insertTimeEntries(periodId, timeEntriesList, daoSession);
    }

    public static void insertTimeEntries(long periodId, List<TimeEntry> timeEntries, DaoSession daoSession) {
        TableTimeEntryDao dao = daoSession.getTableTimeEntryDao();
        for(TimeEntry timeEntry : timeEntries) {
            dao.insert(Converter.toTable(periodId, timeEntry));
        }
    }

    public static void insertAllExpenseData(long expenseId, List<ExpenseData> expenseDataList, DaoSession daoSession) {
        for (ExpenseData expenseData : expenseDataList) {
            insertExpenseData(expenseId, expenseData, daoSession);
        }
    }

    public static void insertAllReceipts(long expenseId, List<Receipt> receiptList, DaoSession daoSession) {
        for(Receipt receipt: receiptList) {
            insertReceipt(expenseId, receipt, daoSession);
        }
    }

    public static void insertAllExpenseFields(List<ExpenseField> fields, String expenseTypeId, DaoSession daoSession) {
        for (ExpenseField singleField : fields) {
            long expenseFieldId = insertExpenseField(singleField, expenseTypeId, daoSession);
            List<ExpenseFieldValue> values = singleField.getValues();
            if (values != null) {
                insertAllExpenseFieldValues(singleField.getValues(), expenseFieldId, daoSession);
            }
        }
    }

    public static void insertAllExpenseFieldValues(List<ExpenseFieldValue> values, long expenseFieldId, DaoSession daoSession) {
        for (ExpenseFieldValue fieldValue : values) {
            insertExpenseFieldValue(fieldValue, expenseFieldId, daoSession);
        }
    }

    //==============================================================================================

    public static void updateValues(TableExpense tableEntity, Expense webEntity) {
        tableEntity.setMboId(webEntity.getMboId());
        tableEntity.setMboWorkOrderId(webEntity.getMboWorkOrderId());
        tableEntity.setDescription(webEntity.getDescription());
        tableEntity.setMboAssociateId(webEntity.getMboAssociateId());
        tableEntity.setAmount(webEntity.getAmount());
        tableEntity.setEditable(webEntity.getEditable());
        tableEntity.setBillable(webEntity.getBillable());
        tableEntity.setVersion(webEntity.getVersion());
        tableEntity.setMboExpenseTypeId(webEntity.getMboExpenseTypeId());
    }

    //==============================================================================================

    public static void completeTimeEntriesWithVirtual(WorkOrder[] workOrders) {
        for (WorkOrder workOrder : workOrders) {
            completeTimeEntriesWithVirtual(workOrder);
        }
    }

    public static void completeTimeEntriesWithVirtual(WorkOrder workOrder) {
        for (TimePeriod timePeriod : workOrder.getTimePeriods()) {
            completeTimeEntriesWithVirtual(workOrder.getTimeTasks(), timePeriod, workOrder.getId());
        }
    }

    public static void completeTimeEntriesWithVirtual(List<TimeTask> timeTasks, TimePeriod timePeriod, String workOrderId) {
        List<TimeEntry> newTimeEntries = new ArrayList<>();

        Date currentDate = getCurrentDate();

        Calendar start = Calendar.getInstance();
        start.setTime(timePeriod.getStartDate());

        Calendar end = Calendar.getInstance();
        end.setTime(timePeriod.getEndDate());

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(currentDate);

        if (end.compareTo(rightNow) > 0) {
            end = rightNow;
        }

        while (!start.after(end)) {
            List<TimeEntry> timeEntries = timePeriod.getTimeEntries();

            completeTimeEntriesWithVirtual(timeTasks, timeEntries, workOrderId, newTimeEntries, start);
            start.add(Calendar.DATE, 1);
        }

        List<TimeEntry> originalTimeEntriesList = timePeriod.getTimeEntries();
        originalTimeEntriesList.clear();
        originalTimeEntriesList.addAll(newTimeEntries);
        return;
    }

    private static void completeTimeEntriesWithVirtual(List<TimeTask> timeTasks, List<TimeEntry> timeEntries, String workOrderId, List<TimeEntry> newTimeEntries, Calendar certainDay) {
        Date date = certainDay.getTime();

        List<TimeEntry> dayTimeEntries = getTimeEntriesForDay(timeEntries, date);
        boolean editable = false;
        if (!dayTimeEntries.isEmpty()) {
            editable = dayTimeEntries.get(0).getEditable();
        } else {
            editable = true;
        }

        TimeEntry timeEntryCandidate;
        for (TimeTask timeTask : timeTasks) {

            String taskId = timeTask.getId();
            timeEntryCandidate = getTimeEntry(timeEntries, date, taskId);
            if (timeEntryCandidate != null) {
                // Ok nothing to do
            } else {
                timeEntryCandidate = new TimeEntry(
                        null, workOrderId, taskId,
                        0.0D, date, "",
                        editable, TimeEntry.VERSION);
            }
            newTimeEntries.add(timeEntryCandidate);
        }
    }

    public static Date getCurrentDate() {
        Calendar rightNow = getCurrentCalendar();
        rightNow.set(Calendar.HOUR_OF_DAY, 0);
        rightNow.set(Calendar.MINUTE, 0);
        rightNow.set(Calendar.SECOND, 0);
        rightNow.set(Calendar.MILLISECOND, 0);
        return rightNow.getTime();
    }

    public static Calendar getCurrentCalendar() {
        TimeZone timeZone = TimeZone.getDefault();
        return Calendar.getInstance(timeZone);
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

    public static List<TimeEntry> getTimeEntriesForDay(List<TimeEntry> timeEntries, Date date) {
        if (timeEntries == null || date == null) {
            return null;
        }

        List<TimeEntry> dayTimeEntryList = new ArrayList<>();
        for (TimeEntry timeEntry : timeEntries) {
            if (timeEntry.getDate().equals(date)) {
                dayTimeEntryList.add(timeEntry);
            }
        }
        return dayTimeEntryList;
    }

}

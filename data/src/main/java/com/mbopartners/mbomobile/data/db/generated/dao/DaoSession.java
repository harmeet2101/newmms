package com.mbopartners.mbomobile.data.db.generated.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessAddressDao;
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
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonPayrollTaxesDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryPayrollAmountDao;
import com.mbopartners.mbomobile.data.db.generated.model.TableBusinessManager;
import com.mbopartners.mbomobile.data.db.generated.model.TableUserProfile;
import com.mbopartners.mbomobile.data.db.generated.model.TableDashboard;
import com.mbopartners.mbomobile.data.db.generated.model.TableDashboardField;
import com.mbopartners.mbomobile.data.db.generated.model.TableWorkOrder;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimeTask;
import com.mbopartners.mbomobile.data.db.generated.model.TableCompany;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimePeriod;
import com.mbopartners.mbomobile.data.db.generated.model.TableTimeEntry;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseData;
import com.mbopartners.mbomobile.data.db.generated.model.TableReceipt;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseType;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseField;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseFieldValue;
import com.mbopartners.mbomobile.data.db.generated.model.ExpenseType_2_ExpenseField;

import com.mbopartners.mbomobile.data.db.generated.dao.TableBusinessManagerDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableUserProfileDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableDashboardDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableDashboardFieldDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableWorkOrderDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimeTaskDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableCompanyDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimePeriodDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableTimeEntryDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDataDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableReceiptDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseTypeDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseFieldDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseFieldValueDao;
import com.mbopartners.mbomobile.data.db.generated.dao.ExpenseType_2_ExpenseFieldDao;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessAddress;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessCenter;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessExpenses;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessPayrollTaxes;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableExpenseReimbersements;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableNextPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollSummary;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollTransactions;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonAfterDeductions;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonDeposits;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonGrossAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonPayrollTaxes;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonalWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableSummaryBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableSummaryPayrollAmount;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tableBusinessManagerDaoConfig;
    private final DaoConfig tableUserProfileDaoConfig;
    private final DaoConfig tableDashboardDaoConfig;
    private final DaoConfig tableDashboardFieldDaoConfig;
    private final DaoConfig tableWorkOrderDaoConfig;
    private final DaoConfig tableTimeTaskDaoConfig;
    private final DaoConfig tableCompanyDaoConfig;
    private final DaoConfig tableTimePeriodDaoConfig;
    private final DaoConfig tableTimeEntryDaoConfig;
    private final DaoConfig tableExpenseDaoConfig;
    private final DaoConfig tableExpenseDataDaoConfig;
    private final DaoConfig tableReceiptDaoConfig;
    private final DaoConfig tableExpenseTypeDaoConfig;
    private final DaoConfig tableExpenseFieldDaoConfig;
    private final DaoConfig tableExpenseFieldValueDaoConfig;
    private final DaoConfig expenseType_2_ExpenseFieldDaoConfig;
    private final DaoConfig tableBusinessCenterDaoConfig;
    private final DaoConfig tableBusinessAddressDaoConfig;
    private final DaoConfig tablePayrollSummaryDaoConfig;
    private final DaoConfig tablePayrollNextPaymentDaoConfig;
    private final DaoConfig tablePayrollPreviousPaymentDaoConfig;

    private final DaoConfig tableBusinessWithHoldingDaoconfig;
    private final DaoConfig tableBusinessPayrollAmountDaoconfig;
    private final DaoConfig tableBusinessExpenseDaoconfig;
    private final DaoConfig tableBusinessPayrollTaxesDaoconfig;
    private final DaoConfig tablePersonWithHoldingDaoconfig;
    private final DaoConfig tablePersonGrossAmountDaoconfig;
    private final DaoConfig tablePersonPayrollTaxesDaoconfig;
    private final DaoConfig tablePersonExpenseReimbersementsDaoconfig;
    private final DaoConfig tablePayrollTransactionsDaoconfig;
    private final DaoConfig tablePersonDepositsDaoconfig;
    private final DaoConfig tableSummaryBusinessWithHoldingDaoconfig;
    private final DaoConfig tableSummaryBusinessPayrollAmountDaoconfig;
    private final DaoConfig tablePersonAfterDeductionsDaoconfig;

    private final TableBusinessManagerDao tableBusinessManagerDao;
    private final TableUserProfileDao tableUserProfileDao;
    private final TableDashboardDao tableDashboardDao;
    private final TableDashboardFieldDao tableDashboardFieldDao;
    private final TableWorkOrderDao tableWorkOrderDao;
    private final TableTimeTaskDao tableTimeTaskDao;
    private final TableCompanyDao tableCompanyDao;
    private final TableTimePeriodDao tableTimePeriodDao;
    private final TableTimeEntryDao tableTimeEntryDao;
    private final TableExpenseDao tableExpenseDao;
    private final TableExpenseDataDao tableExpenseDataDao;
    private final TableReceiptDao tableReceiptDao;
    private final TableExpenseTypeDao tableExpenseTypeDao;
    private final TableExpenseFieldDao tableExpenseFieldDao;
    private final TableExpenseFieldValueDao tableExpenseFieldValueDao;
    private final ExpenseType_2_ExpenseFieldDao expenseType_2_ExpenseFieldDao;
    private final TableBusinessCenterDao tableBusinessCenterDao;
    private final TableBusinessAddressDao tableBusinessAddressDao;
    private final TablePayrollSummaryDao tablePayrollSummaryDao;
    private final TableNextPaymentDao tableNextPaymentDao;
    private final TablePreviousPaymentDao tablePreviousPaymentDao;
    private final TablePersonWithHoldingDao tablePersonWithHoldingDao;
    private final TablePersonDepositsDao tablePersonDepositsDao;

    private final TableBusinessWithHoldingDao tableBusinessWithHoldingDao;
    private final TablePayrollAmountDao tablePayrollAmountDao;
    private final TableBusinessExpensesDao tableBusinessExpensesDao;
    private final TableBusinessPayrollTaxesDao tableBusinessPayrollTaxesDao;
    private final TablePersonGrossAmountDao tablePersonGrossAmountDao;
    private final TablePersonPayrollTaxesDao tablePersonPayrollTaxesDao;
    private final TableExpenseReimbersementsDao tableExpenseReimbersementsDao;
    private final TablePayrollTransactionsDao tablePayrollTransactionsDao;
    private final TableSummaryBusinessWithHoldingDao tableSummaryBusinessWithHoldingDao;
    private final TableSummaryPayrollAmountDao tableSummaryPayrollAmountDao;
    private final TablePersonAfterDeductionsDao tablePersonAfterDeductionsDao;
    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tableBusinessManagerDaoConfig = daoConfigMap.get(TableBusinessManagerDao.class).clone();
        tableBusinessManagerDaoConfig.initIdentityScope(type);

        tableUserProfileDaoConfig = daoConfigMap.get(TableUserProfileDao.class).clone();
        tableUserProfileDaoConfig.initIdentityScope(type);

        tableDashboardDaoConfig = daoConfigMap.get(TableDashboardDao.class).clone();
        tableDashboardDaoConfig.initIdentityScope(type);

        tableDashboardFieldDaoConfig = daoConfigMap.get(TableDashboardFieldDao.class).clone();
        tableDashboardFieldDaoConfig.initIdentityScope(type);

        tableWorkOrderDaoConfig = daoConfigMap.get(TableWorkOrderDao.class).clone();
        tableWorkOrderDaoConfig.initIdentityScope(type);

        tableTimeTaskDaoConfig = daoConfigMap.get(TableTimeTaskDao.class).clone();
        tableTimeTaskDaoConfig.initIdentityScope(type);

        tableCompanyDaoConfig = daoConfigMap.get(TableCompanyDao.class).clone();
        tableCompanyDaoConfig.initIdentityScope(type);

        tableTimePeriodDaoConfig = daoConfigMap.get(TableTimePeriodDao.class).clone();
        tableTimePeriodDaoConfig.initIdentityScope(type);

        tableTimeEntryDaoConfig = daoConfigMap.get(TableTimeEntryDao.class).clone();
        tableTimeEntryDaoConfig.initIdentityScope(type);

        tableExpenseDaoConfig = daoConfigMap.get(TableExpenseDao.class).clone();
        tableExpenseDaoConfig.initIdentityScope(type);

        tableExpenseDataDaoConfig = daoConfigMap.get(TableExpenseDataDao.class).clone();
        tableExpenseDataDaoConfig.initIdentityScope(type);

        tableReceiptDaoConfig = daoConfigMap.get(TableReceiptDao.class).clone();
        tableReceiptDaoConfig.initIdentityScope(type);

        tableExpenseTypeDaoConfig = daoConfigMap.get(TableExpenseTypeDao.class).clone();
        tableExpenseTypeDaoConfig.initIdentityScope(type);

        tableExpenseFieldDaoConfig = daoConfigMap.get(TableExpenseFieldDao.class).clone();
        tableExpenseFieldDaoConfig.initIdentityScope(type);

        tableExpenseFieldValueDaoConfig = daoConfigMap.get(TableExpenseFieldValueDao.class).clone();
        tableExpenseFieldValueDaoConfig.initIdentityScope(type);

        expenseType_2_ExpenseFieldDaoConfig = daoConfigMap.get(ExpenseType_2_ExpenseFieldDao.class).clone();
        expenseType_2_ExpenseFieldDaoConfig.initIdentityScope(type);

        tableBusinessCenterDaoConfig=daoConfigMap.get(TableBusinessCenterDao.class).clone();
        tableBusinessCenterDaoConfig.initIdentityScope(type);
        tableBusinessAddressDaoConfig=daoConfigMap.get(TableBusinessAddressDao.class).clone();
        tableBusinessAddressDaoConfig.initIdentityScope(type);
        tablePayrollSummaryDaoConfig=daoConfigMap.get(TablePayrollSummaryDao.class).clone();
        tablePayrollSummaryDaoConfig.initIdentityScope(type);
        tablePayrollNextPaymentDaoConfig=daoConfigMap.get(TableNextPaymentDao.class).clone();
        tablePayrollNextPaymentDaoConfig.initIdentityScope(type);
        tablePayrollPreviousPaymentDaoConfig=daoConfigMap.get(TablePreviousPaymentDao.class).clone();
        tablePayrollPreviousPaymentDaoConfig.initIdentityScope(type);

        tableBusinessWithHoldingDaoconfig=daoConfigMap.get(TableBusinessWithHoldingDao.class).clone();
        tableBusinessWithHoldingDaoconfig.initIdentityScope(type);
        tablePersonWithHoldingDaoconfig=daoConfigMap.get(TablePersonWithHoldingDao.class).clone();
        tablePersonWithHoldingDaoconfig.initIdentityScope(type);
        tableBusinessPayrollAmountDaoconfig=daoConfigMap.get(TablePayrollAmountDao.class).clone();
        tableBusinessPayrollAmountDaoconfig.initIdentityScope(type);

        tableBusinessExpenseDaoconfig=daoConfigMap.get(TableBusinessExpensesDao.class).clone();
        tableBusinessExpenseDaoconfig.initIdentityScope(type);
        tableBusinessPayrollTaxesDaoconfig=daoConfigMap.get(TableBusinessPayrollTaxesDao.class).clone();
        tableBusinessPayrollTaxesDaoconfig.initIdentityScope(type);

        tablePersonGrossAmountDaoconfig=daoConfigMap.get(TablePersonGrossAmountDao.class).clone();
        tablePersonGrossAmountDaoconfig.initIdentityScope(type);
        tablePersonPayrollTaxesDaoconfig=daoConfigMap.get(TablePersonPayrollTaxesDao.class).clone();
        tablePersonPayrollTaxesDaoconfig.initIdentityScope(type);

        tablePersonExpenseReimbersementsDaoconfig=daoConfigMap.get(TableExpenseReimbersementsDao.class).clone();
        tablePersonExpenseReimbersementsDaoconfig.initIdentityScope(type);

        tablePayrollTransactionsDaoconfig=daoConfigMap.get(TablePayrollTransactionsDao.class).clone();
        tablePayrollTransactionsDaoconfig.initIdentityScope(type);

        tablePersonDepositsDaoconfig=daoConfigMap.get(TablePersonDepositsDao.class).clone();
        tablePersonDepositsDaoconfig.initIdentityScope(type);

        tableSummaryBusinessWithHoldingDaoconfig=daoConfigMap.get(TableSummaryBusinessWithHoldingDao.class).clone();
        tableSummaryBusinessWithHoldingDaoconfig.initIdentityScope(type);

        tableSummaryBusinessPayrollAmountDaoconfig=daoConfigMap.get(TableSummaryPayrollAmountDao.class).clone();
        tableSummaryBusinessPayrollAmountDaoconfig.initIdentityScope(type);

        tablePersonAfterDeductionsDaoconfig=daoConfigMap.get(TablePersonAfterDeductionsDao.class).clone();
        tablePersonAfterDeductionsDaoconfig.initIdentityScope(type);

        tableBusinessManagerDao = new TableBusinessManagerDao(tableBusinessManagerDaoConfig, this);
        tableUserProfileDao = new TableUserProfileDao(tableUserProfileDaoConfig, this);
        tableDashboardDao = new TableDashboardDao(tableDashboardDaoConfig, this);
        tableDashboardFieldDao = new TableDashboardFieldDao(tableDashboardFieldDaoConfig, this);
        tableWorkOrderDao = new TableWorkOrderDao(tableWorkOrderDaoConfig, this);
        tableTimeTaskDao = new TableTimeTaskDao(tableTimeTaskDaoConfig, this);
        tableCompanyDao = new TableCompanyDao(tableCompanyDaoConfig, this);
        tableTimePeriodDao = new TableTimePeriodDao(tableTimePeriodDaoConfig, this);
        tableTimeEntryDao = new TableTimeEntryDao(tableTimeEntryDaoConfig, this);
        tableExpenseDao = new TableExpenseDao(tableExpenseDaoConfig, this);
        tableExpenseDataDao = new TableExpenseDataDao(tableExpenseDataDaoConfig, this);
        tableReceiptDao = new TableReceiptDao(tableReceiptDaoConfig, this);
        tableExpenseTypeDao = new TableExpenseTypeDao(tableExpenseTypeDaoConfig, this);
        tableExpenseFieldDao = new TableExpenseFieldDao(tableExpenseFieldDaoConfig, this);
        tableExpenseFieldValueDao = new TableExpenseFieldValueDao(tableExpenseFieldValueDaoConfig, this);
        expenseType_2_ExpenseFieldDao = new ExpenseType_2_ExpenseFieldDao(expenseType_2_ExpenseFieldDaoConfig, this);
        tableBusinessCenterDao=new TableBusinessCenterDao(tableBusinessCenterDaoConfig,this);
        tableBusinessAddressDao=new TableBusinessAddressDao(tableBusinessAddressDaoConfig,this);

        tablePayrollSummaryDao=new TablePayrollSummaryDao(tablePayrollSummaryDaoConfig,this);
        tableNextPaymentDao=new TableNextPaymentDao(tablePayrollNextPaymentDaoConfig,this);
        tablePreviousPaymentDao=new TablePreviousPaymentDao(tablePayrollPreviousPaymentDaoConfig,this);

        tableBusinessWithHoldingDao=new TableBusinessWithHoldingDao(tableBusinessWithHoldingDaoconfig,this);
        tablePersonWithHoldingDao=new TablePersonWithHoldingDao(tablePersonWithHoldingDaoconfig,this);
        tablePayrollAmountDao=new TablePayrollAmountDao(tableBusinessPayrollAmountDaoconfig,this);
        tableBusinessExpensesDao=new TableBusinessExpensesDao(tableBusinessExpenseDaoconfig,this);
        tableBusinessPayrollTaxesDao=new TableBusinessPayrollTaxesDao(tableBusinessPayrollTaxesDaoconfig,this);
        tablePersonGrossAmountDao=new TablePersonGrossAmountDao(tablePersonGrossAmountDaoconfig,this);
        tablePersonPayrollTaxesDao=new TablePersonPayrollTaxesDao(tablePersonPayrollTaxesDaoconfig,this);

        tableExpenseReimbersementsDao=new TableExpenseReimbersementsDao(tablePersonExpenseReimbersementsDaoconfig,this);
        tablePayrollTransactionsDao=new TablePayrollTransactionsDao(tablePayrollTransactionsDaoconfig,this);

        tablePersonDepositsDao=new TablePersonDepositsDao(tablePersonDepositsDaoconfig,this);
        tableSummaryBusinessWithHoldingDao=new TableSummaryBusinessWithHoldingDao(tableSummaryBusinessWithHoldingDaoconfig,this);
        tableSummaryPayrollAmountDao=new TableSummaryPayrollAmountDao(tableSummaryBusinessPayrollAmountDaoconfig,this);
        tablePersonAfterDeductionsDao=new TablePersonAfterDeductionsDao(tablePersonAfterDeductionsDaoconfig,this);

        registerDao(TableBusinessManager.class, tableBusinessManagerDao);
        registerDao(TableUserProfile.class, tableUserProfileDao);
        registerDao(TableDashboard.class, tableDashboardDao);
        registerDao(TableDashboardField.class, tableDashboardFieldDao);
        registerDao(TableWorkOrder.class, tableWorkOrderDao);
        registerDao(TableTimeTask.class, tableTimeTaskDao);
        registerDao(TableCompany.class, tableCompanyDao);
        registerDao(TableTimePeriod.class, tableTimePeriodDao);
        registerDao(TableTimeEntry.class, tableTimeEntryDao);
        registerDao(TableExpense.class, tableExpenseDao);
        registerDao(TableExpenseData.class, tableExpenseDataDao);
        registerDao(TableReceipt.class, tableReceiptDao);
        registerDao(TableExpenseType.class, tableExpenseTypeDao);
        registerDao(TableExpenseField.class, tableExpenseFieldDao);
        registerDao(TableExpenseFieldValue.class, tableExpenseFieldValueDao);
        registerDao(ExpenseType_2_ExpenseField.class, expenseType_2_ExpenseFieldDao);
        registerDao(TableBusinessCenter.class,tableBusinessCenterDao);
        registerDao(TableBusinessAddress.class,tableBusinessAddressDao);
        registerDao(TablePayrollSummary.class,tablePayrollSummaryDao);
        registerDao(TableNextPayment.class,tableNextPaymentDao);
        registerDao(TablePreviousPayment.class,tablePreviousPaymentDao);
        registerDao(TableBusinessWithHolding.class,tableBusinessWithHoldingDao);
        registerDao(TablePayrollAmount.class,tablePayrollAmountDao);
        registerDao(TableBusinessExpenses.class,tableBusinessExpensesDao);
        registerDao(TableBusinessPayrollTaxes.class,tableBusinessPayrollTaxesDao);
        registerDao(TablePersonalWithHolding.class,tablePersonWithHoldingDao);
        registerDao(TablePersonGrossAmount.class,tablePersonGrossAmountDao);
        registerDao(TablePersonPayrollTaxes.class,tablePersonPayrollTaxesDao);
        registerDao(TableExpenseReimbersements.class,tableExpenseReimbersementsDao);
        registerDao(TablePayrollTransactions.class,tablePayrollTransactionsDao);
        registerDao(TablePersonDeposits.class,tablePersonDepositsDao);
        registerDao(TableSummaryBusinessWithHolding.class,tableSummaryBusinessWithHoldingDao);
        registerDao(TableSummaryPayrollAmount.class,tableSummaryPayrollAmountDao);
        registerDao(TablePersonAfterDeductions.class,tablePersonAfterDeductionsDao);
    }



    public void clear() {
        tableBusinessManagerDaoConfig.getIdentityScope().clear();
        tableUserProfileDaoConfig.getIdentityScope().clear();
        tableDashboardDaoConfig.getIdentityScope().clear();
        tableDashboardFieldDaoConfig.getIdentityScope().clear();
        tableWorkOrderDaoConfig.getIdentityScope().clear();
        tableTimeTaskDaoConfig.getIdentityScope().clear();
        tableCompanyDaoConfig.getIdentityScope().clear();
        tableTimePeriodDaoConfig.getIdentityScope().clear();
        tableTimeEntryDaoConfig.getIdentityScope().clear();
        tableExpenseDaoConfig.getIdentityScope().clear();
        tableExpenseDataDaoConfig.getIdentityScope().clear();
        tableReceiptDaoConfig.getIdentityScope().clear();
        tableExpenseTypeDaoConfig.getIdentityScope().clear();
        tableExpenseFieldDaoConfig.getIdentityScope().clear();
        tableExpenseFieldValueDaoConfig.getIdentityScope().clear();
        expenseType_2_ExpenseFieldDaoConfig.getIdentityScope().clear();
        tableBusinessCenterDaoConfig.getIdentityScope().clear();
        tableBusinessAddressDaoConfig.getIdentityScope().clear();
        tablePayrollSummaryDaoConfig.getIdentityScope().clear();
        tablePayrollNextPaymentDaoConfig.getIdentityScope().clear();
        tablePayrollPreviousPaymentDaoConfig.getIdentityScope().clear();

        tableBusinessWithHoldingDaoconfig.getIdentityScope().clear();
        tableBusinessPayrollAmountDaoconfig.getIdentityScope().clear();
        tableBusinessExpenseDaoconfig.getIdentityScope().clear();
        tableBusinessPayrollTaxesDaoconfig.getIdentityScope().clear();
        tablePersonWithHoldingDaoconfig.getIdentityScope().clear();
        tablePersonGrossAmountDaoconfig.getIdentityScope().clear();
        tablePersonPayrollTaxesDaoconfig.getIdentityScope().clear();
        tablePersonExpenseReimbersementsDaoconfig.getIdentityScope().clear();
        tablePayrollTransactionsDaoconfig.getIdentityScope().clear();
        tablePersonDepositsDaoconfig.getIdentityScope().clear();
        tableSummaryBusinessWithHoldingDaoconfig.getIdentityScope().clear();
        tableSummaryBusinessPayrollAmountDaoconfig.getIdentityScope().clear();
        tablePersonAfterDeductionsDaoconfig.getIdentityScope().clear();
    }

    public TableBusinessManagerDao getTableBusinessManagerDao() {
        return tableBusinessManagerDao;
    }

    public TableUserProfileDao getTableUserProfileDao() {
        return tableUserProfileDao;
    }

    public TableDashboardDao getTableDashboardDao() {
        return tableDashboardDao;
    }

    public TableDashboardFieldDao getTableDashboardFieldDao() {
        return tableDashboardFieldDao;
    }

    public TableWorkOrderDao getTableWorkOrderDao() {
        return tableWorkOrderDao;
    }

    public TableTimeTaskDao getTableTimeTaskDao() {
        return tableTimeTaskDao;
    }

    public TableCompanyDao getTableCompanyDao() {
        return tableCompanyDao;
    }

    public TableTimePeriodDao getTableTimePeriodDao() {
        return tableTimePeriodDao;
    }

    public TableTimeEntryDao getTableTimeEntryDao() {
        return tableTimeEntryDao;
    }

    public TableExpenseDao getTableExpenseDao() {
        return tableExpenseDao;
    }

    public TableExpenseDataDao getTableExpenseDataDao() {
        return tableExpenseDataDao;
    }

    public TableReceiptDao getTableReceiptDao() {
        return tableReceiptDao;
    }

    public TableExpenseTypeDao getTableExpenseTypeDao() {
        return tableExpenseTypeDao;
    }

    public TableExpenseFieldDao getTableExpenseFieldDao() {
        return tableExpenseFieldDao;
    }
    public TableBusinessCenterDao getTableBusinessCenterDao() {
        return tableBusinessCenterDao;
    }

    public TableBusinessAddressDao getTableBusinessAddressDao() {
        return tableBusinessAddressDao;
    }

    public TableExpenseFieldValueDao getTableExpenseFieldValueDao() {
        return tableExpenseFieldValueDao;
    }

    public ExpenseType_2_ExpenseFieldDao getExpenseType_2_ExpenseFieldDao() {
        return expenseType_2_ExpenseFieldDao;
    }

    public TablePayrollSummaryDao getTablePayrollSummaryDao() {
        return tablePayrollSummaryDao;
    }

    public TableNextPaymentDao getTableNextPaymentDao() {
        return tableNextPaymentDao;
    }

    public TablePreviousPaymentDao getTablePreviousPaymentDao() {
        return tablePreviousPaymentDao;
    }

    public TableBusinessWithHoldingDao getTableBusinessWithHoldingDao() {
        return tableBusinessWithHoldingDao;
    }

    public TablePayrollAmountDao getTablePayrollAmountDao() {
        return tablePayrollAmountDao;
    }

    public TableBusinessExpensesDao getTableBusinessExpensesDao() {
        return tableBusinessExpensesDao;
    }

    public TableBusinessPayrollTaxesDao getTableBusinessPayrollTaxesDao() {
        return tableBusinessPayrollTaxesDao;
    }

    public TablePersonWithHoldingDao getTablePersonWithHoldingDao() {
        return tablePersonWithHoldingDao;
    }

    public TablePersonGrossAmountDao getTablePersonGrossAmountDao() {
        return tablePersonGrossAmountDao;
    }

    public TablePersonPayrollTaxesDao getTablePersonPayrollTaxesDao() {
        return tablePersonPayrollTaxesDao;
    }

    public TableExpenseReimbersementsDao getTableExpenseReimbersementsDao() {
        return tableExpenseReimbersementsDao;
    }

    public TablePayrollTransactionsDao getTablePayrollTransactionsDao() {
        return tablePayrollTransactionsDao;
    }

    public TablePersonDepositsDao getTablePersonDepositsDao() {
        return tablePersonDepositsDao;
    }

    public TableSummaryBusinessWithHoldingDao getTableSummaryBusinessWithHoldingDao() {
        return tableSummaryBusinessWithHoldingDao;
    }

    public TableSummaryPayrollAmountDao getTableSummaryPayrollAmountDao() {
        return tableSummaryPayrollAmountDao;
    }

    public TablePersonAfterDeductionsDao getTablePersonAfterDeductionsDao() {
        return tablePersonAfterDeductionsDao;
    }
}

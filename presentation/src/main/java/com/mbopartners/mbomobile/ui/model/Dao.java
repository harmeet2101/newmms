package com.mbopartners.mbomobile.ui.model;

import android.app.Application;
import android.util.Log;

import com.mbopartners.mbomobile.data.db.database.controller.DbAccessController;
import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.TableBusinessManagerDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableDashboardFieldDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseTypeDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableReceiptDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableUserProfileDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableWorkOrderDao;
import com.mbopartners.mbomobile.data.db.generated.model.TableBusinessManager;
import com.mbopartners.mbomobile.data.db.generated.model.TableDashboardField;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseData;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpenseType;
import com.mbopartners.mbomobile.data.db.generated.model.TableReceipt;
import com.mbopartners.mbomobile.data.db.generated.model.TableUserProfile;
import com.mbopartners.mbomobile.data.db.generated.model.TableWorkOrder;
import com.mbopartners.mbomobile.rest.model.Converter;
import com.mbopartners.mbomobile.rest.model.response.BusinessManager;
import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.MboFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class Dao {
    private static final String TAG = Dao.class.getSimpleName();

    private static final String EXPENSE_DATA__VENDOR_FIELD__NAME = "vendor";

    public static TableUserProfile loadUser(Application application) {
        DaoSession daoSession = getDbAccessController(application).getDaoSession();
        TableUserProfileDao dao = daoSession.getTableUserProfileDao();
        return dao.queryBuilder().limit(1).unique();
    }

    public static BusinessManager loadBusinessManagerInfo(Application application) {
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        TableBusinessManagerDao dao = daoSession.getTableBusinessManagerDao();
        List<TableBusinessManager> managers = dao.loadAll();
        return Converter.toWeb_BusinessManager(managers.get(0));
    }

    public static List<DashboardField> loadDashboardFields(Application application) {
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        TableDashboardFieldDao dao = daoSession.getTableDashboardFieldDao();
        List<TableDashboardField> tableDashboardFields = dao.loadAll();
        return Converter.toWeb_DashboardField(tableDashboardFields);
    }

    public static List<WorkOrder> loadWorkOrdersFullTree(Application application) {
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        TableWorkOrderDao workOrderDao = daoSession.getTableWorkOrderDao();
        List<TableWorkOrder> workOrders = workOrderDao.queryBuilder().list();
        return Converter.toWeb_WorkOrder(workOrders);
    }

    public static List<ExpenseTimesheetItem> loadExpenseTimesheet(Application application) {
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        TableExpenseDao expenseDao = daoSession.getTableExpenseDao();
        TableWorkOrderDao workOrderDao = daoSession.getTableWorkOrderDao();
        TableExpenseTypeDao expenseTypeDao = daoSession.getTableExpenseTypeDao();

        List<TableExpense> tableExpense = expenseDao.loadAll();
        List<ExpenseTimesheetItem> expenseItems = new ArrayList<>(tableExpense.size());
        ExpenseTimesheetItem timesheetItem;
        for (TableExpense expenseTableRecord : tableExpense) {
            String mboWorkOrderId = expenseTableRecord.getMboWorkOrderId();
            String lastChangedDate=expenseTableRecord.getLastChangedDate();
            String workOrderName;
            String workOrderCompanyName;
            TableWorkOrder workOrder = resolveWorkOrder(workOrderDao, mboWorkOrderId);
            if (workOrder != null) {
                workOrderName = workOrder.getName();
                workOrderCompanyName = workOrder.getTableCompany().getName();
            } else {
                workOrderName = "";
                workOrderCompanyName = "";
            }

            TableExpenseType tableExpenseType = resolveExpenseType(expenseTypeDao, expenseTableRecord.getMboExpenseTypeId());
            String expenseTypeName = tableExpenseType.getName();

            Map<String, String> expenseDataMap = Converter.to_ExpenseData_Map(expenseTableRecord.getExpenseData());
            String dateStr = expenseDataMap.get(ExpenseField.ID__EXPENSE_DATE__DATA_FIELD);
            String endDateStr = expenseDataMap.get(ExpenseField.ID_EXPENSE_DATE_DATA_FIELD_END);
            Date expenseVirtualDate = getExpenseVirtualDate(expenseTableRecord.getExpenseData());
            Date currentDate = DateUtil.getCurrentDateForExpenseSort();

                    timesheetItem = new ExpenseTimesheetItem(
                            expenseTableRecord.getMboId(),
                            expenseTableRecord.getMboExpenseTypeId(),
                            expenseTypeName,
                            MboFormatter.Money.formatMoney(expenseTableRecord.getAmount()),
                            extractVendor(expenseTableRecord),
                            expenseTableRecord.getEditable(),
                            workOrderCompanyName,
                            workOrderName,
                            expenseVirtualDate,endDateStr,currentDate
            );
            expenseItems.add(timesheetItem);
        }
        return expenseItems;
    }

    private static Date getExpenseVirtualDate(List<TableExpenseData> ExpenseData) {
        Map<String, String> expenseDataMap = Converter.to_ExpenseData_Map(ExpenseData);
        String dateStr = expenseDataMap.get(ExpenseField.ID__EXPENSE_DATE__DATA_FIELD);
        Date expenseDateFromServer = DateUtil.parseFrom_yyyymmdd(dateStr);
        if (expenseDateFromServer == null) {
            Log.e(TAG, "Can not distinguish date in Expense");
        }
        expenseDateFromServer = expenseDateFromServer != null ? expenseDateFromServer : DateUtil.getCurrentDate();
        return expenseDateFromServer;
    }

    /** If mboWorkOrderId == null it is UnBillable Expense */
    public static TableWorkOrder resolveWorkOrder(TableWorkOrderDao workOrderDao, String mboWorkOrderId) {
        TableWorkOrder workOrder = null;
        if (mboWorkOrderId != null) {
            workOrder = workOrderDao.queryBuilder()
                    .where(TableWorkOrderDao.Properties.MboId.eq(mboWorkOrderId))
                    .unique();

            if (workOrder == null) {
                Log.w(TAG, "Unable to find WorkOrder with Id = " + mboWorkOrderId);
            }
        }
        return workOrder;
    }

    public static TableWorkOrder loadWorkOrder(Application application, String mboWorkOrderId) {
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        TableWorkOrderDao workOrderDao = daoSession.getTableWorkOrderDao();
        return resolveWorkOrder(workOrderDao, mboWorkOrderId);
    }

    public static TableExpenseType resolveExpenseType(TableExpenseTypeDao expenseTypeDao, String expenseTypeId) {
        return expenseTypeDao.load(expenseTypeId);
    }

    public static String extractVendor(TableExpense expenseTableRecord) {
        String vendor = extractExpenseDataFieldValue(expenseTableRecord, EXPENSE_DATA__VENDOR_FIELD__NAME);
        return (vendor.isEmpty() ? "No Vendor" : vendor);
    }

    public static String extractExpenseDataFieldValue(TableExpense expenseTableRecord, String fieldName) {
        expenseTableRecord.resetReceipts();
        expenseTableRecord.resetExpenseData();
        List<TableExpenseData> expenseData = expenseTableRecord.getExpenseData();
        for (TableExpenseData dataRecord : expenseData) {
            if (dataRecord.getName().equals(fieldName)) {
                return dataRecord.getValue();
            }
        }
        return "";
    }

    public static List<TableExpenseType> loadExpenseTypes(Application application) {
        DaoSession daoSession = getDbAccessController(application).getDaoSession();
        TableExpenseTypeDao dao = daoSession.getTableExpenseTypeDao();

        return dao.loadAll();
    }

    public static TableExpenseType loadTableExpenseType(Application application, String mboExpenseTypeId) {
        DaoSession daoSession = getDbAccessController(application).getDaoSession();
        TableExpenseTypeDao dao = daoSession.getTableExpenseTypeDao();

        TableExpenseType expenseType = null;
        if (mboExpenseTypeId != null) {
            expenseType = dao.queryBuilder()
                    .where(TableExpenseTypeDao.Properties.MboId.eq(mboExpenseTypeId))
                    .unique();
            if (expenseType == null) {
                Log.w(TAG, "Unable to find ExpenseType with Id = " + mboExpenseTypeId);
            }
        }
        return expenseType;
    }

    public static TableExpense loadTableExpense(Application application, String mboExpenseId) {
        DaoSession daoSession = getDbAccessController(application).getDaoSession();
        TableExpenseDao dao = daoSession.getTableExpenseDao();

        TableExpense expense = null;
        if (mboExpenseId != null) {
            expense = dao.queryBuilder()
                    .where(TableExpenseDao.Properties.MboId.eq(mboExpenseId))
                    .unique();
            if (expense == null) {
                Log.w(TAG, "Unable to find Expense with Id = " + mboExpenseId);
            }
        }
        return expense;
    }

    public static void deleteExpenseReceipt(Application application, String mboExpenseId, String receiptFilename) {
        DaoSession daoSession = getDbAccessController(application).getDaoSession();
        TableReceiptDao dao = daoSession.getTableReceiptDao();

        if (mboExpenseId != null) {
            List<TableReceipt> receipts = dao.queryBuilder()
                    .where(TableReceiptDao.Properties.Filename.eq(receiptFilename), TableReceiptDao.Properties.MboExpenseId.eq(mboExpenseId))
                    .list();
            if (receipts != null) {
                for (TableReceipt receipt : receipts) {
                    receipt.delete();
                }
            } else {
                Log.w(TAG, "Unable to find Expense Receipt with Id = " + mboExpenseId + " filename = " + receiptFilename);
            }
        }
    }

    // ================================================================================

    public static DbAccessController getDbAccessController(Application application) {
        IApplicationControllersManager applicationControllersManager = (IApplicationControllersManager) application;
        DbAccessController dbAccessController = (DbAccessController) applicationControllersManager.getController(Controllers.CONTROLLER__DATABASE);
        return dbAccessController;
    }




}
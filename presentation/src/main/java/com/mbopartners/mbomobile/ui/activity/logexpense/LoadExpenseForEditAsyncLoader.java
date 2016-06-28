package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.data.db.generated.model.TableWorkOrder;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.model.Dao;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

public class LoadExpenseForEditAsyncLoader extends AsyncTaskLoader<ExpenseForEdit> {
    private static final String TAG = LoadExpenseForEditAsyncLoader.class.getSimpleName();

    private Application application;
    private ExpenseForEdit expenseForEdit;

    public LoadExpenseForEditAsyncLoader(Application application, Bundle param) {
        super(application);
        Log.v(TAG, "Constructor");
        this.application = application;

        expenseForEdit = new ExpenseForEdit();
        expenseForEdit.mboId = ActivityIntentHelper.LogExpenseActivityBuilder.getExpenseId(param);
        if (expenseForEdit.isItNewExpense()) {
            // It is new expense
            expenseForEdit.mboExpenseTypeId = ActivityIntentHelper.LogExpenseActivityBuilder.getExpenseTypeId(param);
            expenseForEdit.mboWorkOrderId = ActivityIntentHelper.LogExpenseActivityBuilder.getWorkOrderId(param);
            expenseForEdit.amount = 0d;
        }
    }

    @Override
    public ExpenseForEdit loadInBackground() {
        Log.v(TAG, "loadInBackground()");

        if (expenseForEdit.isItNewExpense()) {
            // It is new expense
            String mboAssociateId = Dao.loadUser(application).getMboId();
            expenseForEdit.fillWithNew(application, mboAssociateId);
        } else {
            // It is existing Expense
            // Load Expense
            TableExpense expense = Dao.loadTableExpense(application, expenseForEdit.mboId);
            expenseForEdit.fillWithExisting(application, expense);
        }

        if (expenseForEdit.isItBillableExpense()) {
            // Billable expense
            TableWorkOrder tableWorkOrder = Dao.loadWorkOrder(application, expenseForEdit.mboWorkOrderId);
            expenseForEdit.companyName = tableWorkOrder.getTableCompany().getName();
            expenseForEdit.workOrderName = tableWorkOrder.getName();
        }

        /**
         * Prepare for correct view accordingly business logic
         */
        /*if (expenseForEdit.isItBillableExpense()) {
            expenseForEdit.removePurposeFieldFromExpenseData();
            expenseForEdit.removePurposeFieldFromExpenseTypeFields();
        } else {
            expenseForEdit.setPurposeFieldAsMandatoryField();
        }*/

        return expenseForEdit;
    }
}

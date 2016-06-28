package com.mbopartners.mbomobile.ui.activity.choose_workorder;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.model.Dao;

import java.util.List;

public class ChooseWorkOrderAsyncLoader extends AsyncTaskLoader<List<WorkOrder>> {

    private static final String KEY__LOAD_MODE = "Load WorkOrders with WorkOrder for Unbillable expese or not";
    private static final boolean MODE__WITHOUT_UNBILLABLE_WORKORDER = false;
    private static final boolean MODE__WITH_UNBILLABLE_WORKORDER = true;

    private Application application;
    private boolean withUnBillableWorkOrder;

    public ChooseWorkOrderAsyncLoader(Application application, Bundle param) {
        super((Context) application);

        this.application = application;

        boolean withUnBillableWorkOrder = getLoadMode(param);
        this.withUnBillableWorkOrder = withUnBillableWorkOrder;
    }

    @Override
    public List<WorkOrder> loadInBackground() {
        List<WorkOrder> allWorkOrders = Dao.loadWorkOrdersFullTree(application);
        boolean nonBillableAllowed = Dao.loadUser(application).getNonbillableAllowed();

        List<WorkOrder> workOrders;

        if(withUnBillableWorkOrder) {
            workOrders = Converter.filterBillableExpensesAllowedWorkOrders(allWorkOrders);
            if (nonBillableAllowed) {
                WorkOrder unBillableWorkOrder = new WorkOrder(
                        null,
                        "Nonbillable Expense",
                        null,
                        false,
                        false,
                        null,
                        null
                );
                workOrders.add(0, unBillableWorkOrder);
            }
        } else {
            workOrders = Converter.filterAllowedWorkOrders(allWorkOrders);
        }

        return workOrders;
    }

    private boolean getLoadMode(Bundle param) {
        return param.getBoolean(KEY__LOAD_MODE, MODE__WITHOUT_UNBILLABLE_WORKORDER);
    }

    public static Bundle getParamForTimeTracking() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY__LOAD_MODE, MODE__WITHOUT_UNBILLABLE_WORKORDER);
        return bundle;
    }

    public static Bundle getParamForExpenseTracking() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY__LOAD_MODE, MODE__WITH_UNBILLABLE_WORKORDER);
        return bundle;
    }
}

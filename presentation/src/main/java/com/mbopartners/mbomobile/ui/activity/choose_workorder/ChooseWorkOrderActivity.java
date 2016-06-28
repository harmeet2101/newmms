package com.mbopartners.mbomobile.ui.activity.choose_workorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.MenuItem;

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.util.List;

public class ChooseWorkOrderActivity extends AutoLockActivity
        implements ChoseWorkOrderActivityFragment.OnFragmentInteractionListener ,
        LoaderManager.LoaderCallbacks<List<WorkOrder>> {

    private static final String TAG = ChooseWorkOrderActivity.class.getSimpleName();
    private static final int LOADER__WORK_ORDERS__ID = 1;

    private List<WorkOrder> workOrdersList = null;
    private ChoseWorkOrderActivityFragment fragment;
    private boolean isRunForTimeTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle loaderParam;

        isRunForTimeTracking = ActivityIntentHelper.ChooseWorkOrderActivityBuilder.isRunForTimeTracking(this);

        if (isRunForTimeTracking) {
            setContentView(R.layout.activity_choose_work_order_for_time);
            loaderParam = ChooseWorkOrderAsyncLoader.getParamForTimeTracking();
        } else {
            setContentView(R.layout.activity_choose_work_order_for_expense);
            loaderParam = ChooseWorkOrderAsyncLoader.getParamForExpenseTracking();
        }

        setupUpAppTabButtonIfPossible();

        fragment = (ChoseWorkOrderActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        getSupportLoaderManager().initLoader(LOADER__WORK_ORDERS__ID, loaderParam, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoadingWorkOrders();
    }

    // ================================================================================
    //
    // Loader Callback interface implementation
    //
    // ================================================================================

    @Override
    public Loader<List<WorkOrder>> onCreateLoader(int id, Bundle args) {
        Loader<List<WorkOrder>> loader = null;
        if (id == LOADER__WORK_ORDERS__ID) {
            loader = new ChooseWorkOrderAsyncLoader(this.getApplication(), args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<WorkOrder>> loader, List<WorkOrder> data) {
        workOrdersList = data;
        notifyDataLoaded();
    }

    @Override
    public void onLoaderReset(Loader<List<WorkOrder>> loader) {

    }

    // ---------- Interception of navigation buttons ----------------------------------

    // Intercept UP Button on the Toolbar (arrow)
    // Intercept SAVE Button on the Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // ---------- END OF Interception navigation buttons ------------------------------


    // ================================================================================
    //
    // Fragment Callback interface implementation
    //
    // ================================================================================

    @Override
    public List<WorkOrder> getWorkOrdersList() {
        return workOrdersList;
    }

    @Override
    public void onWorkOrderSelected(String workOrderId) {
        if (isRunForTimeTracking) {
            startActivity(ActivityIntentHelper.LogTimeActivityBuilder.getLogTimeActivity(this, workOrderId));
        } else {
            Bundle bundleWithParameterExpenseType = ActivityIntentHelper.getBundle(this);
            Intent intent = ActivityIntentHelper.LogExpenseActivityBuilder.getActivity(this, bundleWithParameterExpenseType, workOrderId);
            startActivityForResult(intent,3);

        }
    }


    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        if(resultCode==3)
        {
            Bundle bundle=intent.getExtras();
            setResult(4,ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityExpensePage(this,bundle));
            finish();
        }

    }

    // ================================================================================
    //
    //
    //
    // ================================================================================

    private void startLoadingWorkOrders() {
        Loader<List<WorkOrder>> loader;
        loader = getSupportLoaderManager().getLoader(LOADER__WORK_ORDERS__ID);
        loader.forceLoad();
    }

    private void notifyDataLoaded() {
        if (fragment != null) {
            fragment.onWorkOrdersReceived();
        }
    }
}

package com.mbopartners.mbomobile.ui.activity.choose_expense_type;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.choose_workorder.ChooseWorkOrderAsyncLoader;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;


public class ChooseExpenseTypeActivity extends AutoLockActivity
        implements ChooseExpenseTypeActivityFragment.OnFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<List<ExpenseType>> {

    private static final String TAG = ChooseExpenseTypeActivity.class.getSimpleName();
    private static final int LOADER__EXPENSE_TYPES__ID = 1;
    private static final int LOADER__WORK_ORDERS__ID = 2;
    private List<ExpenseType> expenseTypesList = null;
    private ChooseExpenseTypeActivityFragment fragment;
    private LoaderManager.LoaderCallbacks<List<WorkOrder>> loaderCallbacks;
    private List<WorkOrder> workOrders;
    private int workOrders_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_expense_type);
        setupUpAppTabButtonIfPossible();
        Bundle loaderParam;
        fragment = (ChooseExpenseTypeActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        getSupportLoaderManager().initLoader(LOADER__EXPENSE_TYPES__ID, null, this);
        loaderParam = ChooseWorkOrderAsyncLoader.getParamForTimeTracking();
        loaderCallbacks=new LoaderManager.LoaderCallbacks<List<WorkOrder>>() {
            @Override
            public Loader<List<WorkOrder>> onCreateLoader(int id, Bundle args) {

                Loader<List<WorkOrder>> loader = null;
                if (id == LOADER__WORK_ORDERS__ID) {
                    loader = new ChooseWorkOrderAsyncLoader(getApplication(), args);
                }
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<WorkOrder>> loader, List<WorkOrder> data) {

                workOrders = data;
                workOrders_size = workOrders.size();
            }

            @Override
            public void onLoaderReset(Loader<List<WorkOrder>> loader) {

            }
        };
        getSupportLoaderManager().initLoader(LOADER__WORK_ORDERS__ID, loaderParam,loaderCallbacks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoadingExpenseTypes();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // ================================================================================
    //
    // Loader Callback interface implementation
    //
    // ================================================================================

    @Override
    public Loader<List<ExpenseType>> onCreateLoader(int id, Bundle args) {
        Loader<List<ExpenseType>> loader = null;
        if (id == LOADER__EXPENSE_TYPES__ID) {
            loader = new ExpenseTypesAsyncLoader(this.getApplication());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ExpenseType>> loader, List<ExpenseType> data) {
        expenseTypesList = data;
        notifyDataLoaded();
        Loader<List<WorkOrder>> loader01;
        loader01 = getSupportLoaderManager().getLoader(LOADER__WORK_ORDERS__ID);
        loader01.forceLoad();
    }

    @Override
    public void onLoaderReset(Loader<List<ExpenseType>> loader) {

    }

    // ================================================================================
    //
    // Fragment Callback interface implementation
    //
    // ================================================================================

    @Override
    public List<ExpenseType> getExpenseTypesList() {
        return expenseTypesList;
    }

    @Override
    public void onExpenseTypeSelected(String expenseTypeId,int postion) {

        Log.d("itemPos", "" + postion);
        Iterator iterator = workOrders.iterator();
        while(iterator.hasNext())
        {
            Log.d("workoederslist",iterator.next().toString());
        }

        //Log.d("workorder",workOrders.get(0).getName()+" : "+workOrders.get(1).getName()+" : "+workOrders.get(2).getName());
        if(workOrders_size==1)
        {
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, MODE__EXPENSE_TRACKING);
            bundle.putString(KEY__EXPENSETYPE_ID, expenseTypeId);
            bundle.putInt("value", 1);
            Intent intent = ActivityIntentHelper.LogExpenseActivityBuilder.getActivity(this, bundle, workOrders.get(0).getId());
            startActivityForResult(intent, 3);
        }else if(workOrders.size()>1){
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, MODE__EXPENSE_TRACKING);
            bundle.putString(KEY__EXPENSETYPE_ID, expenseTypeId);
            bundle.putSerializable("serial", (Serializable) workOrders);
            Intent intent = ActivityIntentHelper.LogExpenseActivityBuilder.getActivity(this, bundle,workOrders.get(0).getId());
            startActivityForResult(intent, 4);
            //startActivityForResult(ActivityIntentHelper.ChooseWorkOrderActivityBuilder.getActivityForExpenseTracking(this, expenseTypeId), 4);
        }

    }

    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        if(resultCode==3)
        {
            Bundle bundle=intent.getExtras();
            setResult(2,ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityExpensePage(this,bundle));
            finish();
        }
    }

    // ================================================================================
    //
    //
    //
    // ================================================================================

    private void startLoadingExpenseTypes() {
        Loader<List<ExpenseType>> loader;
        loader = getSupportLoaderManager().getLoader(LOADER__EXPENSE_TYPES__ID);
        loader.forceLoad();
    }

    private void notifyDataLoaded() {
        if (fragment != null) {
            fragment.onExpenseTypesReceived();
        }
    }

    private static final String KEY__CHOOSE_MODE = "For LogTime or LogExpense Mode";
    private static final String KEY__EXPENSETYPE_ID = "MboExpenseTypeId";
    private static final boolean MODE__EXPENSE_TRACKING = true;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_expense_type, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PreviousPayment;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MboAdil on 14/7/16.
 */
public class PreviousPaymentsActivity extends AutoLockActivity implements PreviousPaymentFragment.OnFragmentInteractionListener/*,
        LoaderManager.LoaderCallbacks<List<PayrollSummary>>*/{

    private PreviousPayment previousPayment;
    private PreviousPaymentFragment fragment;
    private static final String TAG = PreviousPaymentsActivity.class.getSimpleName();
    private LoaderManager.LoaderCallbacks<List<PayrollSummary>> loaderCallbacks;
    private List<PayrollTransactions> payrollTransactionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payroll_previous_payments);
        setupUpAppTabButtonIfPossible();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            //previousPayment= (PreviousPayment) bundle.getSerializable("PARAMETERS AS SERIALIZABLE");
            payrollTransactionsList= (List<PayrollTransactions>) bundle.getSerializable("summaryList");

        }

        fragment = (PreviousPaymentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataLoaded();

    }

    private void notifyDataLoaded() {
        if (fragment != null) {
            fragment.onPersonWithHoldingsDataReceived();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

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


    public List<PayrollTransactions> getPayrollSummary() {

        if(payrollTransactionsList!=null)
        {
            return payrollTransactionsList;
        }else
        return null;
    }

    public void onItemClick(int position)
    {
        Bundle bundle=new Bundle();
        bundle.putSerializable("summaryList", (Serializable) payrollTransactionsList);
        bundle.putInt("position",position);
        Intent intent= ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getActivity(PreviousPaymentsActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

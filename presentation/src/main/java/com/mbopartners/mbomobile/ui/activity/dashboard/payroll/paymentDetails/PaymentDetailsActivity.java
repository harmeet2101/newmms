package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;

import java.util.List;

public class PaymentDetailsActivity extends AutoLockActivity implements PaymentDetailsFragment.OnFragmentInteractionListener{


    private PaymentDetailsFragment fragment;
    private static final String TAG = PaymentDetailsActivity.class.getSimpleName();
    private TextView deleteTextview,doneTextview;
    private List<PayrollSummary> payrollSummaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payroll_payment_details);
        setupUpAppTabButtonIfPossible();
        setUpUI();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            payrollSummaryList= (List<PayrollSummary>) bundle.getSerializable("summaryList");

        }

        fragment = (PaymentDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    public void setUpUI(){

        deleteTextview=(TextView)findViewById(R.id.deletetext);
        doneTextview=(TextView)findViewById(R.id.donetext);
    }


    public void deletePayment(View view){

        showAlertDialog();
    }


    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mbo_payroll_delete_payment_text);
        builder.setMessage(R.string.mbo_payroll_delete_payment_alert_msg);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        AlertDialog discardDialog = builder.create();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        discardDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        discardDialog.show();
    }
    public void confirmPayment(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataLoaded();

    }

    private void notifyDataLoaded() {
        if (fragment != null) {
            fragment.onDataReceived();
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

    @Override
    public List<PayrollSummary> getPayrollSummary() {
        if(payrollSummaryList!=null)
        {
            return payrollSummaryList;
        }else
            return null;

    }

    @Override
    public void onItemClick(int position) {

    }
}

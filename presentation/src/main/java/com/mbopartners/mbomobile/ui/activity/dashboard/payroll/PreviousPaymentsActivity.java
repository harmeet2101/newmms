package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.app.Activity;
import android.os.Bundle;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;

/**
 * Created by MboAdil on 14/7/16.
 */
public class PreviousPaymentsActivity extends AutoLockActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payroll_previous_payments);
       // setupUpAppTabButtonIfPossible();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

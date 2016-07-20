package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PreviousPayment;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.DashboardFragmentPagerAdapter;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.util.List;

/**
 * Created by MboAdil on 19/7/16.
 */
public class PayrollWithHoldings extends AutoLockActivity {

    private static final String TAG = PayrollWithHoldings.class.getSimpleName();
    private ViewPager viewPager;
    public static final int DO_NOT_CHANGE_INDEX = -1;
    public static final int BUSINESS_FRAGMENT_INDEX = 0;
    public static final int PERSONAL_FRAGMENT_INDEX = 1;
    private int pageIndex = PERSONAL_FRAGMENT_INDEX;
    private TabLayout tabLayout;
    private List<PayrollSummary> payrollSummaryList;
    private static final String KEY_SAVED_INSTANCE_STATE__PAGE_INDEX = "int pageIndex";


    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent()");
        pageIndex = ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getPageIndex(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            payrollSummaryList= (List<PayrollSummary>) bundle.getSerializable("summaryList");
        }
        if (savedInstanceState != null) {
            pageIndex = ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getPageIndex(savedInstanceState);
        } else {
            pageIndex = ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getPageIndex(this);
        }
        setContentView(R.layout.layout_payroll_withholdings);
        setupUpAppTabButtonIfPossible();
        setupTabs();

    }


    private void setupTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        PayrollWithHoldingPagerAdapter pagerAdapter = new PayrollWithHoldingPagerAdapter(getSupportFragmentManager(), PayrollWithHoldings.this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTabsFont() {

        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/Roboto-Medium.ttf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setCurrentTabItem();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setCurrentTabItem() {
        if (pageIndex != DO_NOT_CHANGE_INDEX) {
            viewPager.setCurrentItem(pageIndex);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        pageIndex = savedInstanceState.getInt(KEY_SAVED_INSTANCE_STATE__PAGE_INDEX, PERSONAL_FRAGMENT_INDEX);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        pageIndex = viewPager.getCurrentItem();
        outState.putInt(KEY_SAVED_INSTANCE_STATE__PAGE_INDEX, pageIndex);
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
}

package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings;

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

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessWithHolding;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewEstimatedWithHolding extends AutoLockActivity implements PersonViewEstimatedFragment.PersonalHoldingInteractionListener,BusinessViewEstimatedFragment.BusinessWithHoldingInteractionListener {

    private static final String TAG = ViewEstimatedWithHolding.class.getSimpleName();
    private ViewPager viewPager;
    public static final int DO_NOT_CHANGE_INDEX = -1;
    public static final int BUSINESS_FRAGMENT_INDEX = 0;
    public static final int PERSONAL_FRAGMENT_INDEX = 1;
    private int pageIndex = BUSINESS_FRAGMENT_INDEX;
    private TabLayout tabLayout;
    private List<PayrollTransactions> payrollTransactionsList;
    private static final String KEY_SAVED_INSTANCE_STATE__PAGE_INDEX = "int pageIndex";
    private int position;

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent()");
        pageIndex = ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getPageIndex(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payroll_withholdings);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            payrollTransactionsList= (List<PayrollTransactions>) bundle.getSerializable("summaryList");
            position=bundle.getInt("position");
        }
        setupUpAppTabButtonIfPossible();
        setupTabs();

    }

    private void setupTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        PayrollViewEstimatedPagerAdapter pagerAdapter = new PayrollViewEstimatedPagerAdapter(getSupportFragmentManager(), ViewEstimatedWithHolding.this);
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

    @Override
    public List<PersonWithHolding> getPersonWithHoldingList() {
        if(payrollTransactionsList!=null)
        {
            if(payrollTransactionsList.size()==1)
            {
                if(payrollTransactionsList.get(0).getPersonalWithholding()!=null)
                {
                    personWithHoldingList.add(payrollTransactionsList.get(0).getPersonalWithholding());
                }

            }else if(payrollTransactionsList.size()>0)
            {
                for(int i=0;i<payrollTransactionsList.size();i++)
                {
                    personWithHoldingList.add(payrollTransactionsList.get(i).getPersonalWithholding());
                }
            }
            return personWithHoldingList;
        }
        else
            return null;
    }

    private List<PersonWithHolding> personWithHoldingList=new ArrayList<>();
    private List<BusinessWithHolding> businessWithHoldingList=new ArrayList<>();

    @Override
    public List<BusinessWithHolding> getBusinessWithHoldingList() {

        if(payrollTransactionsList!=null)
        {
            if(payrollTransactionsList.size()==1)
            {
                if(payrollTransactionsList.get(0).getBusinessWithholding()!=null)
                {
                    businessWithHoldingList.add(payrollTransactionsList.get(0).getBusinessWithholding());
                }

            }else if(payrollTransactionsList.size()>0)
            {
                for(int i=0;i<payrollTransactionsList.size();i++)
                {
                    businessWithHoldingList.add(payrollTransactionsList.get(i).getBusinessWithholding());
                }
            }
            return businessWithHoldingList;
        }
        else
            return null;
    }

    @Override
    public int getSelectedItemPosition() {
        return position;
    }
}

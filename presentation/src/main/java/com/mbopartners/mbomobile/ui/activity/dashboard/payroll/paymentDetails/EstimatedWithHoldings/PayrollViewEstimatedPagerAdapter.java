package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mbopartners.mbomobile.ui.R;

import ua.com.mobidev.android.framework.ui.fragment.SmartFragmentStatePagerAdapter;

public class PayrollViewEstimatedPagerAdapter extends SmartFragmentStatePagerAdapter {

    private String tabTitles[]=new String[2];
    private Context context;

    public PayrollViewEstimatedPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        setUpTabTitles();
    }

    private void setUpTabTitles()
    {

        tabTitles[1] = this.context.getString(R.string.tab_withholding_title_personal);
        tabTitles[0] = this.context.getString(R.string.tab_withholding_title_business);

    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0 : {
                fragment =  PersonViewEstimatedFragment.newInstance("", "");
                break;
            }
            case 1 : {
                fragment =  BusinessViewEstimatedFragment.newInstance("", "");
                break;
            }
            default: {
            }
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

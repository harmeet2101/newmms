package com.mbopartners.mbomobile.ui.activity.dashboard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.dashboard.expense.ExpensePageFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PayrollFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.revenue.RevenuePageFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.timesheet.TimePageFragment;

import ua.com.mobidev.android.framework.ui.fragment.SmartFragmentStatePagerAdapter;

public class DashboardFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    int PAGE_COUNT = 4;
    private String tabTitles[];
    private Context context;

    public DashboardFragmentPagerAdapter(FragmentManager fm, Context context,int tabCount) {
        super(fm);
        this.context = context;
        PAGE_COUNT=tabCount;
        tabTitles= new String[PAGE_COUNT];
        setUpTabTitles(PAGE_COUNT);

    }

    private void setUpTabTitles(int PAGE_COUNT)
    {
        if(PAGE_COUNT==3)
        {
            tabTitles[0] = this.context.getString(R.string.tab_dashboard_title_dashboard);
            tabTitles[1] = this.context.getString(R.string.tab_dashboard_title_time);
            tabTitles[2] = this.context.getString(R.string.tab_dashboard_title_expense);
        }else
        {
            tabTitles[0] = this.context.getString(R.string.tab_dashboard_title_dashboard);
            tabTitles[1] = this.context.getString(R.string.tab_dashboard_title_time);
            tabTitles[2] = this.context.getString(R.string.tab_dashboard_title_expense);
            tabTitles[3]=this.context.getString(R.string.tab_dashboard_title_payroll);
        }
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0 : {
                fragment =  RevenuePageFragment.newInstance("", "");
                break;
            }

            case 1 : {
                fragment =  TimePageFragment.newInstance("", "",context);
                break;
            }

            case 2 : {
                fragment =  ExpensePageFragment.newInstance();
                break;
            }
            case 3 : {
                fragment =  PayrollFragment.newInstance();
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

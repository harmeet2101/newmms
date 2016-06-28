package com.mbopartners.mbomobile.ui.activity.logtime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.mbopartners.mbomobile.ui.activity.helper.TimeEntryToSave;
import com.mbopartners.mbomobile.ui.activity.helper.TimeLogDescriptor;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.Date;
import java.util.List;

public class LogDayTimePagerAdapter extends FragmentPagerAdapter {
    private TimeLogDescriptor timeLogDescriptor;
    private boolean timePeriodIsEditable;


    public LogDayTimePagerAdapter(FragmentManager fragmentManager, TimeLogDescriptor timeLogDescriptor) {
        super(fragmentManager);
        this.timeLogDescriptor = timeLogDescriptor;
        this.timePeriodIsEditable =
                timeLogDescriptor.getTimeEntryAllowed()
                && Converter.checkIsEditableTimeEntries(timeLogDescriptor.getTimePeriod().getTimeEntries());
    }

    @Override
    public Fragment getItem(int i) {
        List<TimeEntryToSave> dayTimeEntriesToSave = Converter.getTimeEntriesToSave(timeLogDescriptor.getTimeEntriesToSave(), getDate(i));
        Fragment fragment = LogDayTimeFragment.newInstance(dayTimeEntriesToSave, timeLogDescriptor.getTimeTaskId_ForSavingNote(), timePeriodIsEditable);
        return fragment;
    }

    @Override
    public int getCount() {
        if (timeLogDescriptor.getTimeEntriesToSave().size() >0 ) {
            int count=(int) DateUtil.daysBetween(this.getStartDate(), this.getEndDate()) + 1;
            return count;
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Date selectedDate = getDate(position);
        return DateUtil.getShortDate01(selectedDate);
    }

    private Date getDate(int position) {
        Date startDate = timeLogDescriptor.getTimePeriod().getStartDate();
        Date selectedDate = DateUtil.addDays(startDate, position);
        return selectedDate;
    }

    public Date getStartDate() {
        return timeLogDescriptor.getTimeEntriesToSave().get(0).getTimeEntry().getDate();
    }

    public Date getEndDate() {
        return timeLogDescriptor.getTimeEntriesToSave().get(timeLogDescriptor.getTimeEntriesToSave().size()-1).getTimeEntry().getDate();
    }


}

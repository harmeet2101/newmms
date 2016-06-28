package com.mbopartners.mbomobile.ui.activity.logtime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.helper.TimeLogDescriptor;
import com.mbopartners.mbomobile.ui.activity.helper.TimePeriodParameters;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.dialog.ApiErrorDialog;
import com.mbopartners.mbomobile.ui.model.Dao;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.DefaultRestClientResponseHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.com.mobidev.android.framework.ui.keyboard.KeyboardUtil;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class LogTimeActivity extends AutoLockActivity implements LogDayTimeFragment.OnFragmentInteractionListener,SmartLogDayRecyclerViewAdapter.ISaveTimeLogListener {

    private static final String TAG = LogTimeActivity.class.getSimpleName();
    private static final boolean NAVIGATION_BACK = false;
    private static final boolean NAVIGATION_UP = true;


    private TextView companyNameTextView;
    private TextView workOrderNameTextView;
    private TextView periodDateTextView;
    private TextView labelDateTextView;
    private ImageView crossImageview;
    private ViewPager mViewPager;
    private LogDayTimePagerAdapter mLogDayTimePagerAdapter;
    private PagerTabStrip pagerTitleStrip;
    private ProgressDialog ringProgressDialog;
    private MenuSaveButtonDescriptor menuItemSave = new MenuSaveButtonDescriptor();

    private TimeLogDescriptor timeLogDescriptor;
    private boolean navigationFlag = NAVIGATION_BACK;
    private ImageView leftImageview,rightImageview;

    private String[] companyNames;
    @Override
    public void saveTime() {
        doSaveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_time);
        setupUpAppTabButtonIfPossible();
        View rootview=findViewById(R.id.log_time_rootview);

        /*Hiding the keyboard when the user taps on No-editable filed.*/
        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.htryToHideKeyboard(LogTimeActivity.this);
            }
        });
        timeLogDescriptor = loadData(ActivityIntentHelper.LogTimeActivityBuilder.getStartingParameters(this));


        boolean timePeriodIsEditable =
                timeLogDescriptor.getTimeEntryAllowed()
                        && Converter.checkIsEditableTimeEntries(timeLogDescriptor.getTimePeriod().getTimeEntries());
        menuItemSave.updateSaveMenuItemVisibility(timePeriodIsEditable);

        mLogDayTimePagerAdapter = new LogDayTimePagerAdapter(getSupportFragmentManager(), timeLogDescriptor);

        mViewPager = (ViewPager) findViewById(R.id.date_viewpager);
        mViewPager.setAdapter(mLogDayTimePagerAdapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setBackgroundColor(getResources().getColor(R.color.mbo_color_white));
        companyNameTextView = (TextView) findViewById(R.id.company_name_TextView);

        crossImageview=(ImageView)findViewById(R.id.crossImageview);
        workOrderNameTextView = (TextView) findViewById(R.id.work_order_name_TextView);
        periodDateTextView = (TextView) findViewById(R.id.period_dates_TextView);
        labelDateTextView = (TextView) findViewById(R.id.label_date_textView);
        pagerTitleStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
        leftImageview=(ImageView)findViewById(R.id.left);
        rightImageview=(ImageView)findViewById(R.id.right);
        leftImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(pos-1);
            }
        });
        rightImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(pos + 1);
            }
        });
        crossImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pagerTitleStrip.setTabIndicatorColorResource(R.color.mbo_grey_layout_bg__color);

        labelDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        stayOnChosenDay(mViewPager, timeLogDescriptor);

        IRestClient restClient = getRestServiceHelper();
        DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
        final IRestClient.Callback saveTimeEntriesCallback = new SaveTimeEntriesCallback(defaultRestClientResponseHandler);
        restClient.registerCallback(RestApiContract.Method.saveTimeEntries, saveTimeEntriesCallback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
        companyNameTextView.setText(timeLogDescriptor.getCompany().getName());
        companyNames=new String[]{timeLogDescriptor.getCompany().getName()};
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,companyNames);
//        companyNamesSpinner.setAdapter(arrayAdapter);

        setUpNavIcons();

        /*workOrderNameTextView.setText(timeLogDescriptor.getWorkOrderName());
        periodDateTextView.setText(DateUtil.getFullFormattedPeriodAsString(timeLogDescriptor.getTimePeriod().getStartDate(), timeLogDescriptor.getTimePeriod().getEndDate()));*/

    }

    public void setUpNavIcons()
    {
        int currentPosition=mViewPager.getCurrentItem();
        Log.d("current", "" + currentPosition);
        counter=getCount();
        if(currentPosition==0 && counter>0)
        {
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_left_disable));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_right));

        }else if(currentPosition==0 && counter==1)
        {
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_left_disable));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_right_disable));

        }else if(currentPosition==counter-1){
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_lesft));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_right_disable));
        }else
        {
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_lesft));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_right));
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, "onRestoreInstanceState()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressDialog();
        KeyboardUtil.htryToHideKeyboard(this);
        Log.v(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_time, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menuItemSave.setMenuItemSave(menu.findItem(R.id.action_save));
        return super.onPrepareOptionsMenu(menu);
    }

    // ---------- Interception of navigation buttons ----------------------------------

    // Intercept UP Button on the Toolbar (arrow)
    // Intercept SAVE Button on the Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        /*if (id == R.id.action_save) {
            doSaveData();
            return true;
        }*/

        if (id == android.R.id.home) {
            navigationFlag = NAVIGATION_BACK;
            showExitDialogIfNeed(timeLogDescriptor.isTimeEntriesChanged());
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //  Intercept Back Button For Android 5+
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigationFlag = NAVIGATION_BACK;
        showExitDialogIfNeed(timeLogDescriptor.isTimeEntriesChanged());
    }

    // Intercept Back Button For Android 4-
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            navigationFlag = NAVIGATION_BACK;
            showExitDialogIfNeed(timeLogDescriptor.isTimeEntriesChanged());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // ---------- END OF Interception navigation buttons ------------------------------


    @Override
    public void onItemChanged() {
        // TODO
    }

    @Override
    public void recyclerViewScrolled(int dx,int dy){
        if (dy != 0) {
            leftImageview.setAlpha(leftImageview.getAlpha() - (float)dy/100);
            rightImageview.setAlpha(rightImageview.getAlpha() - (float)dy/100);
            labelDateTextView.setAlpha(labelDateTextView.getAlpha() - (float)dy/100);
        }else{
            //Keyboard was dismissed so reset the alphas to 0
            leftImageview.setAlpha(1.0f);
            rightImageview.setAlpha(1.0f);
            labelDateTextView.setAlpha(1.0f);
        }
    }

    private void doSaveData() {
        KeyboardUtil.htryToHideKeyboard(LogTimeActivity.this);
        showProgressDialog();
        IRestClient restClient = getRestServiceHelper();
        List<TimeEntry> timeEntries = Converter.getTimeEntries(timeLogDescriptor.getTimeEntriesToSave());
        restClient.saveTimeEntries(this, timeLogDescriptor.getWorkOrderId(), timeLogDescriptor.getTimePeriod().getId(), timeEntries);
    }

    private void doAfterSuccessSavingRoutine() {
        showSuccessToast();
        String workOrderId = timeLogDescriptor.getWorkOrderId();
        String timePeriodId = timeLogDescriptor.getTimePeriod().getId();
        startActivity(ActivityIntentHelper.WeeklyTimeSheetActivityBuilder.getWeeklyTimeSheetActivity(this, workOrderId, timePeriodId,timeLogDescriptor.getTimePeriod().getSubmittable()));
        finish();
    }

    private void showExitDialogIfNeed(boolean userChangedData) {
        if (! userChangedData) {
            doNavigation(navigationFlag);
            return;
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mbo_Discard_changes_dialog_title);
        builder.setMessage(R.string.mbo_Discard_changes_dialog_message);
        builder.setPositiveButton(R.string.mbo_Discard_changes_dialog_Button_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                doNavigation(navigationFlag);
            }
        });
        builder.setNegativeButton(R.string.mbo_Discard_changes_dialog_Button_NO, new DialogInterface.OnClickListener() {
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

    private void doNavigation(boolean navigationFlag) {
        if (navigationFlag == NAVIGATION_BACK) {
            // nothing to do
        } else {
            startActivity(ActivityIntentHelper.WeeklyTimeSheetActivityBuilder.getWeeklyTimeSheetActivity(this, timeLogDescriptor.getWorkOrderId(), timeLogDescriptor.getTimePeriod().getId(),timeLogDescriptor.getTimePeriod().getSubmittable()));
        }
        finish();
    }

    private void __showErrorSavingDialog() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mbo_LogTime_Save_Error_dialog_title);
        builder.setMessage(R.string.mbo_LogTime_Save_Error_dialog_message);
        builder.setPositiveButton(R.string.mbo_LogTime_Save_Error_dialog_Button_OK, new DialogInterface.OnClickListener() {
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

    private void showSuccessToast() {
        ActivityToaster.showToastLongMessage(this, R.string.mbo_snackbar_success_time_save_message);
    }

    private void stayOnChosenDay(ViewPager viewPager, TimeLogDescriptor timeLogDescriptor) {

        int index = findDateIndex(timeLogDescriptor.getChosenDate());

        if (index < 0) {
            index = findDateIndex(timeLogDescriptor.getCurrentDate());
        }
        if (index < 0) {
            index = viewPager.getAdapter().getCount() - 1;
        }

        viewPager.setCurrentItem(index);
    }

    private int findDateIndex(Date date) {
        if (date == null) {
            return -1;
        }

        Date startDate = timeLogDescriptor.getTimePeriod().getStartDate();
        Date endDate = timeLogDescriptor.getTimePeriod().getEndDate();
        int index = (int) DateUtil.daysBetween(startDate, date);

        if (index > 6 || index < 0) {
            index = -1;
        }
        return index;
    }

    public void showProgressDialog() {
        ringProgressDialog = ProgressDialog.show(LogTimeActivity.this, "Please wait...",getResources().getString(R.string.mbo_saving_time_dialogbox_msg), true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgressDialog() {
        if (ringProgressDialog != null) {
            ringProgressDialog.dismiss();
        }
    }
    // --------------------------------------------------------------------------------

    TimeLogDescriptor loadData(TimePeriodParameters parameters) {
        TimeLogDescriptor timeLogDescriptor;
        String workOrderId;
        String timePeriodId;
        List<WorkOrder> workOrders = Dao.loadWorkOrdersFullTree(this.getApplication());

        if (parameters.mboWorkOrderId == null) {
            throw new IllegalArgumentException("WorkOrder should not be null");
        } else {
            workOrderId = parameters.mboWorkOrderId;
        }

        if (parameters.mboTimePeriodId == null) {
            WorkOrder workOrder = Converter.findWorkOrder(workOrders, workOrderId);
            timePeriodId = Converter.autoChoseTimePeriod(workOrder).getId();
        } else {
            timePeriodId = parameters.mboTimePeriodId;
        }

       timeLogDescriptor =  new TimeLogDescriptor(workOrders, workOrderId, timePeriodId);

        if (parameters.chosenDate == null) {
            timeLogDescriptor.setChosenDate(DateUtil.getCurrentDate());
        } else {
            timeLogDescriptor.setChosenDate(parameters.chosenDate);
        }

        return timeLogDescriptor;
    }

    // --------------------------------------------------------------------------------

    public void showTimePickerDialog(View v) {
        int currentItem = mViewPager.getCurrentItem();
        Date statDate = mLogDayTimePagerAdapter.getStartDate();
        Date endDate = mLogDayTimePagerAdapter.getEndDate();
        Date currentDate = DateUtil.addDays(statDate, currentItem);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialogListener listener = new DatePickerDialogListener();
        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month, day);

        dialog.getDatePicker().setMinDate(statDate.getTime());
        dialog.getDatePicker().setMaxDate(endDate.getTime());

        dialog.show();
    }

    private class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date selectedDate = calendar.getTime();
            int dateIndex= LogTimeActivity.this.findDateIndex(selectedDate);
            LogTimeActivity.this.mViewPager.setCurrentItem(dateIndex);
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            KeyboardUtil.htryToHideKeyboard(LogTimeActivity.this);

            enableDisableNavigationIcons(position);


        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    public void enableDisableNavigationIcons(int position)
    {
        if(position>0)
        {
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_lesft));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_right));

            if(position==counter-1)
            {
                rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_right_disable));
            }
        }else{
            leftImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_left_disable));
            rightImageview.setImageDrawable(getResources().getDrawable(R.drawable.worktracking_ic_right));
        }
    }


    class SaveTimeEntriesCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public SaveTimeEntriesCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            hideProgressDialog();
            switch (response.getClientResult()) {
                case Ok : {
                    doAfterSuccessSavingRoutine();
                    break;
                }

                case HttpError : {
                    int httpErrorCode = response.getClientResult().getStatusCode();
                    switch (httpErrorCode) {
                        case 400 : {
                            ApiErrorDialog.showApi_FailedToUpdateError(LogTimeActivity.this);
                            break;
                        }
                        default : {
                            defaultHandler.onComplete(response);
                        }
                    }
                    break;
                }

                default: {
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    private class MenuSaveButtonDescriptor {
        private MenuItem menuItemSave;
        private boolean buttonEnableState;

        public MenuSaveButtonDescriptor() {
            this.buttonEnableState = false;
        }

        public void setMenuItemSave(MenuItem menuItemSave) {
            this.menuItemSave = menuItemSave;
            this.menuItemSave.setEnabled(this.buttonEnableState);
        }

        public void updateSaveMenuItemVisibility(boolean visibility) {
            this.buttonEnableState = visibility;
            if (this.menuItemSave != null) {
                this.menuItemSave.setEnabled(this.buttonEnableState);
            }
        }
    }

    private int counter;
    public Date getStartDate() {
        return timeLogDescriptor.getTimeEntriesToSave().get(0).getTimeEntry().getDate();
    }

    public Date getEndDate() {
        return timeLogDescriptor.getTimeEntriesToSave().get(timeLogDescriptor.getTimeEntriesToSave().size()-1).getTimeEntry().getDate();
    }

    public int getCount()
    {
        int count=(int) DateUtil.daysBetween(this.getStartDate(), this.getEndDate()) + 1;
        Log.d("count",""+count);
        return count;
    }
}

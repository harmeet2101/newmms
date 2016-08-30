package com.mbopartners.mbomobile.ui.activity.dashboard;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.model.response.BusinessManager;
import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.rest.model.response.TimePeriod;
import com.mbopartners.mbomobile.rest.model.response.UserProfile;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.expense.ExpensePageFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.expense.ExpenseRecyclerViewAdapter;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PayrollFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PayrollRecyclerViewAdapter;
import com.mbopartners.mbomobile.ui.activity.dashboard.revenue.RevenuePageFragment;
import com.mbopartners.mbomobile.ui.activity.dashboard.timesheet.SmartSectionedRecyclerViewAdapter;
import com.mbopartners.mbomobile.ui.activity.dashboard.timesheet.TimePageFragment;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.loader.DbAsyncLoader;
import com.mbopartners.mbomobile.ui.loader.IDataSource;
import com.mbopartners.mbomobile.ui.model.Dao;
import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;
import com.mbopartners.mbomobile.ui.util.Communicator;
import com.mbopartners.mbomobile.ui.util.DefaultRestClientResponseHandler;
import com.mbopartners.mbomobile.ui.util.Security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.framework.ui.keyboard.KeyboardUtil;
import ua.com.mobidev.android.mdrest.web.rest.processor.IHttpRequestProcessor;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class DashboardActivity extends AutoLockActivity
        implements
        RevenuePageFragment.RevenueFragmentInteractionListener,
        TimePageFragment.TimeFragmentInteractionListener,
        ExpensePageFragment.ExpensesInteractionListener,
        SmartSectionedRecyclerViewAdapter.IAddTimeCallbackListener,PayrollRecyclerViewAdapter.IPaymentDetailsListener,
        ExpenseRecyclerViewAdapter.IExpenseCallbackListener,PayrollFragment.PayrollFragmentInteractionListener,PayrollRecyclerViewAdapter.IPreviousCallbackListener {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final String KEY_SAVED_INSTANCE_STATE__PAGE_INDEX = "int pageIndex";
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar downloadingProgressBar;
    private static final int MENU_ITEM_ID__BUSINESS_MANAGER = 2;

    public static final int DO_NOT_CHANGE_INDEX = -1;
    public static final int REVENUE_FRAGMENT_INDEX = 0;
    public static final int TIMES_FRAGMENT_INDEX = 1;
    public static final int EXPENSE_FRAGMENT_INDEX = 2;
    public static final int PAYROLL_FRAGMENT_INDEX = 3;

    public static final int REVENUE_FRAGMENT_DATA_LOADER = 0;
    public static final int TIMES_FRAGMENT_DATA_LOADER = 1;
    public static final int EXPENSE_FRAGMENT_DATA_LOADER = 2;

    public static final int PAYROLL_FRAGMENT_DATA_LOADER = 3;
    public static final int PAYROLL_SUMMARY_DATA_LOADER = 4;
    public static final int PAYROLL_TRANSACTION_DATA_LOADER = 5;

    public static final int FAB_BACKGROUND_COLOR__NORMAL = Color.TRANSPARENT;

    private DashboardActivityDataModel dataModel = new DashboardActivityDataModel();



    private FloatingActionsMenu fabMenu;
    private RelativeLayout fabHolderLayout;
//    private boolean userProfileLoaded = false;

    private ViewPager viewPager;
    private int pageIndex = REVENUE_FRAGMENT_INDEX;

    private DataLoadingDispatcher dataLoadingDispatcher;
    private MenuBusinessManagerButtonDescriptor menuBusinessManagerButtonDescriptor =
            new MenuBusinessManagerButtonDescriptor();

    private DashboardFieldsLoadCallbackDriver dashboardFieldsLoadCallbackDriver = new DashboardFieldsLoadCallbackDriver();
    private WorkOrdersLoadCallbackDriver workOrdersLoadCallbackDriver = new WorkOrdersLoadCallbackDriver();
    private ExpensesLoadCallbackDriver expensesLoadCallbackDriver = new ExpensesLoadCallbackDriver();
    private BusinessCenterLoadCallbackDriver businessCenterLoadCallbackDriver = new BusinessCenterLoadCallbackDriver();
    private payrollSummaryLoadCallbackDriver payrollSummaryLoadCallbackDriver = new payrollSummaryLoadCallbackDriver();
    private payrollTransactionsLoadCallbackDriver payrollTransactionsLoadCallbackDriver=new payrollTransactionsLoadCallbackDriver();
    private static boolean notNowFlag = false;
    public static String SYSTEM_LOCALE;
    public ActionBar actionBar;
    private int mNetworkingCount = 0;
    private IRestClient restServiceHelper;
    private DefaultRestClientResponseHandler defaultRestClientResponseHandler;
    private boolean isNonBillableAllowed;
    private int tabCount;
    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent()");
        pageIndex = ActivityIntentHelper.DashboardActivityBuilder.getPageIndex(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        SYSTEM_LOCALE = newConfig.locale.getCountry();
        Log.d("locale", SYSTEM_LOCALE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        isNonBillableAllowed=bundle.getBoolean("isNonBillableAlowed");
        SYSTEM_LOCALE = Locale.getDefault().getCountry();
        Log.d("locale", SYSTEM_LOCALE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (savedInstanceState != null) {
            pageIndex = ActivityIntentHelper.DashboardActivityBuilder.getPageIndex(savedInstanceState);
        } else {
            pageIndex = ActivityIntentHelper.DashboardActivityBuilder.getPageIndex(this);
        }
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) getApplicationControllersManager()
                        .getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        dataLoadingDispatcher = new DataLoadingDispatcher(sharedPreferencesController);

        setContentView(R.layout.mbo_activity_dashboard);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator01);
/* Initializing the progressBar and set the visibility to invisible first*/
        downloadingProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        downloadingProgressBar.setVisibility(View.INVISIBLE);
        setupToolbarLogo();
        ConfigurationController configurationController=(ConfigurationController)getApplicationControllersManager().
                getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
        String currentConfiguration = configurationController.getCurrentConfiguration().toString();
        if(currentConfiguration.equalsIgnoreCase(ConfigurationController.EnvironmentConfiguration.PRE_PROD.toString()))
        {
            tabCount=4;
        }else
        tabCount=3;

        /*if (isNonBillableAllowed)
            tabCount=4;
        else tabCount=3;*/

        setupTabs(tabCount);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        pageIndex = savedInstanceState.getInt(KEY_SAVED_INSTANCE_STATE__PAGE_INDEX, REVENUE_FRAGMENT_INDEX);
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();
        KeyboardUtil.htryToHideKeyboard(this);
        tryToShowPinDialog();
        restServiceHelper = getRestServiceHelper();
        defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
        registerForCallbacks();
        if(dataLoadingDispatcher.isFirstTimeLaunch() || dataLoadingDispatcher.isLoadingInProgress())
        {
            downloadAllData();
        }else {
            startAllLoadDataFromLocalDb();
        }
        setCurrentTabItem();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        KeyboardUtil.htryToHideKeyboard(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*if (dataLoadingDispatcher.isNeedToLoadData()) {
            hideFabMenu();
        } else {
            //showFabMenu();
        }*/

//        MenuItem businessManagerMenuItem = menu.getItem(MENU_ITEM_ID__BUSINESS_MANAGER);
        MenuItem businessManagerMenuItem = menu.findItem(R.id.menu_dashboard_action_businessManager);
        menuBusinessManagerButtonDescriptor.setMenuItem(businessManagerMenuItem);
//        if (userProfileLoaded) {
//            businessManagerMenuItem.setEnabled(true);
//        } else {
//            businessManagerMenuItem.setEnabled(false);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_dashboard_action_settings) {
            Intent intent = ActivityIntentHelper.getPasscodePreferencesActivity(DashboardActivity.this);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.menu_dashboard_action_businessManager) {
            doContactWithBusinessManager();
            return true;
        }

        if (itemId == R.id.menu_dashboard_action_logout) {
            Security.doLogoutRoutine(this.getApplication());
            //AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock01();
           if(notNowFlag)
            {
                Security.doLogoutRoutine(this.getApplication());
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock02();
            }else
            {
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock01();
            }
            //AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock01();
            String message = getString(R.string.mbo_Login_Successfully_Logged_Out_message);
            dataLoadingDispatcher.setFlagFirstTimeLaunch_logout();
            startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(this, message));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        pageIndex = viewPager.getCurrentItem();
        outState.putInt(KEY_SAVED_INSTANCE_STATE__PAGE_INDEX, pageIndex);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
        downloadingProgressBar.setVisibility(View.GONE);
        clearHttpClientQueue();
        mNetworkingCount = 0;
        final IRestClient restServiceHelper = getRestServiceHelper();
        restServiceHelper.clearCallbacks();
        //collapseFab();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoadingDispatcher.notifyNeedDataReload();
    }

    // --------------------------------------------------------------------------------

    private void setupToolbarLogo() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar. setLogo(R.drawable.dashboard_logo);
        }
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
   private  TabLayout tabLayout;
    private void setupTabs(int count) {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        DashboardFragmentPagerAdapter pagerAdapter = new DashboardFragmentPagerAdapter(getSupportFragmentManager(), DashboardActivity.this,count);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        /* if Tab count is greater than 3 then
        * make it Scrollable or else
        * make it Fixed.
        */
        if(tabCount>3) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

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

    private void set_FAB_LogTimeButtonVisiblity(boolean flag)
    {
        if(!flag)
        {
            if(fabMenu!=null)
            {
                FloatingActionButton actionLogTime = (FloatingActionButton) findViewById(R.id.action_log_time);
                actionLogTime.setVisibility(View.GONE);
            }
        }else
        {
            if(fabMenu!=null)
            {
                FloatingActionButton actionLogTime = (FloatingActionButton) findViewById(R.id.action_log_time);
                actionLogTime.setVisibility(View.VISIBLE);
            }
        }
    }
    private void setCurrentTabItem() {
        if (pageIndex != DO_NOT_CHANGE_INDEX) {
            viewPager.setCurrentItem(pageIndex);
        }
    }

    private void tryToShowPinDialog() {
        if (AppLockManager.getInstance().getCurrentAppLock().isLockModeNotSet()) {
            showInformationalPinLockDialog();
        }
    }

    private void doContactWithBusinessManager() {

        BusinessManager businessManager = Dao.loadBusinessManagerInfo(this.getApplication());
        String email = businessManager.getEmail();
        String chooserTitle = getString(R.string.mbo_settings_Support_Email_Email_Chooser_title);
        Communicator.startSendEmail(this, email, "", "", chooserTitle);
    }

    private void showInformationalPinLockDialog() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mbo_PIN_Settings_dialog_message)
                .setTitle(R.string.mbo_PIN_Settings_dialog_title);
        builder.setPositiveButton(R.string.mbo_PIN_Settings_dialog_Button_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                notNowFlag=false;
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock();
                startActivity(ActivityIntentHelper.getPasscodeManagePasswordActivity(DashboardActivity.this));

            }
        });
        builder.setNegativeButton(R.string.mbo_PIN_Settings_dialog_Button_NO, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                notNowFlag=true;
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock();

            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        AlertDialog pinDialog = builder.create();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pinDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pinDialog.show();
    }

    private void startAllLoadDataFromLocalDb() {
        forceLoadRevenueData();
        forceLoadTimesData();
        forceLoadExpenseData();
        if(tabCount>3) {
            forceLoadPayrollData();
            forceLoadPayrollSummaryData();
            forceLoadPayrollTransactionData();
        }
    }

    private void forceLoadRevenueData() {
        dataModel.initDashboardModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(REVENUE_FRAGMENT_DATA_LOADER, null, dashboardFieldsLoadCallbackDriver).forceLoad();
    }
    private void forceLoadTimesData() {
        dataModel.initWorkOrderModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(TIMES_FRAGMENT_DATA_LOADER, null, workOrdersLoadCallbackDriver).forceLoad();
    }
    private void forceLoadExpenseData() {
        dataModel.initExpensesModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(EXPENSE_FRAGMENT_DATA_LOADER, null, expensesLoadCallbackDriver).forceLoad();
    }
    private void forceLoadPayrollData() {
        dataModel.initBusinessModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(PAYROLL_FRAGMENT_DATA_LOADER, null, businessCenterLoadCallbackDriver).forceLoad();
    }

    private void forceLoadPayrollSummaryData() {
        dataModel.initPayrollModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(PAYROLL_SUMMARY_DATA_LOADER, null, payrollSummaryLoadCallbackDriver).forceLoad();
    }

    private void forceLoadPayrollTransactionData(){
        dataModel.initTransactionModel();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(PAYROLL_TRANSACTION_DATA_LOADER, null, payrollTransactionsLoadCallbackDriver).forceLoad();
    }
    private void notifyAllFragmentsDataReceived() {
        notifyRevenueDataReceived();
        notifyTimesDataReceived();
        notifyExpensesDataReceived();
        if(tabCount>3)
        notifyPayrollDataReceived();
    }

    private void notifyRevenueDataReceived() {
        DashboardFragmentPagerAdapter fragmentPagerAdapter = (DashboardFragmentPagerAdapter) viewPager.getAdapter();
        RevenuePageFragment revenuePageFragment = (RevenuePageFragment) fragmentPagerAdapter.getRegisteredFragment(REVENUE_FRAGMENT_INDEX);
        if (revenuePageFragment != null) {
            revenuePageFragment.showRevenue();
        }
    }
    private void notifyPayrollDataReceived() {
        DashboardFragmentPagerAdapter fragmentPagerAdapter = (DashboardFragmentPagerAdapter) viewPager.getAdapter();
        PayrollFragment payrollFragment = (PayrollFragment) fragmentPagerAdapter.getRegisteredFragment(PAYROLL_FRAGMENT_INDEX);
        if (payrollFragment != null) {
            payrollFragment.showPayroll();
        }
    }

    private void notifyTimesDataReceived() {
        DashboardFragmentPagerAdapter fragmentPagerAdapter = (DashboardFragmentPagerAdapter) viewPager.getAdapter();
        TimePageFragment timePageFragment = (TimePageFragment) fragmentPagerAdapter.getRegisteredFragment(TIMES_FRAGMENT_INDEX);
        if (timePageFragment != null) {
            timePageFragment.showTimes();
        }
    }

    private void notifyExpensesDataReceived() {
        DashboardFragmentPagerAdapter fragmentPagerAdapter = (DashboardFragmentPagerAdapter) viewPager.getAdapter();
        ExpensePageFragment expensePageFragment = (ExpensePageFragment) fragmentPagerAdapter.getRegisteredFragment(EXPENSE_FRAGMENT_INDEX);
        if (expensePageFragment != null) {
            expensePageFragment.onExpensesReceived();
        }
    }

    private void startLogTime() {
        List<WorkOrder> workOrders = dataModel.getWorkOrders();
        if (Converter.countAllowedWorkOrders(workOrders) == 1) {
            WorkOrder allowedWorkOrder = Converter.getFirstAllowedWorkOrder(workOrders);
            TimePeriod autoChosenTimePeriod = Converter.autoChoseTimePeriod(allowedWorkOrder);

            Log.d("timePeriod",autoChosenTimePeriod.toString());
            startActivity(ActivityIntentHelper.LogTimeActivityBuilder.getLogTimeActivity(this, allowedWorkOrder.getId(), autoChosenTimePeriod.getId(),null));
        } else {
            startActivity(ActivityIntentHelper.ChooseWorkOrderActivityBuilder.getActivityForTimeTracking(this));
        }
    }
    //New Expense Button Pressed
    private void startLogExpenses() {
        startActivityForResult(ActivityIntentHelper.ChoseLogExpenseTypeActivityBuilder.getActivity(this),2);


    }

    private static final String KEY__CHOOSE_MODE = "for LogTime or LogExpense Mode";
    private static final String KEY__EXPENSETYPE_ID = "MboExpenseTypeId";
    private static final String MODE__EXPENSE_TRACKING = "true";

    public boolean isCurrentDateUnderLimit(int position)
    {
        List<WorkOrder> workOrders = dataModel.getWorkOrders();
        if (Converter.countAllowedWorkOrders(workOrders) == 1) {
            WorkOrder allowedWorkOrder = Converter.getFirstAllowedWorkOrder(workOrders);
            return Converter.isUnderLimit(allowedWorkOrder,position);
        }else
        return false;
    }

    private void downloadAllData() {
        dataModel.initModel();
        notifyAllFragmentsDataReceived();
        downloadingProgressBar.setVisibility(View.VISIBLE);                        //making progressBar visible when data is loading from server only.
        menuBusinessManagerButtonDescriptor.updateMenuItemAccessibility(false);
        clearHttpClientQueue();
        fetchUser();

        fetchDashboardData();
        fetchExpenseTypes();
        fetchWorkOrders();
        if(tabCount>3) {
            fetchBusinessCenterData();
            fetchPayrollSummaryData();
            fetchPayrollTransactionsData();
        }
    }

    /**
     * Register for RestClient Callbacks
     */
    private void registerForCallbacks(){
        final IRestClient.Callback getDashboardsCallback = new DashboardsCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getUserProfileCallback = new UserProfileCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getWorkOrdersCallback = new WorkOrdersCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getExpenseTypesCallback = new ExpenseTypesCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getExpensesCallback = new ExpensesCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getBusinessCenterCallback = new BusinessCenterCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getPayrollSummaryCallback = new PayrollSummaryCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getPayrollTransactionsCallback=new PayrollTransactionsCallback(defaultRestClientResponseHandler);
        restServiceHelper.clearCallbacks();
        restServiceHelper.registerCallback(RestApiContract.Method.getDashboards, getDashboardsCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getUserProfile, getUserProfileCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getWorkOrdersList, getWorkOrdersCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getExpenseTypesList, getExpenseTypesCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getExpensesList, getExpensesCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getBusinessCenterList, getBusinessCenterCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getPayrollSummary, getPayrollSummaryCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getPayrollTransactions, getPayrollTransactionsCallback);
    }

    /**
     * Get Dashboard Data from Server
     */
    private void fetchDashboardData(){
        dataModel.initDashboardModel();
        notifyAllFragmentsDataReceived();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getDashboardsList(this);
        mNetworkingCount++;
    }

    /**
     * Get User Profile from Server
     */
    private void fetchUser(){
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getUserProfile(this);
        mNetworkingCount++;
    }

    /**
     * Get WorkOrders from Server
     */
    private void fetchWorkOrders() {
        dataModel.initWorkOrderModel();
        notifyAllFragmentsDataReceived();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getWorkOrdersList(this);
        mNetworkingCount++;
    }

    /**
     * Get ExpenseTypes from Server
     */
    private void fetchExpenseTypes(){
        dataModel.initExpensesModel();
        notifyAllFragmentsDataReceived();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getExpenseTypesList(this);
        mNetworkingCount++;
    }

    /**
     * GetExpenses from Server
     */
    private void fetchExpenses() {
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getExpensesList(this);
        mNetworkingCount++;
    }

    /**
     * Get BusinessCenterData  from Server
     */
    private void fetchBusinessCenterData(){
        dataModel.initBusinessModel();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getBusinessCenterList(this);
        mNetworkingCount++;
    }
    private void fetchPayrollSummaryData(){
        dataModel.initPayrollModel();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getPayrollSummaryList(this);
        mNetworkingCount++;
    }

    private void fetchPayrollTransactionsData(){
        dataModel.initTransactionModel();
        dataLoadingDispatcher.notifyNeedDataReload();
        dataLoadingDispatcher.notifyDadaLoadingStarted();
        restServiceHelper.getPayrollTransactionsList(this);
        mNetworkingCount++;
    }

    private void doDataLoadingFromServer_Step_4() {
        //showFabMenu();
        if(mNetworkingCount <= 0) {
            dataLoadingDispatcher.setFlagFirstTimeLaunch();
            dataLoadingDispatcher.notifyDataLoaded();
            downloadingProgressBar.setVisibility(View.GONE); //making progressBar in-visible when data loading is done for all three tabs.
        }
    }

    private void doOnDataLoadingFromServer_FailedRoutine() {
        downloadingProgressBar.setVisibility(View.INVISIBLE);
        dataLoadingDispatcher.notifyDataLoadingFailed();
        dataModel.onAllDataLoadingFailed();
        //notifyAllFragmentsDataReceived();
    }

    public void showFabMenu() {
        if (fabMenu != null) {
            fabMenu.setVisibility(View.VISIBLE);
        }
    }

    public void hideFabMenu() {
        if (fabMenu != null) {
            fabMenu.setVisibility(View.GONE);
        }
    }

    public void collapseFab() {
        if (fabMenu.isExpanded()) {
            fabMenu.collapse();
        }
    }

    public void setupFabMenu() {

        fabHolderLayout = (RelativeLayout) findViewById(R.id.FAB_holder_layout);
        fabMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        FloatingActionsMenu.OnFloatingActionsMenuUpdateListener listener = new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                int FAB_BACKGROUND_COLOR__DARK = getResources().getColor(R.color.mbo_color_black_70);
                animateFabMenuBackground(Color.TRANSPARENT, FAB_BACKGROUND_COLOR__DARK, true);
            }

            @Override
            public void onMenuCollapsed() {
                int FAB_BACKGROUND_COLOR__DARK = getResources().getColor(R.color.mbo_color_black_70);
                animateFabMenuBackground(FAB_BACKGROUND_COLOR__DARK, FAB_BACKGROUND_COLOR__NORMAL, false);
            }
        };
        fabMenu.setOnFloatingActionsMenuUpdateListener(listener);

        FloatingActionButton actionLogTime = (FloatingActionButton) findViewById(R.id.action_log_time);
        actionLogTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogTime();
            }
        });

        FloatingActionButton actionLogExpenses = (FloatingActionButton) findViewById(R.id.action_log_expense);
        actionLogExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogExpenses();
            }
        });
    }

    private void animateFabMenuBackground(Integer colorFrom, Integer colorTo, boolean clickableFlag) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                fabHolderLayout.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        fabHolderLayout.setClickable(clickableFlag);
        if(clickableFlag) {
            fabHolderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fabMenu.isExpanded()) {
                        fabMenu.collapse();

                    }

                }
            });
        }

        colorAnimation.start();
    }

    private void clearHttpClientQueue() {
        IHttpRequestProcessor httpRequestProcessor = (IHttpRequestProcessor) getApplicationControllersManager().getController(Controllers.CONTROLLER__REST_PROCESSOR);
        httpRequestProcessor.cancelAllRequests();
    }

    // ================================================================================
    //
    // Fragments Callback interfaces implementation
    //
    // ================================================================================

    @Override
    public List<DashboardField> getDashboardData() {
        return dataModel.getDashboardData();
    }

    @Override
    public void onDashboardSelected() {
    }

    @Override
    public void onRefreshData() {
        if( ! dataLoadingDispatcher.isLoadingInProgress()) {
            downloadingProgressBar.setVisibility(View.VISIBLE);
            if (pageIndex == TIMES_FRAGMENT_INDEX){
                fetchWorkOrders();
            }else if(pageIndex == EXPENSE_FRAGMENT_INDEX){
                fetchExpenseTypes();
            }else if(pageIndex == REVENUE_FRAGMENT_INDEX){
                fetchDashboardData();
            }else if (pageIndex==PAYROLL_FRAGMENT_INDEX)
            {
                fetchBusinessCenterData();
                fetchPayrollSummaryData();
                fetchPayrollTransactionsData();
            }
        }
    }

    // --------------------------------------------------------------------------------

    @Override
    public List<WorkOrder> getTimeData() {
        return dataModel.getWorkOrders();
    }

    @Override
    public void onWorkOrderSelected(String workOrderId, String timePeriodId,boolean isSubmittable) {

        Log.d("isSubmittable"," "+isSubmittable);
        startActivityForResult(ActivityIntentHelper.WeeklyTimeSheetActivityBuilder.
                getWeeklyTimeSheetActivity(this, workOrderId, timePeriodId, isSubmittable), 1);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent intent) {

        if (resultCode == 1) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Your timesheet has been submitted.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if (resultCode == 2)
        {
            Bundle bundle=intent.getExtras();
            String data_to_display=bundle.getString("displayResult");
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout,data_to_display, Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }


    // --------------------------------------------------------------------------------

    @Override
    public List<ExpenseTimesheetItem> getExpenses() {
        return dataModel.getExpenses();
    }

    @Override
    public void onExpenseSelected(String mboExpenseId) {
        Bundle bundle = new Bundle();
        bundle.putInt("value", 1);
        Intent intent = ActivityIntentHelper.LogExpenseActivityBuilder.getActivity(this, mboExpenseId);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void callBackLogTime(String workOrderId, String timePeriodId,Date startDate,int position) {
        if(isCurrentDateUnderLimit(position))
        startLogTime();
        else
        startActivity(ActivityIntentHelper.LogTimeActivityBuilder.getLogTimeActivity(this,workOrderId,timePeriodId,startDate));
    }

    @Override
    public void callbackPrevious() {
        /*PreviousPayment previousPayment=dataModel.getPayrollSummaryList().get(0).getLast_payroll();
        if(previousPayment!=null)
        {
            Bundle bundle=new Bundle();
            //bundle.putSerializable("PARAMETERS AS SERIALIZABLE",previousPayment);
            bundle.putSerializable("summaryList", (Serializable) dataModel.getPayrollTransactionsList());
            Intent intent=ActivityIntentHelper.PayrollActivityBuilder.getActivity(DashboardActivity.this);
            intent.putExtras(bundle);
            startActivity(intent);
        }*/

        Bundle bundle=new Bundle();
        //bundle.putSerializable("PARAMETERS AS SERIALIZABLE",previousPayment);
        bundle.putSerializable("summaryList", (Serializable) dataModel.getPayrollTransactionsList());
        Intent intent=ActivityIntentHelper.PayrollActivityBuilder.getActivity(DashboardActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);
        /*Bundle bundle=new Bundle();
        bundle.putSerializable("summaryList", (Serializable) dataModel.getPayrollTransactionsList());
        bundle.putInt("position",0);
        Intent intent= ActivityIntentHelper.ChoosePreviousPaymentsActivityBuilder.getActivity(DashboardActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);*/

    }

    @Override
    public void callPaymentDetails() {

        if(dataModel.getPayrollSummaryList()!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("summaryList", (Serializable) dataModel.getPayrollSummaryList());
            Intent intent=ActivityIntentHelper.Payroll_PaymentDetails_ActivityBuilder.getActivity(DashboardActivity.this);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    // ================================================================================

    class UserProfileCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public UserProfileCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok : {
                    UserProfile userProfile=(UserProfile)response.getResponseEntity();
                    isNonBillableAlowed=userProfile.getNonbillableAllowed();
                    menuBusinessManagerButtonDescriptor.updateMenuItemAccessibility(true);
                    break;
                }
                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    private boolean isNonBillableAlowed;
    class DashboardsCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public DashboardsCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok : {
                    /*Dashboard dashboard = (Dashboard) response.getResponseEntity();
                    dataModel.setDashboardData(dashboard.getDashboardData());*/
                    //notifyRevenueDataReceived();
                    forceLoadRevenueData();
                    doDataLoadingFromServer_Step_4();
                    break;
                }

                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class BusinessCenterCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public BusinessCenterCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok: {
                            forceLoadPayrollData();
                    doDataLoadingFromServer_Step_4();
                    break;
                }

                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }
    class PayrollSummaryCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public PayrollSummaryCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok: {
                    forceLoadPayrollSummaryData();
                    doDataLoadingFromServer_Step_4();
                    break;
                }

                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }
    class PayrollTransactionsCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public PayrollTransactionsCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok: {
                    forceLoadPayrollTransactionData();
                    doDataLoadingFromServer_Step_4();
                    break;
                }

                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class ExpenseTypesCallback implements IRestClient.Callback {

        private DefaultRestClientResponseHandler defaultHandler;
        public ExpenseTypesCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok : {
                    fetchExpenses();
                    break;
                }
                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class WorkOrdersCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public WorkOrdersCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok : {
                    /*WorkOrder[] workOrders = (WorkOrder[]) response.getResponseEntity();
                    dataModel.setWorkOrders(workOrders);
                    notifyTimesDataReceived();*/
                    //doDataLoadingFromServer_Step_3();
                    forceLoadTimesData();
                    doDataLoadingFromServer_Step_4();
                    //dataLoadingDispatcher.notifyDataLoaded();
                    break;
                }

                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class ExpensesCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public ExpensesCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            mNetworkingCount--;
            switch (response.getClientResult()) {
                case Ok : {
//                    dataModel.setExpenses(Dao.loadExpenseTimesheet(DashboardActivity.this.getApplication()));
                    //dataLoadingDispatcher.notifyDataLoaded();
                    forceLoadExpenseData();
                    //startAllLoadDataFromLocalDb();
                    doDataLoadingFromServer_Step_4();
                    break;
                }
                default: {
                    doOnDataLoadingFromServer_FailedRoutine();
                    defaultHandler.onComplete(response);
                }
            }
        }
    }
    //================================================================================

    private class DashboardFieldsLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<DashboardField>> {

        @Override
        public Loader<List<DashboardField>> onCreateLoader(int id, Bundle args) {
            DashboardFieldsListDataExtractor dataExtractor = new DashboardFieldsListDataExtractor();
            DbAsyncLoader<List<DashboardField>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<DashboardField>> loader, List<DashboardField> data) {
            dataModel.setDashboardData(data);
            notifyRevenueDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<DashboardField>> loader) {
        }
    }
    private class WorkOrdersLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<WorkOrder>> {
        @Override
        public Loader<List<WorkOrder>> onCreateLoader(int id, Bundle args) {
            WorkOrdersListDataExtractor dataExtractor = new WorkOrdersListDataExtractor();
            DbAsyncLoader<List<WorkOrder>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<WorkOrder>> loader, List<WorkOrder> workOrders) {
            dataModel.setWorkOrders(workOrders);
            notifyTimesDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<WorkOrder>> loader) {
        }
    }

    private class ExpensesLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<ExpenseTimesheetItem>> {
        @Override
        public Loader<List<ExpenseTimesheetItem>> onCreateLoader(int id, Bundle args) {
            ExpensesListDataExtractor dataExtractor = new ExpensesListDataExtractor();
            DbAsyncLoader<List<ExpenseTimesheetItem>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<ExpenseTimesheetItem>> loader, List<ExpenseTimesheetItem> data) {
            dataModel.setExpenses(data);
            notifyExpensesDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<ExpenseTimesheetItem>> loader) {
        }
    }
    //--------------------------------------------------------------------------------

    private class DashboardFieldsListDataExtractor implements IDataSource<List<DashboardField>> {
        @Override
        public List<DashboardField> loadData(Application application, Bundle params) {
            return Dao.loadDashboardFields(DashboardActivity.this.getApplication());
        }
    }
    private class BusinessCenterListDataExtractor implements IDataSource<List<BusinessCenter>> {
        @Override
        public List<BusinessCenter> loadData(Application application, Bundle params) {
            return Dao.loadBusinessCenterFields(DashboardActivity.this.getApplication());
        }
    }

    private class PayrollSummaryListDataExtractor implements IDataSource<List<PayrollSummary>> {
        @Override
        public List<PayrollSummary> loadData(Application application, Bundle params) {
            return Dao.loadPayrollSummaryFields(DashboardActivity.this.getApplication());
        }
    }

    private class PayrollTransactionsListDataExtractor implements IDataSource<List<PayrollTransactions>> {
        @Override
        public List<PayrollTransactions> loadData(Application application, Bundle params) {
            return Dao.loadPayrollTransactionsFields(DashboardActivity.this.getApplication());
        }
    }

    private class WorkOrdersListDataExtractor implements IDataSource<List<WorkOrder>> {
        @Override
        public List<WorkOrder> loadData(Application application, Bundle params) {
            return Dao.loadWorkOrdersFullTree(application);
        }
    }

    private class ExpensesListDataExtractor implements IDataSource<List<ExpenseTimesheetItem>> {
        @Override
        public List<ExpenseTimesheetItem> loadData(Application application, Bundle params) {
            return  Dao.loadExpenseTimesheet(DashboardActivity.this.getApplication());
        }
    }

    private class MenuBusinessManagerButtonDescriptor {
        private MenuItem menuItem;
        private boolean buttonEnableState;

        public MenuBusinessManagerButtonDescriptor() {
            this.buttonEnableState = false;
        }

        public void setMenuItem(MenuItem menuItemSave) {
            this.menuItem = menuItemSave;
            this.menuItem.setEnabled(this.buttonEnableState);
        }

        public void updateMenuItemAccessibility(boolean accessibility) {
            this.buttonEnableState = accessibility;
            if (this.menuItem != null) {
                this.menuItem.setEnabled(this.buttonEnableState);
            }
        }
    }

    private class BusinessCenterLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<BusinessCenter>>
    {
        @Override
        public Loader<List<BusinessCenter>> onCreateLoader(int id, Bundle args) {
            BusinessCenterListDataExtractor dataExtractor = new BusinessCenterListDataExtractor();
            DbAsyncLoader<List<BusinessCenter>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<BusinessCenter>> loader, List<BusinessCenter> data) {
            dataModel.setBusinessData(data);
            notifyPayrollDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<BusinessCenter>> loader) {
        }
    }
    private class payrollSummaryLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<PayrollSummary>>
    {
        @Override
        public Loader<List<PayrollSummary>> onCreateLoader(int id, Bundle args) {
            PayrollSummaryListDataExtractor dataExtractor = new PayrollSummaryListDataExtractor();
            DbAsyncLoader<List<PayrollSummary>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<PayrollSummary>> loader, List<PayrollSummary> data) {
            dataModel.setPayrollSummaryData(data);
            notifyPayrollDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<PayrollSummary>> loader) {
        }

    }

    private class payrollTransactionsLoadCallbackDriver implements LoaderManager.LoaderCallbacks<List<PayrollTransactions>>
    {
        @Override
        public Loader<List<PayrollTransactions>> onCreateLoader(int id, Bundle args) {
            PayrollTransactionsListDataExtractor dataExtractor = new PayrollTransactionsListDataExtractor();
            DbAsyncLoader<List<PayrollTransactions>> loader = new DbAsyncLoader<>(DashboardActivity.this.getApplication(), args);
            loader.setDataSource(dataExtractor);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<PayrollTransactions>> loader, List<PayrollTransactions> data) {
            dataModel.setPayrollTransactionsData(data);
            notifyPayrollDataReceived();
        }

        @Override
        public void onLoaderReset(Loader<List<PayrollTransactions>> loader) {
        }
    }


    public void onBackPressed()
    {
        if(fabMenu!=null) {
            if (fabMenu.isExpanded()) {
                fabMenu.collapse();
            }
        }else
        super.onBackPressed();

    }

    public boolean onKeyDown(int keyCode,KeyEvent keyEvent)
    {
        super.onKeyDown(keyCode, keyEvent);

            if(fabMenu!=null){
                if (fabMenu.isExpanded()) {
                    fabMenu.collapse();
                }
                return false;
            }
        else
        return true;
    }

    @Override
    public void callbackExpense() {
        startLogExpenses();
    }

    @Override
    public List<BusinessCenter> getBusinessCenterData() {
        return dataModel.getBusinessCenterList();
    }

    @Override
    public List<PayrollSummary> getPayrollSummaryData() {
        return dataModel.getPayrollSummaryList();
    }


    public List<PayrollTransactions> getPayrollTransactionData(){
        return dataModel.getPayrollTransactionsList();
    }


}

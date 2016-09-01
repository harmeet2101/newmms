package com.mbopartners.mbomobile.ui.activity.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mbopartners.mbomobile.ui.activity.about.AboutActivity;
import com.mbopartners.mbomobile.ui.activity.choose_expense_type.ChooseExpenseTypeActivity;
import com.mbopartners.mbomobile.ui.activity.choose_workorder.ChooseWorkOrderActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.DashboardActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PayrollWithHoldings;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PreviousPayments.PreviousPaymentsActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.PaymentDetailsActivity;
import com.mbopartners.mbomobile.ui.activity.joinus.JoinUsActivity;
import com.mbopartners.mbomobile.ui.activity.logexpense.LogExpenseActivity;
import com.mbopartners.mbomobile.ui.activity.login.LoginActivity;
import com.mbopartners.mbomobile.ui.activity.logtime.LogTimeActivity;
import com.mbopartners.mbomobile.ui.activity.passcodelock.PasscodeManagePasswordActivity;
import com.mbopartners.mbomobile.ui.activity.passcodelock.PasscodeUnlockActivity;
import com.mbopartners.mbomobile.ui.activity.settings.SettingsActivity;
import com.mbopartners.mbomobile.ui.activity.splash.SplashActivity;
import com.mbopartners.mbomobile.ui.activity.weekly_timesheet.WeeklyTimeSheetActivity;

import java.util.Date;


public class ActivityIntentHelper {
    private static final String KEY__PARAMETERS_AS_SERIALIZABLE = "PARAMETERS AS SERIALIZABLE";
    private static final String KEY__WORK_ORDER_ID = "MboWorkOrderId";
    private static final String KEY__TIME_PERIODID = "MboTimePeriodId";
    private static final String KEY__CHOOSE_MODE = "For LogTime or LogExpense Mode";
    private static final String KEY__EXPENSETYPE_ID = "MboExpenseTypeId";
    private static final String KEY__EXPENSE_ID = "MboExpenseId";


    private static Intent getNewStandardIntent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static Bundle getBundle(Activity activity) {
        Intent intent = activity.getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    public static void cloneStringKeyValue(Bundle src, Bundle dest, String key){
        String value = src.getString(key);
        if (value == null) {
            dest.remove(KEY__EXPENSE_ID);
        } else {
            dest.putString(KEY__EXPENSE_ID, value);
        }
    }

    // Splash Activity
    public static Intent getSplashActivity(Context context) {
        return getNewStandardIntent(context, SplashActivity.class);
    }

    public static Intent getPasscodeUnlockActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), PasscodeUnlockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    // PIN Preferences Activity
    public static Intent getPasscodePreferencesActivity(Context context) {
        return getNewStandardIntent(context, SettingsActivity.class);
    }

    public static Intent getPasscodeManagePasswordActivity(Context context, int mode) {
        Intent intent = getNewStandardIntent(context, PasscodeManagePasswordActivity.class);
        intent.putExtra("type", mode);
        return intent;
    }

    public static Intent getPasscodeManagePasswordActivity(Context context) {
        return getPasscodeManagePasswordActivity(context, SettingsActivity.ENABLE_PASSLOCK);
    }

    // Login Activity
    public static class LoginActivityBuilder {
        private static final String KEY__STARTING_MESSAGE = "Key for starting Toast message";

        public static Intent getLoginActivity(Context context) {
            Intent intent = getNewStandardIntent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return intent;
        }

        public static Intent getLoginActivity(Context context, String message) {
            Intent intent = getLoginActivity(context);
            Bundle bundle = new Bundle();
            bundle.putString(KEY__STARTING_MESSAGE, message);
            intent.putExtras(bundle);
            return intent;
        }

        public static String getStartingMessage(Activity activity) {
            String message = null;
            if (activity.getClass() == LoginActivity.class) {
                Intent intent = activity.getIntent();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    message = bundle.getString(KEY__STARTING_MESSAGE);
                }
            }
            return message;
        }
    }

    public static Intent getJoinUsActivity(Context context) {
        return getNewStandardIntent(context, JoinUsActivity.class);
    }

    public static Intent getAboutActivity(Context context) {
        return getNewStandardIntent(context, AboutActivity.class);
    }

    // DashBoard Activity
    public static class DashboardActivityBuilder {
        public static final String ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER = "Active page number";

        public static Intent getDashboardActivityRevenuePage(Context context) {
            return getDashboardActivity(context, DashboardActivity.REVENUE_FRAGMENT_INDEX);
        }

        public static Intent getDashboardActivityRevenuePage(Context context,Bundle bundle) {
            return getDashboardActivity(context, DashboardActivity.REVENUE_FRAGMENT_INDEX,bundle);
        }

        public static Intent getDashboardActivityTimePage(Context context) {
            return getDashboardActivity(context, DashboardActivity.TIMES_FRAGMENT_INDEX);
        }

        public static Intent getDashboardActivityExpensePage(Context context) {
            return getDashboardActivity(context, DashboardActivity.EXPENSE_FRAGMENT_INDEX);
        }
        public static Intent getDashboardActivityExpensePage(Context context, Bundle bundle){
            return getDashboardActivity(context, DashboardActivity.EXPENSE_FRAGMENT_INDEX, bundle);

        }

        public static int getPageIndex(Activity activity) {
            int pageIndex = DashboardActivity.DO_NOT_CHANGE_INDEX;
            if (activity.getClass() == DashboardActivity.class) {
                Intent intent = activity.getIntent();
                pageIndex = getPageIndex(intent);
            }
            return pageIndex;
        }

        public static int getPageIndex(Intent intent) {
            int pageIndex = DashboardActivity.DO_NOT_CHANGE_INDEX;
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                pageIndex = bundle.getInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER);
            }
            return pageIndex;
        }

        public static int getPageIndex(Bundle bundle) {
            return bundle.getInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER, DashboardActivity.DO_NOT_CHANGE_INDEX);
        }

        public static Intent getDashboardActivity(Context context, int pageId) {
            Bundle bundle = new Bundle();
            bundle.putInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER, pageId);
            Intent intent = getNewStandardIntent(context, DashboardActivity.class);
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getDashboardActivity(Context context, int pageId,Bundle bundle) {

            bundle.putInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER, pageId);

            Intent intent = getNewStandardIntent(context, DashboardActivity.class);
            intent.putExtras(bundle);
            return intent;
        }
    }

    // Weekly TimeSheet Activity
    public static class WeeklyTimeSheetActivityBuilder {

        public static Intent getWeeklyTimeSheetActivity(Context context, String workOrderId, String timePeriodId,boolean isSubmittable) {
            Intent intent = getNewStandardIntent(context, WeeklyTimeSheetActivity.class);
            Bundle bundle = new Bundle();
            TimePeriodParameters parameters = new TimePeriodParameters(workOrderId, timePeriodId, null);
            //Log.d("params",workOrderId+" ; "+timePeriodId+" submittable "+isSubmittable);
            bundle.putSerializable(KEY__PARAMETERS_AS_SERIALIZABLE, parameters);
            bundle.putBoolean("isSubmittable", isSubmittable);
            intent.putExtras(bundle);
            return intent;
        }

        public static TimePeriodParameters getStartingParameters(Activity activity) {
            TimePeriodParameters parameters = null;
            if (activity.getClass() == WeeklyTimeSheetActivity.class) {
                Intent intent = activity.getIntent();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    parameters = (TimePeriodParameters) bundle.getSerializable(KEY__PARAMETERS_AS_SERIALIZABLE);
                }
            }
            return parameters;
        }
    }

    public static class LogTimeActivityBuilder {

        public static Intent getLogTimeActivity(Context context, String workOrderId) {
            Intent intent = getNewStandardIntent(context, LogTimeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY__PARAMETERS_AS_SERIALIZABLE, new TimePeriodParameters(workOrderId, null, null));
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getLogTimeActivity(Context context, String workOrderId, String timePeriodId) {
            Intent intent = getNewStandardIntent(context, LogTimeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY__PARAMETERS_AS_SERIALIZABLE, new TimePeriodParameters(workOrderId, timePeriodId, null));
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getLogTimeActivity(Context context, String workOrderId, String timePeriodId, Date chosenDate) {
            Intent intent = getNewStandardIntent(context, LogTimeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY__PARAMETERS_AS_SERIALIZABLE, new TimePeriodParameters(workOrderId, timePeriodId, chosenDate));
            intent.putExtras(bundle);
            return intent;
        }

        public static TimePeriodParameters getStartingParameters(Activity activity) {
            TimePeriodParameters parameters = null;
            if (activity.getClass() == LogTimeActivity.class) {
                Intent intent = activity.getIntent();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    parameters = (TimePeriodParameters) bundle.getSerializable(KEY__PARAMETERS_AS_SERIALIZABLE);
                }
            }
            return parameters;
        }
    }

    public static class ChooseWorkOrderActivityBuilder {
        private static final boolean MODE__TIME_TRACKING = false;
        private static final boolean MODE__EXPENSE_TRACKING = true;

        public static Intent getActivityForTimeTracking(Context context) {
            Intent intent = getNewStandardIntent(context, ChooseWorkOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, MODE__TIME_TRACKING);
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getActivityForExpenseTracking(Context context, String mboExpenseTypeId) {
            Intent intent = getNewStandardIntent(context, ChooseWorkOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, MODE__EXPENSE_TRACKING);
            bundle.putString(KEY__EXPENSETYPE_ID, mboExpenseTypeId);
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getActivityForExpenseTrackingWithItems(Context context, String mboExpenseTypeId,Bundle bundle01) {
            Intent intent = getNewStandardIntent(context, ChooseWorkOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, MODE__EXPENSE_TRACKING);
            bundle.putString(KEY__EXPENSETYPE_ID, mboExpenseTypeId);
            intent.putExtras(bundle);
            return intent;
        }

        public static boolean isRunForTimeTracking(Activity activity) {
            //noinspection PointlessBooleanExpression
            return getRunMode(activity) == MODE__TIME_TRACKING;
        }

        public static boolean isRunForExpenseTracking(Activity activity) {
            //noinspection PointlessBooleanExpression
            return getRunMode(activity) == MODE__EXPENSE_TRACKING;
        }



        private static Intent getActivity(Context context, boolean mode) {
            Intent intent = getNewStandardIntent(context, ChooseWorkOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY__CHOOSE_MODE, mode);
            intent.putExtras(bundle);
            return intent;
        }

        private static boolean getRunMode(Activity activity){
            boolean runMode = MODE__TIME_TRACKING;
            if (activity.getClass() == ChooseWorkOrderActivity.class) {
                Intent intent = activity.getIntent();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    runMode = bundle.getBoolean(KEY__CHOOSE_MODE, MODE__TIME_TRACKING);
                }
            }
            return runMode;
        }
    }

    public static class ChoseLogExpenseTypeActivityBuilder {
        public static Intent getActivity(Context context) {
            Intent intent = getNewStandardIntent(context, ChooseExpenseTypeActivity.class);
            return intent;
        }
    }
    public static class ChoosePreviousPaymentsActivityBuilder {
        public static final String ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER = "Active page number";
        public static Intent getActivity(Context context) {
            Intent intent = getNewStandardIntent(context, PayrollWithHoldings.class);
            return intent;
        }


        public static int getPageIndex(Activity activity) {
            int pageIndex = PayrollWithHoldings.DO_NOT_CHANGE_INDEX;
            if (activity.getClass() == PayrollWithHoldings.class) {
                Intent intent = activity.getIntent();
                pageIndex = getPageIndex(intent);
            }
            return pageIndex;
        }

        public static int getPageIndex(Intent intent) {
            int pageIndex = PayrollWithHoldings.DO_NOT_CHANGE_INDEX;
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                pageIndex = bundle.getInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER);
            }
            return pageIndex;
        }

        public static int getPageIndex(Bundle bundle) {
            return bundle.getInt(ACTIVITY_STATE_KEY__ACTIVE_PAGE_NUMBER, PayrollWithHoldings.DO_NOT_CHANGE_INDEX);
        }
    }

    public static class LogExpenseActivityBuilder {
        public static Bundle createParamForExistingExpense(String mboExpenseId) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY__EXPENSE_ID, mboExpenseId);
            return bundle;
        }

        public static void cloneParams(Bundle src, Bundle dest) {
            cloneStringKeyValue(src, dest, KEY__EXPENSE_ID);
            cloneStringKeyValue(src, dest, KEY__WORK_ORDER_ID);
            cloneStringKeyValue(src, dest, KEY__EXPENSETYPE_ID);
        }

        public static Intent getActivity(Context context, String mboExpenseId) {
            Intent intent = getNewStandardIntent(context, LogExpenseActivity.class);
            Bundle bundle = createParamForExistingExpense(mboExpenseId);
            intent.putExtras(bundle);
            return intent;
        }

        public static Intent getActivity(Context context, Bundle bundle, String mboWorkOrderId) {
            Intent intent = getNewStandardIntent(context, LogExpenseActivity.class);
            bundle.putString(KEY__WORK_ORDER_ID, mboWorkOrderId);
            intent.putExtras(bundle);
            return intent;
        }

        public static String getExpenseId(Activity activity) {
            String expenseId = null;
            if (activity.getClass() == LogExpenseActivity.class) {
                Bundle bundle = getBundle(activity);
                getExpenseId(bundle);
            }
            return expenseId;
        }
        public static String getExpenseId(Bundle bundle) {
            String expenseId = null;
            if (bundle != null) {
                expenseId = bundle.getString(KEY__EXPENSE_ID);
            }
            return expenseId;
        }

        public static String getExpenseTypeId(Activity activity) {
            String expenseTypeId = null;
            if (activity.getClass() == LogExpenseActivity.class) {
                Bundle bundle = getBundle(activity);
                expenseTypeId = getExpenseTypeId(bundle);
            }
            return expenseTypeId;
        }

        public static String getExpenseTypeId(Bundle bundle) {
            String expenseTypeId = null;
            if (bundle != null) {
                expenseTypeId = bundle.getString(KEY__EXPENSETYPE_ID);
            }
            return expenseTypeId;
        }

        public static String getWorkOrderId(Activity activity){
            String workOrderId = null;
            if (activity.getClass() == LogExpenseActivity.class) {
                Bundle bundle = getBundle(activity);
                workOrderId = getWorkOrderId(bundle);
            }
            return workOrderId;
        }

        public static String getWorkOrderId(Bundle bundle) {
            String workOrderId = null;
            if (bundle != null) {
                workOrderId = bundle.getString(KEY__WORK_ORDER_ID);
            }
            return workOrderId;
        }

    }

    public static class PayrollActivityBuilder{

        public static Intent getActivity(Context context) {
            Intent intent = getNewStandardIntent(context, PreviousPaymentsActivity.class);
            return intent;
        }
    }


    public static class Payroll_PaymentDetails_ActivityBuilder{
        public static Intent getActivity(Context context) {
            Intent intent = getNewStandardIntent(context, PaymentDetailsActivity.class);
            return intent;
        }
    }


}

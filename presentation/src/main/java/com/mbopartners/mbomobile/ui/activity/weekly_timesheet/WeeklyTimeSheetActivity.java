package com.mbopartners.mbomobile.ui.activity.weekly_timesheet;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.choose_expense_type.ChooseExpenseTypeActivity;
import com.mbopartners.mbomobile.ui.activity.choose_workorder.ChooseWorkOrderAsyncLoader;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.helper.TimeLogDescriptor;
import com.mbopartners.mbomobile.ui.activity.helper.TimePeriodParameters;
import com.mbopartners.mbomobile.ui.activity.logtime.LogTimeActivity;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.dialog.ApiErrorDialog;
import com.mbopartners.mbomobile.ui.model.Dao;
import com.mbopartners.mbomobile.ui.model.WeeklyTimeSheetItem;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;
import com.mbopartners.mbomobile.ui.util.DefaultRestClientResponseHandler;

import java.util.Date;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class WeeklyTimeSheetActivity extends AutoLockActivity implements WeeklyTimeSheetActivityFragment.OnFragmentInteractionListener,
		LoaderManager.LoaderCallbacks<List<WorkOrder>>,WeeklyTimeSheetActivityFragment.ISubmittableListener{

	//private FabDescriptor mboFabDescriptor = new FabDescriptor();
	private boolean submitButtonActive = false;
	private TimeLogDescriptor timeLogDescriptor;
	private ProgressDialog ringProgressDialog;
	private static final int LOADER__WORK_ORDERS__ID = 1;
	private CoordinatorLayout coordinatorLayout;
	private List<WorkOrder> workOrdersList = null;
	private boolean isSubmittable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekly_time_sheet);
		coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator02);
		setupUpAppTabButtonIfPossible();
		Bundle loaderParam;
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null)
		{
			isSubmittable=bundle.getBoolean("isSubmittable");
		}
		loaderParam = ChooseWorkOrderAsyncLoader.getParamForTimeTracking();
		getSupportLoaderManager().initLoader(LOADER__WORK_ORDERS__ID, loaderParam,this);
		//setupFabMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_weekly_time_sheet, menu);

		/*mboFabDescriptor.setFab((FloatingActionButton) findViewById(R.id.mboFAB));
		if (timeLogDescriptor.getTimeEntryAllowed()) {
			mboFabDescriptor.fab.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startLogging();
				}
			});
		} else {
			mboFabDescriptor.updateFabVisibility(false);
		}*/
		return true;
	}

	@Override
	public void onBackPressed() {
		/*if(fabMenu.isExpanded())
		{
			fabMenu.collapse();
		}else*/
			super.onBackPressed();
		/*startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivity(WeeklyTimeSheetActivity
				.this, DashboardActivity.DO_NOT_CHANGE_INDEX));*/
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		/*if (id == R.id.action_submit) {
			showSubmitDialog();
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}

	@Override
	public TimeLogDescriptor getTimeLogDescriptor() {
		return loadData();
	}

	@Override
	public void onDaySelected(Date date) {
		TimeLogDescriptor timeLogDescriptor = getTimeLogDescriptor();
		timeLogDescriptor.setChosenDate(date);

		startActivity(ActivityIntentHelper.LogTimeActivityBuilder.getLogTimeActivity(this, timeLogDescriptor.getWorkOrderId(), timeLogDescriptor.getTimePeriod().getId(), date));
	}

	private TimeLogDescriptor loadData() {
		//mboFabDescriptor.updateFabVisibility(false);
		List<WorkOrder> allWorkOrders = Dao.loadWorkOrdersFullTree(this.getApplication());
		TimePeriodParameters weekParameters = ActivityIntentHelper.WeeklyTimeSheetActivityBuilder.getStartingParameters(this);

		timeLogDescriptor = new TimeLogDescriptor(allWorkOrders, weekParameters.mboWorkOrderId, weekParameters.mboTimePeriodId);
		onDataLoaded(timeLogDescriptor);
		return timeLogDescriptor;
	}

	private void onDataLoaded(TimeLogDescriptor timeLogDescriptor) {
		submitButtonActive = timeLogDescriptor.getTimePeriod().getSubmittable();
		showFabIfExistEditableDays(timeLogDescriptor);
	}

	private void showSubmitDialog() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.mbo_Submit_TimeSheet_dialog_title);
		builder.setMessage(R.string.mbo_Submit_TimeSheet_dialog_message);
		builder.setPositiveButton(R.string.mbo_Submit_TimeSheet_dialog_Button_OK, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				doSubmit(timeLogDescriptor.getWorkOrderId(), timeLogDescriptor.getTimePeriod().getId());
			}
		});
		builder.setNegativeButton(R.string.mbo_Submit_TimeSheet_dialog_Button_NO, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
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

	public void showFabIfExistEditableDays(TimeLogDescriptor timeLogDescriptor) {
		List<WeeklyTimeSheetItem> weeklyTimeSheetItems =
				Converter.timeLogDescriptor_2_WeeklyTimeSheet(timeLogDescriptor);
		boolean fabNeedToShow = false;
		for (WeeklyTimeSheetItem day : weeklyTimeSheetItems){
			boolean dayEditable = timeLogDescriptor.getTimeEntryAllowed() && day.isDayEditable() && day.isParentTmePeriodEditable();
			fabNeedToShow = fabNeedToShow || dayEditable;
		}
		/*if (fabNeedToShow) {
			mboFabDescriptor.updateFabVisibility(true);
		} else {
			mboFabDescriptor.updateFabVisibility(false);
		}*/
	}

	private void startLogging() {

		setupFabMenu();
		//startActivity(ActivityIntentHelper.LogTimeActivityBuilder.getLogTimeActivity(this, timeLogDescriptor.getWorkOrderId(), timeLogDescriptor.getTimePeriod().getId()));
	}

	void doSubmit(String workOrderId, String periodId) {
		showProgressDialog();
		DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
		final IRestClient.Callback submitCallback = new SubmitCallback(defaultRestClientResponseHandler);
		IRestClient restServiceHelper = getRestServiceHelper();
		restServiceHelper.registerCallback(RestApiContract.Method.submitTimePeriod, submitCallback);
		getRestServiceHelper().submitTimePeriod(this, workOrderId, periodId);
	}

	public void showProgressDialog() {
		ringProgressDialog = ProgressDialog.show(WeeklyTimeSheetActivity.this, "Please wait ...", "Submitting timesheet ...", true);
		ringProgressDialog.setCancelable(true);
	}

	public void hideProgressDialog() {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
	}

	private void showSuccessSnackBar() {
//        final View coordinatorLayoutView = findViewById(R.id.coordinatorLayout);
//        Snackbar snackbar = Snackbar.make(coordinatorLayoutView, R.string.mbo_snackbar_success_submit_message, Snackbar.LENGTH_LONG);
//        snackbar.show();
		ActivityToaster.showToastLongMessage(this, R.string.mbo_snackbar_success_submit_message);
	}

	@Override
	public boolean isSubmittable() {


		return isSubmittable;

	}

	class SubmitCallback implements IRestClient.Callback {
		private DefaultRestClientResponseHandler defaultHandler;

		public SubmitCallback(DefaultRestClientResponseHandler defaultHandler) {
			this.defaultHandler = defaultHandler;
		}

		@Override
		public void onComplete(UniversalRestResponse response) {
			hideProgressDialog();
			switch (response.getClientResult()) {
				case Ok: {
					showSuccessSnackBar();
					break;
				}

				case HttpError: {
					ApiErrorDialog.showApi_FailedToSubmitTimeError(WeeklyTimeSheetActivity.this);
					break;
				}

				default: {
					defaultHandler.onComplete(response);
				}
			}
		}
	}

	private class FabDescriptor {
		private FloatingActionButton fab;
		private boolean buttonEnableState;

		public FabDescriptor() {
			this.buttonEnableState = false;
		}

		public void setFab(FloatingActionButton fab) {
			this.fab = fab;
			updateFabVisibility(this.buttonEnableState);
		}

		public void updateFabVisibility(boolean visibility) {
			this.buttonEnableState = visibility;
			if (this.fab != null) {
				if(this.buttonEnableState) {
					this.fab.setVisibility(View.VISIBLE);
				} else {
					this.fab.setVisibility(View.GONE);
				}

			}
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
				animateFabMenuBackground(FAB_BACKGROUND_COLOR__DARK,Color.TRANSPARENT, false);
			}
		};
		fabMenu.setOnFloatingActionsMenuUpdateListener(listener);

		FloatingActionButton actionLogTime = (FloatingActionButton) findViewById(R.id.action_log_time);
		actionLogTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//startActivity(ActivityIntentHelper.ChooseWorkOrderActivityBuilder.getActivityForTimeTracking(getApplicationContext()));
				//startLogTime();
				Intent intent = new Intent(getApplicationContext(), LogTimeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(KEY__PARAMETERS_AS_SERIALIZABLE, new TimePeriodParameters(workorderid, null, null));
				intent.putExtras(bundle);
				startActivity(intent);
				fabMenu.collapse();
			}
		});

		FloatingActionButton actionLogExpenses = (FloatingActionButton) findViewById(R.id.action_log_expense);
		actionLogExpenses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(getApplicationContext(), ChooseExpenseTypeActivity.class);
				startActivity(intent);
				fabMenu.collapse();
			}
		});
	}
	private static final String KEY__PARAMETERS_AS_SERIALIZABLE = "PARAMETERS AS SERIALIZABLE";
	private String workorderid;
	private void animateFabMenuBackground(Integer colorFrom, Integer colorTo, boolean clickableFlag) {
		ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				fabHolderLayout.setBackgroundColor((Integer)animator.getAnimatedValue());
			}
		});
		fabHolderLayout.setClickable(clickableFlag);
		colorAnimation.start();
	}
	private RelativeLayout fabHolderLayout;
	private FloatingActionsMenu fabMenu;




	public boolean onKeyDown(int keyCode,KeyEvent keyEvent)
	{
		super.onKeyDown(keyCode,keyEvent);
		/*if(fabMenu.isExpanded())
		{
			fabMenu.collapse();
			return false;
		}else*/
			return true;
	}


	@Override
	public Loader<List<WorkOrder>> onCreateLoader(int id, Bundle args) {
		Loader<List<WorkOrder>> loader = null;
		if (id == LOADER__WORK_ORDERS__ID) {
			loader = new ChooseWorkOrderAsyncLoader(this.getApplication(), args);
		}
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<WorkOrder>> loader, List<WorkOrder> data) {
		workOrdersList = data;
		workorderid=workOrdersList.get(0).getId();

	}

	@Override
	public void onLoaderReset(Loader<List<WorkOrder>> loader) {

	}


	public void onResume()
	{
		super.onResume();

		Loader<List<WorkOrder>> loader;
		loader = getSupportLoaderManager().getLoader(LOADER__WORK_ORDERS__ID);
		loader.forceLoad();

	}

	@Override
	public void onSubmitTimeSheet(boolean flag) {

		if(flag)
		{
			setResult(1,null);
			finish();

		}
	}
}

package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.DataLoadingDispatcher;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.dialog.ApiErrorDialog;
import com.mbopartners.mbomobile.ui.model.Dao;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;
import com.mbopartners.mbomobile.ui.model.ExpenseSpinner;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;
import com.mbopartners.mbomobile.ui.util.Communicator;
import com.mbopartners.mbomobile.ui.util.DefaultRestClientResponseHandler;
import com.mbopartners.mbomobile.ui.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.framework.ui.keyboard.KeyboardUtil;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class LogExpenseActivity extends AutoLockActivity
        implements LogExpenseActivityFragment.OnFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<ExpenseForEdit>,ApiErrorDialog.ISaveexpenseListener, ExpenseFieldViewHolder_BigText.IanimateBottomBar {

    private static final String TAG = LogExpenseActivity.class.getSimpleName();

    private static final String KEY__SAVED_INSTANCE_STATE__EXPENSE = "ExpenseForEdit expense";
    private static final String KEY__SAVED_INSTANCE_STATE__NEW_IMAGE_URI = "CreationExpenseMode creationExpenseMode";
    private static final String KEY__SAVED_INSTANCE_STATE__PARAMS_FOR_LOAD_EXPENSE = "Bundle paramsForLoadExpense";

    private static final int LOADER__EXPENSE_FOR_EDIT__ID = 1;
    public static final int REQUEST_PICK = 9162;

    private static final boolean NAVIGATION_BACK = false;
    private static final boolean NAVIGATION_UP = true;

    private ImageView expenseImage;
    private TextView expenseNameTextView;
    //private TextView companyName;
    //private TextView workOrderName;
    private Spinner projectSelectionSpinner;
    private ArrayList<ExpenseSpinner> spinnerItemsArrayList;
    private FloatingActionButton fab;
    private LogExpenseActivityFragment fragment;
    private TextView submitTextview;
    private MenuSaveButtonDescriptor menuItemSave = new MenuSaveButtonDescriptor();
    private FloatingActionsMenu fabMenu;
    private com.getbase.floatingactionbutton.FloatingActionButton actionGallery;
    private com.getbase.floatingactionbutton.FloatingActionButton actionCamera;

    private View mFabFadeBackground;
    private int value;
    private ProgressDialog ringProgressDialog;
    private ProgressDialog ringProgressDialog01;

    private boolean navigationFlag = NAVIGATION_BACK;
    private CreationExpenseMode creationExpenseMode;

    private Bundle paramsForLoadExpense;
    protected Uri mNewImageUri;
    private ExpenseForEdit expense = null;
    private String picturefileName;
    private DataLoadingDispatcher dataLoadingDispatcher;
    private View includeview;
    private boolean nonBillableAllowed;
    private IRestClient restClient;
    private boolean submitPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_expense);
        setupUpAppTabButtonIfPossible();
        setupUi();
        restClient = getRestServiceHelper();
        new LoadWorkOrdersTask().execute();
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) getApplicationControllersManager()
                        .getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        dataLoadingDispatcher = new DataLoadingDispatcher(sharedPreferencesController);

        creationExpenseMode = new CreationExpenseMode();


        if (savedInstanceState == null) {
            paramsForLoadExpense = ActivityIntentHelper.getBundle(this);
            startLoadingExpenseForEdit(paramsForLoadExpense);
        } else {
            expense = (ExpenseForEdit) savedInstanceState.getSerializable(KEY__SAVED_INSTANCE_STATE__EXPENSE);

            mNewImageUri = savedInstanceState.getParcelable(KEY__SAVED_INSTANCE_STATE__NEW_IMAGE_URI);
            picturefileName=savedInstanceState.getString("fileName");
            paramsForLoadExpense = savedInstanceState.getBundle(KEY__SAVED_INSTANCE_STATE__PARAMS_FOR_LOAD_EXPENSE);
        }
    }

    private void setupToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (expense.mboId != null) {
            toolbar.setTitle("Recent Expenses");
        }else{
            toolbar.setTitle("Expense Type");
        }
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            Uri source;
            String picFileName=null;
            if (result != null && result.getData() != null) {
                source = result.getData();
                mNewImageUri = source;
                try {
                    picturefileName = source.getPath().substring(source.getPath().lastIndexOf("="));
                }catch (StringIndexOutOfBoundsException siobe){
                    picturefileName = source.getPath().substring(source.getPath().lastIndexOf("/"));
                }
            } else {
                source = mNewImageUri;
                picFileName=picturefileName;

            }
            if (expense.mboId != null) {
                sendPhoto(source);
            }else{
                tryToSaveExpense();
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            mNewImageUri = null;
            picturefileName=null;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillValuesUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressDialog();
        hideProgressDialog01();
        KeyboardUtil.htryToHideKeyboard(LogExpenseActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY__SAVED_INSTANCE_STATE__EXPENSE, expense);
        outState.putParcelable(KEY__SAVED_INSTANCE_STATE__NEW_IMAGE_URI, mNewImageUri);
        outState.putString("fileName", picturefileName);
        outState.putBundle(KEY__SAVED_INSTANCE_STATE__PARAMS_FOR_LOAD_EXPENSE, paramsForLoadExpense);
    }

    private void setupUi() {

        //expenseImage = (ImageView) findViewById(R.id.expense_ImageView);
        expenseNameTextView = (TextView) findViewById(R.id.expense_name_TextView);
        //companyName = (TextView) findViewById(R.id.company_name_TextView);
        //workOrderName = (TextView) findViewById(R.id.work_order_name_TextView);
        projectSelectionSpinner=(Spinner)findViewById(R.id.spinner01);
        includeview=findViewById(R.id.includeview);
        spinnerItemsArrayList=new ArrayList<>();
        submitTextview=(TextView)includeview.findViewById(R.id.submittext);

        findViewById(R.id.mbo_log_expense_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.htryToHideKeyboard(LogExpenseActivity.this);
            }
        });
        submitTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPressed = true;
                tryToSaveExpense();
            }
        });
        projectSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expense.mboWorkOrderId = spinnerItemsArrayList.get(position).getItem().getId();
                if (expense.mboWorkOrderId == null && nonBillableAllowed){
                    expense.billable = false;
                }else{
                    expense.billable = true;
                }

                if (expense.isItNewExpense()){
                    setPurposeFieldRequirement(expense.billable);
                    startLoadingExpenseForEdit(paramsForLoadExpense);
                }else{
                    setPurposeFieldRequirement(expense.billable);
                    reStartLoadingExpenseForEdit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setUpFabMenu01();
        hideFabMenu01();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
            value=bundle.getInt("value");
       /* workOrderArrayList= (ArrayList<WorkOrder>) bundle.getSerializable("serial");
        spinnerItemsArrayList.add(new ExpenseSpinner("Select project"));
        for(int i=0;i<workOrderArrayList.size();i++)
        {
            workorderName=(workOrderArrayList.get(i).getName());
            spinnerItemsArrayList.add(new ExpenseSpinner(workorderName));
        }*/
        fragment = (LogExpenseActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    public static final int FAB_BACKGROUND_COLOR__NORMAL = Color.TRANSPARENT;

    private void setUpFabMenu01()
    {
        mFabFadeBackground = findViewById(R.id.fab_fade_background);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        actionGallery = (com.getbase.floatingactionbutton.FloatingActionButton) fabMenu.findViewById(R.id.action_gallery);
        actionGallery.setOnClickListener(mAttachImageListener);

        actionCamera = (com.getbase.floatingactionbutton.FloatingActionButton) fabMenu.findViewById(R.id.action_camera);
        actionCamera.setOnClickListener(mAttachPhotoListener);

        final RelativeLayout fabMenuLayout = (RelativeLayout) findViewById(R.id.multiple_actions_layout);
        final LinearLayout appBarLayout = (LinearLayout) findViewById(R.id.appbar);

        fab = (FloatingActionButton) findViewById(R.id.mboFAB);
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

        ViewTreeObserver vto = appBarLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fabMenuLayout.setTranslationY(appBarLayout.getHeight() - fab.getHeight()/2);
                    appBarLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }

/*mFabFadeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabMenu.isExpanded()) {
                    fabMenu.collapse();
                    mFabFadeBackground.clearAnimation();
                    mFabFadeBackground.animate().alpha(0).setDuration(200).start();
                    mFabFadeBackground.setClickable(false);
                }
                else
                {
                    mFabFadeBackground.clearAnimation();
                    mFabFadeBackground.animate().alpha(1).setDuration(200).start();
                    mFabFadeBackground.setClickable(true);
                }
            }
        });*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabMenu.isExpanded()) {
                    fabMenu.collapse();
                    includeview.setVisibility(View.VISIBLE);
                    mFabFadeBackground.clearAnimation();
                    mFabFadeBackground.animate().alpha(0).setDuration(200).start();
                    mFabFadeBackground.setClickable(false);
                } else {
                    fabMenu.expand();
                    includeview.setVisibility(View.GONE);
                    mFabFadeBackground.clearAnimation();
                    mFabFadeBackground.animate().alpha(1).setDuration(200).start();
                    mFabFadeBackground.setClickable(true);
                    hideKeyboard();
                }
            }
        });
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            view.clearFocus();
        }
    }
    /*public void setupFabMenu01() {

        mFabFadeBackground = (RelativeLayout) findViewById(R.id.multiple_actions_layout);
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

        actionGallery = (com.getbase.floatingactionbutton.FloatingActionButton) fabMenu.findViewById(R.id.action_gallery);
        actionGallery.setOnClickListener(mAttachImageListener);

        actionCamera = (com.getbase.floatingactionbutton.FloatingActionButton) fabMenu.findViewById(R.id.action_camera);
        actionCamera.setOnClickListener(mAttachPhotoListener);
    }*/

    private void animateFabMenuBackground(Integer colorFrom, Integer colorTo, boolean clickableFlag) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mFabFadeBackground.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        mFabFadeBackground.setClickable(clickableFlag);
        if(clickableFlag) {
            mFabFadeBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fabMenu.isExpanded()) {
                        fabMenu.collapse();
                        includeview.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        colorAnimation.start();
    }

    private void fillValuesUi() {

        if (expense != null) {
            if (expense.mboExpenseTypeId != null && expense.expenseType != null) {
                //expenseImage.setImageResource(ChooseExpenseTypeActivityFragment.getExpenseImageIdTwo(expense.mboExpenseTypeId));
                if(expense.editable)
                {
                    includeview.setBackgroundColor(getResources().getColor(R.color.mbo_theme_blue_primary));
                    submitTextview.setEnabled(true);

                }else {
                    includeview.setBackgroundColor(getResources().getColor(R.color.mbo_simple_button_bg__color));
                    submitTextview.setEnabled(false);
                }
                expenseNameTextView.setText(expense.expenseType.getName());

            }
        }
    }

    private View.OnClickListener mAttachImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*if (expense.isItNewExpense()) {
                creationExpenseMode.switchToGalleryPhotoMode();
                tryToSaveExpense();
            }*/
            creationExpenseMode.switchToGalleryPhotoMode();
            startMakeGalleryPhoto();
            submitPressed = false;
            fab.performClick();
        }
    };

    private View.OnClickListener mAttachPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*if (expense.isItNewExpense()) {
                creationExpenseMode.switchToCameraPhotoMode();
                tryToSaveExpense();
            }*/
            creationExpenseMode.switchToCameraPhotoMode();
            startMakeCameraPhoto();
            submitPressed = false;
            fab.performClick();
        }
    };

    private void sendPhoto(Uri imageUri) {
        try {
            if (ringProgressDialog != null) {
                if (!ringProgressDialog.isShowing()) {
                    showProgressDialog(getString(R.string.mbo_LogExpense_ProgressDialog_title), getString(R.string.mbo_LogExpense_ProgressDialog_post_receipt_message));
                }
            }

            //TODO replace with AsyncTask
            //File tempFile = FileUtils.createTempFileFromInputStream(getContentResolver().openInputStream(mNewImageUri), getCacheDir(), "receipt_photo");

            Cursor returnCursor = getContentResolver().query(imageUri,null,null,null,null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            picturefileName = returnCursor.getString(nameIndex);
            returnCursor.close();
            File tempFile = FileUtils.createTempFileFromInputStream(getContentResolver().openInputStream(imageUri), getCacheDir(), picturefileName);
            IRestClient restClient = getRestServiceHelper();

            DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
            final IRestClient.Callback deleteReceiptCallback = new PostReceiptCallback(defaultRestClientResponseHandler);
            restClient.registerCallback(RestApiContract.Method.createReceipt, deleteReceiptCallback);
            restClient.postExpenseReceipt(LogExpenseActivity.this, expense.mboId, tempFile);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            ActivityToaster.showToastLongMessage(LogExpenseActivity.this, R.string.mbo_LogExpense_post_receipt_fail_message);
        }
    }

    public void showProgressDialog(String title, String message) {
        ringProgressDialog = ProgressDialog.show(LogExpenseActivity.this, title, message, true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.setCanceledOnTouchOutside(false);
    }
    public void showProgressDialog01(String title, String message,int theme) {
        // ringProgressDialog01 = ProgressDialog.show(LogExpenseActivity.this, title, message, true);
        ringProgressDialog01=new ProgressDialog(this,theme);
        ringProgressDialog01.setTitle(title);
        ringProgressDialog01.setMessage(message);
        ringProgressDialog01.setCancelable(false);
        ringProgressDialog01.setCanceledOnTouchOutside(false);
        ringProgressDialog01.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ringProgressDialog01.show();
    }

    public void hideProgressDialog() {
        if (ringProgressDialog != null) {
            ringProgressDialog.dismiss();
        }
    }

    public void hideProgressDialog01() {
        if (ringProgressDialog01 != null) {
            ringProgressDialog01.dismiss();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_expense_type, menu);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_save) {
            tryToSaveExpense();
            return true;
        }*/

        if (id == android.R.id.home) {
            navigationFlag = NAVIGATION_BACK;
            showExitDialogIfNeed(expense.editable && expense.isChanged());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  Intercept Back Button For Android 5+
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigationFlag = NAVIGATION_BACK;
        if(fabMenu.isExpanded())
        {
            fabMenu.collapse();
            includeview.setVisibility(View.VISIBLE);
            mFabFadeBackground.clearAnimation();
            mFabFadeBackground.animate().alpha(0).setDuration(200).start();
            mFabFadeBackground.setClickable(false);
        }else
            doNavigation(navigationFlag);
    }

    // Intercept Back Button For Android 4-
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            navigationFlag = NAVIGATION_BACK;
            if(fabMenu.isExpanded())
            {
                fabMenu.collapse();
                includeview.setVisibility(View.VISIBLE);
                mFabFadeBackground.clearAnimation();
                mFabFadeBackground.animate().alpha(0).setDuration(200).start();
                mFabFadeBackground.setClickable(false);
            }else
                doNavigation(navigationFlag);
            //showExitDialogIfNeed(expense.editable && expense.isChanged());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ArrayList<String> mandatoryFilledValues;
    private String vendorName,amount;
    // ---------- END OF Interception navigation buttons ------------------------------
    private void tryToSaveExpense() {
        KeyboardUtil.htryToHideKeyboard(LogExpenseActivity.this);
        ArrayList<String> emptyFields = expense.getMandatoryEmptyFileds();
        mandatoryFilledValues=expense.getMandatoryFilledValues();
        //double check the purpose field requirement
        setPurposeFieldRequirement(expense.billable);
        if (expense.isAllMandatoryFieldsFilled()) {

            setAmountValue();
            doSaveExpense();
        } else {
            showMandatoryFieldsDialog();
        }

    }

    private void setAmountValue(){
        for (ExpenseField expenseField : expense.expenseType.getFields()){
            if(expenseField.getName().equalsIgnoreCase("amount") || expenseField.getName().equalsIgnoreCase("rate")){
                amount = expense.expenseDataMap.get(expenseField.getMboId());
            }
        }
    }

    private void doSaveExpense() {
        //showProgressDialog(getString(R.string.mbo_LogExpense_ProgressDialog_title), getString(R.string.mbo_LogExpense_ProgressDialog_expense_message));
        if (submitPressed) {
            if (amount != null) {
                showProgressDialog01("Please Wait...", "Sending " + expense.expenseType.getName() + " $" + amount + " expense.", R.style.mboAlertTheme);
            } else {
                showProgressDialog01("Please Wait...", "Sending " + expense.expenseType.getName() + " expense.", R.style.mboAlertTheme);
            }
        }else{
            showProgressDialog(getString(R.string.mbo_LogExpense_ProgressDialog_title), getString(R.string.mbo_LogExpense_ProgressDialog_post_receipt_message));
        }

        //IRestClient restClient = getRestServiceHelper();

        DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
        final IRestClient.Callback putExpenseCallback = new PutExpenseCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback postExpenseCallback = new PostExpenseCallback(defaultRestClientResponseHandler);
        restClient.registerCallback(RestApiContract.Method.updateExpense, putExpenseCallback);
        restClient.registerCallback(RestApiContract.Method.createExpense, postExpenseCallback);

        Expense expenseForSave = this.expense.toExpenseForSave();
        if (this.expense.isItNewExpense()) {
            restClient.createExpense(this, expenseForSave);
        } else {
            restClient.updateExpense(this, expense.mboId, expenseForSave);
        }


    }

    private void showMandatoryFieldsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mbo_LogExpense_mandatory_fields_dialog_message);
        builder.setPositiveButton(R.string.mbo_LogExpense_mandatory_fields_dialog_Button_OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        AlertDialog mandatoryFieldsDialog = builder.create();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mandatoryFieldsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mandatoryFieldsDialog.show();

    }
    android.app.AlertDialog.Builder builder;
    android.app.AlertDialog dialog;
    public void showProgressDialog()
    {

        builder=new android.app.AlertDialog.Builder(this,R.style.mboAlertTheme);
        builder.setCancelable(false);
        builder.setTitle("Please wait");
        builder.setMessage("Sending " + expense.expenseType.getName() + " " + vendorName + " " + amount.toString() + " expense.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog=builder.create();
        dialog.show();
    }
    private void showExitDialogIfNeed(boolean userChangedData) {
        if (! userChangedData) {
            doNavigation(navigationFlag);
            return;
        }
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        discardDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        discardDialog.show();
    }

    private void doNavigation(boolean navigationFlag) {
        if (navigationFlag == NAVIGATION_BACK) {
            // nothing to do
        } else {
            startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityExpensePage(this));
        }
        finish();
    }


    public void updateFabVisibility() {
        if (expense != null && expense.editable) {
            showFabMenu();

        } else {
            hideFabMenu01();
        }
    }

    public void showFabMenu() {
        if (fabMenu != null) {
            fab.setVisibility(View.VISIBLE);
            fabMenu.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            fabMenu.setEnabled(true);
        }
    }

    public void hideFabMenu() {
        if (fabMenu != null) {
            fab.setVisibility(View.VISIBLE);
            fabMenu.setVisibility(View.VISIBLE);
            fab.setEnabled(false);
            fabMenu.setEnabled(false);
        }
    }
    public void hideFabMenu01() {
        if (fabMenu != null) {
            fab.setVisibility(View.INVISIBLE);
            fabMenu.setVisibility(View.INVISIBLE);
            fab.setEnabled(false);
            fabMenu.setEnabled(false);
        }
    }

    private void showSuccessToast() {

        ActivityToaster.showToastLongMessage(this, R.string.mbo_snackbar_success_expense_save_message);
    }

    private void doAfterSuccessSavingRoutine() {


        Bundle bundle=new Bundle();
        if (amount != null) {
            bundle.putString("displayResult", "Expense sent! " + expense.expenseType.getName() + " $" + amount + " is sent!");
        }else{
            bundle.putString("displayResult", "Expense sent! " + expense.expenseType.getName() + " is sent!");
        }
        setResult(3, ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityExpensePage(this,bundle));
        finish();
    }

    // ================================================================================
    //
    // Loader Callback interface implementation
    //
    // ================================================================================

    @Override
    public Loader<ExpenseForEdit> onCreateLoader(int id, Bundle args) {
        Loader<ExpenseForEdit> loader = null;
        if (id == LOADER__EXPENSE_FOR_EDIT__ID) {
            loader = new LoadExpenseForEditAsyncLoader(this.getApplication(), args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ExpenseForEdit> loader, ExpenseForEdit data) {

        if (expense == null) {
            expense = data;
        } else if(expense.isItNewExpense()) {
            expense.update(data);
        } else {
            expense.updateReceipts(data.receipts);
        }

        fillValuesUi();
        menuItemSave.updateSaveMenuItemVisibility(expense.editable);
        updateFabVisibility();
        notifyDataLoaded();

        int afterCreationExpenseAction = creationExpenseMode.getMode();
        switch (afterCreationExpenseAction) {
            case CreationExpenseMode.MODE_GENERAL : {
                // Nothing to do
                break;
            }
            case CreationExpenseMode.MODE_GET_CAMERA_PHOTO : {
                creationExpenseMode.switchToGeneralMode();
                sendPhoto(mNewImageUri);
                break;
            }
            case CreationExpenseMode.MODE_GET_GALLERY_PHOTO : {
                creationExpenseMode.switchToGeneralMode();
                sendPhoto(mNewImageUri);
                break;
            }
        }
        setupToolBar();
    }

    @Override
    public void onLoaderReset(Loader<ExpenseForEdit> loader) {

    }

    // ================================================================================
    //
    // Fragment Callback interface implementation
    //
    // ================================================================================

    @Override
    public ExpenseForEdit getExpenseForEdit() {
        return expense;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDeleteReceipt(final String receiptFilename) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mbo_LogExpense_delete_receipt_dialog_title);
        String message = String.format(getString(R.string.mbo_LogExpense_delete_receipt_dialog_message), receiptFilename);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.mbo_LogExpense_delete_receipt_dialog_Button_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                IRestClient restClient = getRestServiceHelper();

                DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(LogExpenseActivity.this);
                final IRestClient.Callback deleteReceiptCallback = new DeleteReceiptCallback(defaultRestClientResponseHandler, receiptFilename);
                restClient.registerCallback(RestApiContract.Method.deleteReceiptExpense, deleteReceiptCallback);

                restClient.deleteExpenseReceipt(LogExpenseActivity.this, expense.mboId, receiptFilename);
                showProgressDialog(getString(R.string.mbo_LogExpense_ProgressDialog_title), getString(R.string.mbo_LogExpense_ProgressDialog_remove_receipt_message));
            }
        });
        builder.setNegativeButton(R.string.mbo_LogExpense_delete_receipt_dialog_Button_NO, null);

        AlertDialog deleteDialog = builder.create();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        deleteDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        deleteDialog.show();
    }

    // ================================================================================
    //
    //
    //
    // ================================================================================

    private void startMakeCameraPhoto() {
        AppLockManager.getInstance().setExtendedTimeout();
        try {
            Intent intent=Communicator.getCameraIntent_01(LogExpenseActivity.this);
            mNewImageUri=intent.getExtras().getParcelable(MediaStore.EXTRA_OUTPUT);
            picturefileName=intent.getExtras().getString("fileName");
            /*Pair<Intent, Uri> chooser = Communicator.getCameraIntent(LogExpenseActivity.this);
            mNewImageUri = chooser.second;
            chooser.first.getStringExtra("fileName");*/
            startActivityForResult(intent, REQUEST_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(LogExpenseActivity.this, R.string.mbo_image_pick_error_no_source, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            ActivityToaster.showToastLongMessage(LogExpenseActivity.this, R.string.mbo_LogExpense_post_receipt_fail_message);
        }
    }

    private void startMakeGalleryPhoto() {
        AppLockManager.getInstance().setExtendedTimeout();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try {
            startActivityForResult(intent, REQUEST_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(LogExpenseActivity.this, R.string.mbo_image_pick_error_no_source, Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoadingExpenseForEdit(Bundle paramsForLoadExpense){
        getSupportLoaderManager()
                .initLoader(LOADER__EXPENSE_FOR_EDIT__ID, paramsForLoadExpense, this)
                .forceLoad();

    }

    private void reStartLoadingExpenseForEdit() {
        reStartLoadingExpenseForEdit(paramsForLoadExpense);
    }

    private void reStartLoadingExpenseForEdit(Bundle paramsForLoadExpense) {
        getSupportLoaderManager()
                .restartLoader(LOADER__EXPENSE_FOR_EDIT__ID, paramsForLoadExpense, this)
                .forceLoad();
    }

    private void notifyDataLoaded() {
        if (fragment != null) {
            fragment.onExpenseForEditReceived();
        }
    }

    private void notifyLocalDbNeedToReload() {
        dataLoadingDispatcher.notifyNeedDataReload();
    }

    @Override
    public void startanimation() {

        //// TODO: 7/6/16 add bottom-to-up animation to bottom bar.
        /*Toast.makeText(getBaseContext(),"hello",Toast.LENGTH_SHORT).show();

        overridePendingTransition(R.anim.bottom_down, R.anim.bottom_up);*/
    }

    class PostExpenseCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public PostExpenseCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            if (ringProgressDialog01 != null) {
                hideProgressDialog01();
            }
            switch (response.getClientResult()) {
                case Ok : {
                    notifyLocalDbNeedToReload();
                    if (creationExpenseMode.getMode() == CreationExpenseMode.MODE_GENERAL) {
                        doAfterSuccessSavingRoutine();

                    } else {
                        Expense createdExpense = (Expense) response.getResponseEntity();
                        String mboExpenseId = createdExpense.getMboId();
                        paramsForLoadExpense = ActivityIntentHelper.LogExpenseActivityBuilder.createParamForExistingExpense(mboExpenseId);
                        reStartLoadingExpenseForEdit(paramsForLoadExpense);
                    }
                    break;
                }

                case HttpError : {
                    int httpErrorCode = response.getClientResult().getStatusCode();
                    switch (httpErrorCode) {
                        case 400 : {
                            ApiErrorDialog.showApi_FailedToCreateAnExpenseError(LogExpenseActivity.this);
                            break;
                        }
                        case 500 : {
                            ApiErrorDialog.showApiDialog(LogExpenseActivity.this,expense.expenseType.getName(),amount,"");
                            Log.d("details",expense.expenseType.getName()+""+expense.amount+" "+vendorName);
                            break;
                        }
                        default : {
                            defaultHandler.onComplete(response);
                        }
                    }
                    break;
                }

                case NoConnectionError :
                case NetworkError :
                case TimeoutError :
                case ParseError :
                case Unknown : {
                    defaultHandler.onComplete(response);
                    break;
                }
                default: {
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class PutExpenseCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public PutExpenseCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            hideProgressDialog01();
            switch (response.getClientResult()) {
                case Ok : {
//                    notifyLocalDbNeedToReload();
                    doAfterSuccessSavingRoutine();
                    break;
                }

                case HttpError : {
                    int httpErrorCode = response.getClientResult().getStatusCode();
                    switch (httpErrorCode) {
                        case 400 : {
                            // ApiErrorDialog.showApi_FailedToUpdateError(LogExpenseActivity.this);
                            ApiErrorDialog.showApiDialog(LogExpenseActivity.this,expense.expenseType.getName(),amount,"");
                            Log.d("details",expense.expenseType.getName()+""+expense.amount+" "+vendorName);
                            break;
                        }
                        case 500 : {
                            ApiErrorDialog.showApiDialog(LogExpenseActivity.this,expense.expenseType.getName(),amount,"");
                            Log.d("details",expense.expenseType.getName()+""+expense.amount+" "+vendorName);
                            break;
                        }
                        default : {
                            defaultHandler.onComplete(response);
                        }
                    }
                    break;
                }

                case NoConnectionError :
                case NetworkError :
                case TimeoutError :
                case ParseError :
                case Unknown : {
                    defaultHandler.onComplete(response);
                    break;
                }
                default: {
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class DeleteReceiptCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;
        private String receiptFilename;

        public DeleteReceiptCallback(DefaultRestClientResponseHandler defaultHandler, String receiptFilename) {
            this.defaultHandler = defaultHandler;
            this.receiptFilename = receiptFilename;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            hideProgressDialog();
            switch (response.getClientResult()) {
                case Ok : {
                    String message = String.format(getString(R.string.mbo_LogExpense_delete_receipt_success_message), receiptFilename);
                    ActivityToaster.showToastLongMessage(LogExpenseActivity.this, message);

                    //TODO replace this with db handler   Dao.deleteExpenseReceipt(LogExpenseActivity.this.getApplication(), expense.mboId, receiptFilename);
                    hideProgressDialog();
                    reStartLoadingExpenseForEdit();
                    break;
                }

                case HttpError :
                case NoConnectionError :
                case NetworkError :
                case TimeoutError :
                case ParseError :
                case Unknown : {
                    defaultHandler.onComplete(response);
                    break;
                }
                default: {
                    defaultHandler.onComplete(response);
                }
            }
        }
    }

    class PostReceiptCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public PostReceiptCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            hideProgressDialog();
            switch (response.getClientResult()) {
                case Ok : {
                    String message = getString(R.string.mbo_LogExpense_post_receipt_success_message);
                    ActivityToaster.showToastLongMessage(LogExpenseActivity.this, message);
                    hideProgressDialog();
                    reStartLoadingExpenseForEdit();
                    break;
                }

                case HttpError :
                case NoConnectionError :
                case NetworkError :
                case TimeoutError :
                case ParseError :
                case Unknown: {
                    defaultHandler.onComplete(response);
                    break;
                }
                default: {
                    defaultHandler.onComplete(response);
                }
            }
            creationExpenseMode.switchToGeneralMode();
        }
    }

    private class CreationExpenseMode {
        private static final int MODE_GENERAL = 0;
        private static final int MODE_GET_CAMERA_PHOTO = 1;
        private static final int MODE_GET_GALLERY_PHOTO = 2;

        private int mode;

        public CreationExpenseMode() {
            this.mode = MODE_GENERAL;
        }

        public void switchToGeneralMode() {
            this.mode = MODE_GENERAL;
        }

        public void switchToCameraPhotoMode() {
            this.mode = MODE_GET_CAMERA_PHOTO;
        }

        public void switchToGalleryPhotoMode() {
            this.mode = MODE_GET_GALLERY_PHOTO;
        }

        public int getMode() {
            return mode;
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

    @Override
    public void saveExpense() {
        tryToSaveExpense();
    }

    /*private ILogExpenseSanckbarListener iLogExpenseSanckbarListener;
    public interface ILogExpenseSanckbarListener
    {
        void showLogExpenseListener();
    }
    private Context context;
    public LogExpenseActivity(Context context)
    {
        this.context=context;
        iLogExpenseSanckbarListener=(ILogExpenseSanckbarListener)context;
    }*/


    /**
     * Work Orders
     */
    private class LoadWorkOrdersTask extends AsyncTask<Void,Void,List<WorkOrder>>{
        protected List<WorkOrder> doInBackground(Void...params){
            List<WorkOrder> allWorkOrders = Dao.loadWorkOrdersFullTree(getApplication());
            nonBillableAllowed = Dao.loadUser(getApplication()).getNonbillableAllowed();

            List<WorkOrder> workOrders;

            if(nonBillableAllowed) {
                workOrders = Converter.filterBillableExpensesAllowedWorkOrders(allWorkOrders);

                WorkOrder unBillableWorkOrder = new WorkOrder(
                        null,
                        "Nonbillable Expense",
                        null,
                        false,
                        false,
                        null,
                        null
                );
                workOrders.add(0, unBillableWorkOrder);

            } else {
                workOrders = Converter.filterAllowedWorkOrders(allWorkOrders);
            }

            return workOrders;
        }

        protected void onPostExecute(List<WorkOrder> workOrders){
            if (spinnerItemsArrayList.size() == 0){
                spinnerItemsArrayList.clear();
            }
            for (WorkOrder workOrder : workOrders){
                spinnerItemsArrayList.add(new ExpenseSpinner(workOrder));
            }
            LogExpenseSpinnerAdapter arrayAdapter=new LogExpenseSpinnerAdapter(getApplicationContext(),spinnerItemsArrayList);
            projectSelectionSpinner.setAdapter(arrayAdapter);
            projectSelectionSpinner.setClickable(true);
            int index = 0;
            if (expense != null) {
                if (expense.workOrderName != null) {
                    for (WorkOrder workOrder : workOrders) {
                        if (workOrder.getName().equalsIgnoreCase(expense.workOrderName)) {
                            index = workOrders.indexOf(workOrder);
                        }
                    }
                }
            }
            projectSelectionSpinner.setSelection(index);
        }
    }

    /**
     * Set the purposeField for the expense
     * @param billable
     */
    private void setPurposeFieldRequirement(Boolean billable){
        for (ExpenseField expenseField : expense.expenseType.getFields()){
            if (expenseField.getMboId().equalsIgnoreCase("purposeId") || expenseField.getMboId().equalsIgnoreCase("purpose")){
                expenseField.setRequired(!billable);
            }
        }
    }
}

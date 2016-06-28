package com.mbopartners.mbomobile.ui.activity.dashboard.expense;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;

import java.util.List;

public class ExpensePageFragment extends Fragment implements ExpenseRecyclerViewAdapter.IBottombarVisibility {
    private static final String TAG = ExpensePageFragment.class.getSimpleName();

    private UiModel uiModel = new UiModel();
    private DataModel dataModel = new DataModel();
    private LinearLayoutManager      linearLayoutManager;
    private SwipeRefreshLayout swipeContainer;
    private ExpenseRecyclerViewAdapter adapter;
    private ExpensesInteractionListener mListener;
    private ExpenseRecyclerViewAdapter.IExpenseCallbackListener iExpenseCallbackListener;
    private int includeViewHeight;
    private boolean includeviewFlag;
    public static ExpensePageFragment newInstance() {
        ExpensePageFragment fragment = new ExpensePageFragment();
        return fragment;
    }

    public ExpensePageFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iExpenseCallbackListener=(ExpenseRecyclerViewAdapter.IExpenseCallbackListener)activity;

        Log.v(TAG, "onAttach()");
        try {
            mListener = (ExpensesInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + ExpensesInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate()");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");
        View fragmentRootView = inflater.inflate(R.layout.fragment_page_expense, container, false);

        //====================================================================
        swipeContainer = (SwipeRefreshLayout) fragmentRootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //====================================================================

        dataModel.setExpenseItemList(mListener.getExpenses());
//        dataModel.periodStr = DateUtil.get4weekPeriod()__;
        setupUi(fragmentRootView);
        updateUi();
        return fragmentRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.v(TAG, "onDetach()");
    }

    public void onExpenseSelected(String mboExpenseId ) {
        if (mListener != null) {
            mListener.onExpenseSelected(mboExpenseId);
        }
    }

    private void setupUi(View fragmentRootView) {
        //uiModel.periodTextView = (TextView) fragmentRootView.findViewById(R.id.mbo_dashboard_time_period_textView); ;
        linearLayoutManager = new LinearLayoutManager (getActivity());

        uiModel.newExpenseTextview=(TextView)fragmentRootView.findViewById(R.id.newExpenseTextview);
        uiModel.expenseLayout=(RelativeLayout)fragmentRootView.findViewById(R.id.bottombar);
        uiModel.expenseLayout.setVisibility(View.GONE);
        uiModel.rvExpenses = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        uiModel.rvExpenses.setLayoutManager(linearLayoutManager);

        adapter = new ExpenseRecyclerViewAdapter(dataModel.getExpenseItemList(),getActivity(),this);
        adapter.SetOnItemClickListener(new ExpenseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String expenseMboId) {
                onExpenseSelected(expenseMboId);
            }
        });
        //uiModel.rvExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        uiModel.rvExpenses.setAdapter(adapter);
        uiModel.rvExpenses.setScrollContainer(true);
        uiModel.rvExpenses.setOnScrollListener(scrollListener);
        uiModel.newExpenseTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iExpenseCallbackListener.callbackExpense();
            }
        });



    }
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener () {
        @Override
        public void onScrolled (RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);
            int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition ();
            int visibleItemCount = linearLayoutManager.getChildCount ();
            int totalItemCount = linearLayoutManager.getItemCount ();
//            Log.d("onScrolled","past " + pastVisiblesItems + " visble " + visibleItemCount + " totalItems " + totalItemCount);
            if(pastVisiblesItems>=1)
            {

                uiModel.expenseLayout.setVisibility(View.VISIBLE);



            }else {
                uiModel.expenseLayout.setVisibility(View.GONE);

            }


        }
    };
    public void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,view.getHeight(),0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    private void updateUi() {
        //uiModel.periodTextView.setText(dataModel.getPeriodStr());

    }

    private void updateAdapter() {
        adapter.updateDataSource(dataModel.getExpenseItemList());
    }

    public void onExpensesReceived() {
//        dataModel.periodStr = DateUtil.get4weekPeriod()__;

        if (mListener == null) {
            return;
        } else {
            dataModel.setExpenseItemList(mListener.getExpenses());
            updateAdapter();
            updateUi();
        }
    }

    private void refreshData() {
        if (mListener == null) {
            return;
        } else {
            swipeContainer.setRefreshing(false);
            mListener.onRefreshData();
        }
    }

    /*Implementing the method to make the bottom bar visible or not.*/
    @Override
    public void setVisible(boolean flag) {
        if(flag) {
            uiModel.expenseLayout.setVisibility(View.VISIBLE);
        } else {
            uiModel.expenseLayout.setVisibility(View.GONE);
            uiModel.rvExpenses.invalidate();
            uiModel.expenseLayout.invalidate();
                    }
    }

    // ================================================================================

    public interface ExpensesInteractionListener {
        List<ExpenseTimesheetItem> getExpenses();
        void onExpenseSelected(String mboExpenseId);
        void onRefreshData();
    }

    private class UiModel {
        public TextView periodTextView;
        public TextView newExpenseTextview;
        public RecyclerView rvExpenses;
        public RelativeLayout expenseLayout;

    }


    // ================================================================================


}
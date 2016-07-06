package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollField;
import com.mbopartners.mbomobile.ui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MboAdil on 29/6/16.
 */
public class PayrollFragment extends Fragment {
    private static final String TAG = PayrollFragment.class.getSimpleName();
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView payrollRecyclerView;
    private PayrollRecyclerViewAdapter adapter;
    private List<PayrollField> payrollFields;
    private Context context;
    private PayrollFragmentInteractionListener mListener = new InteractionListenerWrapper(null);
    public static PayrollFragment newInstance() {
        PayrollFragment fragment = new PayrollFragment();
        return fragment;
    }

    public PayrollFragment() {
        payrollFields=new ArrayList<>();
        payrollFields.add(new PayrollField("Business Account Balance",false,"$200,000",""));
        payrollFields.add(new PayrollField("Next Payment",true,"Unscheduled",""));
        payrollFields.add(new PayrollField("Last Payment",false,"$2,300","09/1/15-20/2/15"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");
        View fragmentRootView = inflater.inflate(R.layout.fragment_page_payroll, container, false);

        swipeContainer = (SwipeRefreshLayout) fragmentRootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        payrollRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        payrollRecyclerView.setHasFixedSize(true);
        payrollRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new PayrollRecyclerViewAdapter(context,payrollFields);
        payrollRecyclerView.setAdapter(adapter);
        return fragmentRootView;
    }

    public void onResume()
    {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    public void showPayroll() {
        Log.d(TAG, "showPayroll");

        if (mListener == null) {
            return;
        }
        List<BusinessCenter> fields = mListener.getBusinessCenterData();
        //adapter.updateDataSource(fields, periodStr);
    }

    public interface PayrollFragmentInteractionListener {
        List<BusinessCenter> getBusinessCenterData();
        void onRefreshData();
    }
    private class InteractionListenerWrapper implements PayrollFragmentInteractionListener {
        private final PayrollFragmentInteractionListener realListener;

        public InteractionListenerWrapper(PayrollFragmentInteractionListener listener) {
            this.realListener = listener;
        }

        @Override
        public List<BusinessCenter> getBusinessCenterData() {
            if (realListener != null) {
                return realListener.getBusinessCenterData();
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        @Override
        public void onRefreshData() {
            if (realListener != null) {
                realListener.onRefreshData();
            }
        }
    }
}

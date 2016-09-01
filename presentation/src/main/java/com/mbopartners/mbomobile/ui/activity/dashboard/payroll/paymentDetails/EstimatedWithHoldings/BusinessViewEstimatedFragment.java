package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.BusinessWithHoldingsFragment;

import java.util.List;

public class BusinessViewEstimatedFragment extends Fragment {

    private static final String LOG_TAG = BusinessWithHoldingsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private BusinessViewEstimatedRecycleViewAdapter adapter;
    private BusinessWithHoldingInteractionListener mListener;
    public static BusinessViewEstimatedFragment newInstance(String param1, String param2) {


        Log.v(LOG_TAG, "Constructor()");
        BusinessViewEstimatedFragment fragment = new BusinessViewEstimatedFragment();

        return fragment;
    }

    public BusinessViewEstimatedFragment() {
        Log.v(LOG_TAG, "defaultConstructor()");
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Log.v(LOG_TAG,"onAttach()");
        try {
            mListener=(BusinessWithHoldingInteractionListener)activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement " + BusinessWithHoldingsFragment.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView()");

        View fragmentRootView = inflater.inflate(R.layout.fragment_businesswithholding, container, false);
        recyclerView=(RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new BusinessViewEstimatedRecycleViewAdapter(getActivity(),mListener.getBusinessWithHoldingList(),mListener.getSelectedItemPosition());
        recyclerView.setAdapter(adapter);
        recyclerView.setScrollContainer(true);
        return fragmentRootView;
    }


    public interface BusinessWithHoldingInteractionListener{

        List<BusinessWithHolding> getBusinessWithHoldingList();
        int getSelectedItemPosition();
    }
}
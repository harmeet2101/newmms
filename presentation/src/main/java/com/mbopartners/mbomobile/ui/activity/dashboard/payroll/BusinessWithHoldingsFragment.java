package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

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

import java.util.List;

/**
 * Created by MboAdil on 19/7/16.
 */
public class BusinessWithHoldingsFragment extends Fragment {

    private static final String LOG_TAG = BusinessWithHoldingsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private BusinessWithholdingsRecyclerciewAdapter adapter;
    private BusinessWithHoldingInteractionListener mListener;
    public static BusinessWithHoldingsFragment newInstance(String param1, String param2) {


        Log.v(LOG_TAG, "Constructor()");
        BusinessWithHoldingsFragment fragment = new BusinessWithHoldingsFragment();

        return fragment;
    }

    public BusinessWithHoldingsFragment() {
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
        adapter = new BusinessWithholdingsRecyclerciewAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return fragmentRootView;
    }


    public interface BusinessWithHoldingInteractionListener{

        List<BusinessWithHolding> getBusinessWithHoldingList();
    }
}

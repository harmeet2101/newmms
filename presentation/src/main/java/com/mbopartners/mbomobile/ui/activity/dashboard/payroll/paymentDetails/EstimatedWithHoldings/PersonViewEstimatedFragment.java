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

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PersonalWithHoldingsFragment;

import java.util.List;

public class PersonViewEstimatedFragment extends Fragment {

    private static final String LOG_TAG = PersonalWithHoldingsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private PersonViewEstimatedRecycleViewAdapter adapter;
    private PersonalHoldingInteractionListener mListener;
    public static PersonViewEstimatedFragment newInstance(String param1, String param2) {


        Log.v(LOG_TAG, "Constructor()");
        PersonViewEstimatedFragment fragment = new PersonViewEstimatedFragment();

        return fragment;
    }

    public PersonViewEstimatedFragment() {
        Log.v(LOG_TAG, "defaultConstructor()");
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(LOG_TAG, "onAttach()");
        try {
            mListener = (PersonalHoldingInteractionListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + PersonalWithHoldingsFragment.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView()");

        View fragmentRootView = inflater.inflate(R.layout.fragment_personalwithholdings, container, false);
        recyclerView=(RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new PersonViewEstimatedRecycleViewAdapter(getActivity(),mListener.getPersonWithHoldingList(),mListener.getSelectedItemPosition());

        recyclerView.setAdapter(adapter);
        recyclerView.setScrollContainer(true);

        return fragmentRootView;
    }


    public interface PersonalHoldingInteractionListener
    {
        List<PersonWithHolding> getPersonWithHoldingList();
        int getSelectedItemPosition();
    }
}

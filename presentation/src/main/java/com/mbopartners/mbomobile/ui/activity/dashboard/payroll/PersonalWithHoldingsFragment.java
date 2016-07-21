package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.ui.R;

/**
 * Created by MboAdil on 19/7/16.
 */
public class PersonalWithHoldingsFragment extends Fragment {

    private static final String LOG_TAG = BusinessWithHoldingsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private PersonWithholdingsRecylerViewAdapter adapter;
    public static PersonalWithHoldingsFragment newInstance(String param1, String param2) {


        Log.v(LOG_TAG, "Constructor()");
        PersonalWithHoldingsFragment fragment = new PersonalWithHoldingsFragment();

        return fragment;
    }

    public PersonalWithHoldingsFragment() {
        Log.v(LOG_TAG, "defaultConstructor()");
        // Required empty public constructor
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
        adapter = new PersonWithholdingsRecylerViewAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return fragmentRootView;
    }
}
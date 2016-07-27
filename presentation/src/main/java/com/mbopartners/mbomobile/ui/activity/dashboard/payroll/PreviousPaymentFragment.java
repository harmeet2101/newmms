package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;

import java.util.List;


/**
 * Created by MboAdil on 19/7/16.
 */
public class PreviousPaymentFragment extends Fragment {


    private RecyclerView previousPaymentsRecyclerView;
    private PreviousPaymentRecylerViewAdapter adapter;
    private OnFragmentInteractionListener mListener;
    public PreviousPaymentFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + PreviousPaymentFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentRootView = inflater.inflate(R.layout.fragment_payroll_previous_payments, container, false);
        previousPaymentsRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);

        previousPaymentsRecyclerView.setHasFixedSize(true);
        previousPaymentsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new PreviousPaymentRecylerViewAdapter(getActivity(),null,mListener.getPayrollSummary());
        adapter.SetOnItemClickListener(new PreviousPaymentRecylerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //ActivityToaster.showToastLongMessage(getActivity(),"under production");
                mListener.onItemClick(position);
            }
        });
        previousPaymentsRecyclerView.setAdapter(adapter);
        previousPaymentsRecyclerView.setScrollContainer(true);
        return fragmentRootView;
    }

    public void onPersonWithHoldingsDataReceived() {

        if (mListener != null) {
            adapter.updateDataSource(mListener.getPayrollSummary());
        }
    }


    public interface OnFragmentInteractionListener {

        List<PayrollTransactions> getPayrollSummary();
        void onItemClick(int position);
    }
}

package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

public class PaymentDetailsFragment extends Fragment {


    private RecyclerView paymentDetailsRecyclerView;
    private PaymentDetailsRecyclerViewAdapter adapter;
    private OnFragmentInteractionListener mListener;


    public PaymentDetailsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + PaymentDetailsFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentRootView = inflater.inflate(R.layout.fragment_payroll_payment_details, container, false);
        paymentDetailsRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);

        paymentDetailsRecyclerView.setHasFixedSize(true);
        paymentDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new PaymentDetailsRecyclerViewAdapter(getActivity(),null,mListener.getPayrollSummary());
        /*adapter.SetOnItemClickListener(new PaymentDetailsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //ActivityToaster.showToastLongMessage(getActivity(),"under production");
                mListener.onItemClick(position);
            }
        });*/
        paymentDetailsRecyclerView.setAdapter(adapter);
        paymentDetailsRecyclerView.setScrollContainer(true);
        return fragmentRootView;
    }


    public void onDataReceived() {

        if (mListener != null) {
            adapter.updateDataSource(mListener.getPayrollSummary());
        }
    }
    public interface OnFragmentInteractionListener {

        List<PayrollSummary> getPayrollSummary();
        void onItemClick(int position);
    }
}

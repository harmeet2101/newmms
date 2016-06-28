package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;


public class LogExpenseActivityFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private LogExpenseRecyclerViewAdapter adapter;

    private ExpenseForEdit expense = null;
    private LinearLayoutManager linearLayoutManager;

    public static LogExpenseActivityFragment newInstance() {
        LogExpenseActivityFragment fragment = new LogExpenseActivityFragment();
        return fragment;
    }

    public LogExpenseActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + LogExpenseActivityFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootFragmentView = inflater.inflate(R.layout.fragment_log_expense, container, false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) rootFragmentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new LogExpenseRecyclerViewAdapter(getActivity(),expense);
        adapter.setOnReceiptLongClickListener(mListener);

        recyclerView.setAdapter(adapter);
        recyclerView.setScrollContainer(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                Log.d("onScrolled", "past " + pastVisiblesItems + " visble " + visibleItemCount + " totalItems " + totalItemCount);
            }
        });
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                KeyboardUtil.htryToHideKeyboard(getActivity());
//                return false;
//            }
//        });
        return rootFragmentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onExpenseForEditReceived() {
        if (mListener != null) {
            adapter.updateDataSource(getExpenseForEdit());
        }
    }

    private ExpenseForEdit getExpenseForEdit() {
        if (mListener != null) {
            return mListener.getExpenseForEdit();
        } else {
            return null;
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        ExpenseForEdit getExpenseForEdit();
        void onFragmentInteraction(Uri uri);
        void onDeleteReceipt(String receiptFilename);
    }
}

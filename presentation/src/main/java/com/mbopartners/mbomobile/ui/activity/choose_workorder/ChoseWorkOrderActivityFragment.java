package com.mbopartners.mbomobile.ui.activity.choose_workorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.R;

import java.util.Collections;
import java.util.List;

import ua.com.mobidev.android.framework.ui.view.recyclerview.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoseWorkOrderActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoseWorkOrderActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoseWorkOrderActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    private ChoseWorkOrderRecyclerViewAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoseWorkOrderActivityFragment newInstance(String param1, String param2) {
        ChoseWorkOrderActivityFragment fragment = new ChoseWorkOrderActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChoseWorkOrderActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + ChoseWorkOrderActivityFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootFragmentView = inflater.inflate(R.layout.fragment_choose_work_order, container, false);
        recyclerView = (RecyclerView) rootFragmentView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new ChoseWorkOrderRecyclerViewAdapter(getWorkOrders());
        adapter.SetOnItemClickListener(new ChoseWorkOrderRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onWorkOrderSelected(String workOrderId) {

                ChoseWorkOrderActivityFragment.this.onWorkOrderSelected(workOrderId);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        recyclerView.setScrollContainer(true);
        return rootFragmentView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onWorkOrderSelected(String workOrderId) {
        if (mListener != null) {
            mListener.onWorkOrderSelected(workOrderId);
        }
    }

    public void onWorkOrdersReceived() {
        if (mListener != null) {
            adapter.updateDataSource(getWorkOrders());
        }
    }

    private List<WorkOrder> getWorkOrders() {
        if (mListener != null) {
         return mListener.getWorkOrdersList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public interface OnFragmentInteractionListener {
        List<WorkOrder> getWorkOrdersList();
        void onWorkOrderSelected(String workOrderId);
    }

}
package com.mbopartners.mbomobile.ui.activity.dashboard.revenue;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.Collections;
import java.util.List;

import ua.com.mobidev.android.framework.ui.view.recyclerview.DividerItemDecoration;

// In this case, the fragment displays simple text based on the page
public class RevenuePageFragment extends Fragment implements FontController{
    private static final String LOG_TAG = RevenuePageFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RevenueFragmentInteractionListener mListener = new InteractionListenerWrapper(null);
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvFields;
    private RevenueRecyclerViewAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenuePageFragment newInstance(String param1, String param2) {
        Log.v(LOG_TAG, "Constructor()");
        RevenuePageFragment fragment = new RevenuePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RevenuePageFragment() {
        Log.v(LOG_TAG, "defaultConstructor()");
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView()");

        View fragmentRootView = inflater.inflate(R.layout.fragment_page_revenue, container, false);
        //TextView textView=(TextView)fragmentRootView.findViewById(R.id.textView);
        View view=fragmentRootView.findViewById(R.id.textView);
        setFont(view);
        //===========================================================================

        swipeContainer = (SwipeRefreshLayout) fragmentRootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //==============================================================================



        rvFields = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        // Create adapter passing in the sample user data
        adapter = new RevenueRecyclerViewAdapter(getActivity(),mListener.getDashboardData(), DateUtil.get4weekPeriod());
        // Attach the adapter to the recyclerview to populate items
        rvFields.setAdapter(adapter);
        // Set layout manager to position the items
        rvFields.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvFields.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rvFields.setScrollContainer(true);

        return fragmentRootView;
    }

    public void onResume()
    {
        super.onResume();
        Log.v(LOG_TAG, "onResume()");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        mListener.onDashboardSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(LOG_TAG, "onAttach()");
        try {
            mListener = new InteractionListenerWrapper((RevenueFragmentInteractionListener) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + RevenueFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        Log.v(LOG_TAG, "onDetach()");
        super.onDetach();
        mListener = new InteractionListenerWrapper(null);
    }

    public void showRevenue() {
        Log.d(LOG_TAG, "showRevenue");
        String periodStr = DateUtil.get4weekPeriod();

        if (mListener == null) {
            return;
        }
        List<DashboardField> fields = mListener.getDashboardData();
        adapter.updateDataSource(fields, periodStr);
    }

    private void refreshData() {
        if (mListener == null) {
            return;
        } else {
            swipeContainer.setRefreshing(false);
            mListener.onRefreshData();
        }
    }

    public interface RevenueFragmentInteractionListener {
        List<DashboardField> getDashboardData();
        void onDashboardSelected();
        void onRefreshData();
    }

    private class InteractionListenerWrapper implements RevenueFragmentInteractionListener {
        private final RevenueFragmentInteractionListener realListener;

        public InteractionListenerWrapper(RevenueFragmentInteractionListener listener) {
            this.realListener = listener;
        }

        @Override
        public List<DashboardField> getDashboardData() {
            if (realListener != null) {
                return realListener.getDashboardData();
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        @Override
        public void onDashboardSelected() {
            if (realListener != null) {
                realListener.onDashboardSelected();
            } else {
                // Nothing to do
            }
        }

        @Override
        public void onRefreshData() {
            if (realListener != null) {
                realListener.onRefreshData();
            }
        }
    }

    public void setFont(View...args)
    {
        TextView textView1=(TextView)args[0];
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Regular.ttf");
        textView1.setTypeface(typeface);

    }
}

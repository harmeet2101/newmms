package com.mbopartners.mbomobile.ui.activity.dashboard.timesheet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.model.TimeSheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.List;

/**
 * Use the {@link TimePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePageFragment extends Fragment implements FontController{
    private static final String TAG = TimePageFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TimeFragmentInteractionListener mListener;
    private TextView periodTextView,headingTextview;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView timeSheetRecyclerView;
    private SmartSectionedRecyclerViewAdapter adapter;
    private Context context;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimePageFragment newInstance(String param1, String param2,Context context) {
        TimePageFragment fragment = new TimePageFragment(context);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public TimePageFragment(Context context) {
        this.context=context;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"onCreate()");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void onResume()
    {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG,"onCreateView()");
        View fragmentRootView = inflater.inflate(R.layout.fragment_page_time, container, false);
        periodTextView = (TextView) fragmentRootView.findViewById(R.id.mbo_dashboard_time_period_textView);
        View view=fragmentRootView.findViewById(R.id.textView);
        setFont(view);
        timeSheetRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        timeSheetRecyclerView.setHasFixedSize(true);
        timeSheetRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //============================================================================

        swipeContainer = (SwipeRefreshLayout) fragmentRootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //============================================================================


        final List<TimeSheetItem> dataToShow = Converter.workOrder_2_SummaryTimeSheet(mListener.getTimeData());
        adapter = new SmartSectionedRecyclerViewAdapter(dataToShow,context,this);
        adapter.SetOnItemClickListener(new SmartSectionedRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(String workOrderId, String timePeriodId,boolean isSubmittable) {

                onWorkOrderSelected(workOrderId, timePeriodId,isSubmittable);
            }
        });


        timeSheetRecyclerView.setAdapter(adapter);
        timeSheetRecyclerView.setScrollContainer(true);
        return fragmentRootView;
    }

    public void onWorkOrderSelected(String workOrderId, String timePeriodId,boolean isSubmittable) {
        if (mListener != null) {
            mListener.onWorkOrderSelected(workOrderId, timePeriodId,isSubmittable);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(TAG, "onAttach()");
        try {
            mListener = (TimeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + TimeFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach()");
        mListener = null;
    }

    public void showTimes() {
        String periodStr = DateUtil.get4weekPeriod();
        //periodTextView.setText(periodStr);

        if (mListener == null) {
            return;
        }

        List<WorkOrder> workOrders = mListener.getTimeData();

        adapter.updateDataSource(Converter.workOrder_2_SummaryTimeSheet(workOrders));
    }

    private void refreshData() {
        if (mListener == null) {
            return;
        } else {
            swipeContainer.setRefreshing(false);
            mListener.onRefreshData();
        }
    }

    public interface TimeFragmentInteractionListener {
        List<WorkOrder> getTimeData();
        void onWorkOrderSelected(String workOrderId, String timePeriodName,boolean isSubmittable);
        void onRefreshData();
    }


    public void setFont(View...args)
    {
        TextView textView1=(TextView)args[0];
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Regular.ttf");
        textView1.setTypeface(typeface);
    }


    public interface IAddTimeListener{
        public void startAddTime();
    }
    public void setOnIAddTimeListener(final IAddTimeListener iAddTimeListener)
    {
        this.iAddTimeListener=iAddTimeListener;
    }
    private IAddTimeListener iAddTimeListener;
}
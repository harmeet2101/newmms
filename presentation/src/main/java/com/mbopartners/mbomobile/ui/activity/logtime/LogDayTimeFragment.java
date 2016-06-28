package com.mbopartners.mbomobile.ui.activity.logtime;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.TimeEntryToSave;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class LogDayTimeFragment extends Fragment {

    public static final String ARG_PARAM_DAY_LOGS = "The list of Day Logs";
    public static final String ARG_PARAM_TIME_TASK_ID = "Only TimeEntry with this timeTaskId holds Note for whole day!";
    public static final String ARG_PARAM_TIME_PERIOD_IS_EDITABLE = "Is all TimeEntries in parent TimePeriod isEditable()";

    private String mParam1;
    private OnFragmentInteractionListener mListener;
    private List<TimeEntryToSave> dayTimeEntriesToSave;
    private String timeTaskIdWithNote;
    private RecyclerView recyclerView;
    private SmartLogDayRecyclerViewAdapter adapter;
    private boolean readyToListenChanges = false;
    private LogTimeActivity logTimeActivity;
    private  Activity activity;


    public static LogDayTimeFragment newInstance(List<TimeEntryToSave> dayTimeEntriesToSave, String timeTaskIdForSaveNote, boolean timePeriodIsEditable) {
        LogDayTimeFragment fragment = new LogDayTimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DAY_LOGS, (Serializable) dayTimeEntriesToSave);
        args.putString(ARG_PARAM_TIME_TASK_ID, timeTaskIdForSaveNote);
        args.putBoolean(ARG_PARAM_TIME_PERIOD_IS_EDITABLE, timePeriodIsEditable);
        fragment.setArguments(args);
        return fragment;
    }

    public LogDayTimeFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            logTimeActivity= (LogTimeActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + LogDayTimeFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_log_time, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        DataSource dataSource = getDataSource();
        dayTimeEntriesToSave = dataSource.timeEntriesToSave;
        timeTaskIdWithNote = dataSource.timeTaskIdWithNote;
        adapter = new SmartLogDayRecyclerViewAdapter(logTimeActivity,dayTimeEntriesToSave, timeTaskIdWithNote, dataSource.timePeriodIsEditable,logTimeActivity);
        adapter.SetOnItemChangeListener(new SmartLogDayRecyclerViewAdapter.OnItemChangeListener() {
            @Override
            public void onItemChanged() {
                LogDayTimeFragment.this.onItemChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setScrollContainer(true);

//        /*Hiding the keyboard when the recycler view is scrolling. */
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                KeyboardUtil.htryToHideKeyboard(getActivity());
//                return false;
//
//            }
//        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        readyToListenChanges = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        readyToListenChanges = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onItemChanged() {
        if (readyToListenChanges && mListener != null) {
            mListener.onItemChanged();
        }
    }
    public interface OnFragmentInteractionListener {
        void onItemChanged();
        void recyclerViewScrolled(int dx,int dy);
    }

    public DataSource getDataSource() {
        List<TimeEntryToSave> timeEntriesToSave = Collections.EMPTY_LIST;
        String timeTaskIdWithNote = "";
        boolean timePeriodIsEditable = true;

        if (getArguments() != null) {
            timeEntriesToSave = (List<TimeEntryToSave>) getArguments().getSerializable(ARG_PARAM_DAY_LOGS);
            timeTaskIdWithNote = getArguments().getString(ARG_PARAM_TIME_TASK_ID);
            timePeriodIsEditable = getArguments().getBoolean(ARG_PARAM_TIME_PERIOD_IS_EDITABLE);
        }
        return new DataSource(timeEntriesToSave, timeTaskIdWithNote, timePeriodIsEditable);
    }

    private class DataSource {
        private List<TimeEntryToSave> timeEntriesToSave;
        private String timeTaskIdWithNote;
        private boolean timePeriodIsEditable;

        public DataSource(List<TimeEntryToSave> timeEntriesToSave, String timeTaskIdWithNote, boolean timePeriodIsEditable) {
            this.timeEntriesToSave = timeEntriesToSave;
            this.timeTaskIdWithNote = timeTaskIdWithNote;
            this.timePeriodIsEditable = timePeriodIsEditable;
        }
    }
}
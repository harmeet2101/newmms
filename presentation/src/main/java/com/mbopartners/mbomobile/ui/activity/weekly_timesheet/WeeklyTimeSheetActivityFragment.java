package com.mbopartners.mbomobile.ui.activity.weekly_timesheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mbopartners.mbomobile.rest.model.response.TimePeriod;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.TimeLogDescriptor;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.model.WeeklyTimeSheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.FontController;
import com.mbopartners.mbomobile.ui.util.MboFormatter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.com.mobidev.android.framework.ui.view.recyclerview.DividerItemDecoration;

public class WeeklyTimeSheetActivityFragment extends Fragment implements FontController{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    TextView companyNameTextView;
    TextView workOrderNameTextView;
    TextView timePeriodTextView;
    TextView totalSumTextView;
    TextView toatlSumUnitsTextView;
    TextView submitTextview;
    TextView messageTextview;
    TextView dialog_submitTextview,dialog_cancelTextview,dialog_heading;
    RecyclerView timeSheetRecyclerView;
    private WeeklyTimeSheetRecyclerViewAdapter adapter;
    private View includeView;
    private View bottonbarsumitlayout;
    DataModel dataModel = new DataModel();
    Typeface typeface_bold;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyTimeSheetActivityFragment newInstance(String param1, String param2) {
        WeeklyTimeSheetActivityFragment fragment = new WeeklyTimeSheetActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WeeklyTimeSheetActivityFragment() {
        int i = 1;
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            iSubmittableListener=(ISubmittableListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + WeeklyTimeSheetActivityFragment.OnFragmentInteractionListener.class.getSimpleName());
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
        View fragmentRootView = inflater.inflate(R.layout.fragment_weekly_time_sheet, container, false);
        companyNameTextView = (TextView) fragmentRootView.findViewById(R.id.mbo_weekly_timesheet_CompanyName_TextView);
        workOrderNameTextView = (TextView) fragmentRootView.findViewById(R.id.mbo_weekly_timesheet_WorkOrderName_TextView);
        timePeriodTextView = (TextView) fragmentRootView.findViewById(R.id.mbo_weekly_timesheet_TimePeriodName_TextView);
        View include=fragmentRootView.findViewById(R.id.bottom_total_layout);
        totalSumTextView = (TextView) include.findViewById(R.id.mbo_weekly_timesheet_summary_TextView);
        includeView=include.findViewById(R.id.bottonbar01);
        submitTextview=(TextView)includeView.findViewById(R.id.submittext);
        bottonbarsumitlayout=include.findViewById(R.id.bottonbarsumitlayout);
        toatlSumUnitsTextView=(TextView)include.findViewById(R.id.mbo_timesheet_totalhours_sign_TextView);

        timeSheetRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);
        typeface_bold=Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Bold.ttf");

        companyNameTextView.setTypeface(typeface_bold);
        totalSumTextView.setTypeface(typeface_bold);
        toatlSumUnitsTextView.setTypeface(typeface_bold);

        timeSheetRecyclerView.setHasFixedSize(true);
        timeSheetRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

//        getWeeklyTimeSheetData();
        adapter = new WeeklyTimeSheetRecyclerViewAdapter(dataModel.timePeriodItems, dataModel.timeEntryAllowed,getActivity());
        adapter.SetOnItemClickListener(new WeeklyTimeSheetRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Date date) {
                onDaySelected(date);
            }
        });

//        timeSheetRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        timeSheetRecyclerView.setAdapter(adapter);
        timeSheetRecyclerView.setScrollContainer(true);
        includeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        submitTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });
        return fragmentRootView;
    }

    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.mboAlertTheme);
        /*LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_submit_dialogbox, null);
        builder.setView(view);

        messageTextview=(TextView)view.findViewById(R.id.msg);
        dialog_submitTextview=(TextView)view.findViewById(R.id.submittext);
        dialog_cancelTextview=(TextView)view.findViewById(R.id.canceltext);
        dialog_heading=(TextView)view.findViewById(R.id.heading);
        setFont((View)dialog_heading,(View)dialog_submitTextview,(View)dialog_cancelTextview,(View)messageTextview);
        dialog_cancelTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        dialog_submitTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSubmitTimeSheet(true);
            }
        });*/
        builder.setTitle("Submit Timesheet?");
        builder.setMessage(R.string.mbo_submit_dialog_text);
        builder.setCancelable(true);

        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mListener.onSubmitTimeSheet(true);
                alertDialog.dismiss();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog=builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private AlertDialog alertDialog;
    @Override
    public void onResume() {
        super.onResume();

        getWeeklyTimeSheetData();
        adapter.updateDataSource(dataModel.timePeriodItems, dataModel.timeEntryAllowed);

        companyNameTextView.setText(dataModel.companyName);
        workOrderNameTextView.setText(dataModel.workOrderName);
        timePeriodTextView.setText(dataModel.timePeriodDates);
        totalSumTextView.setText(MboFormatter.Hours.formatHours(dataModel.totalSum));
       /* if(dataModel.timeEntryAllowed && dataModel.timePeriodItems.get(dataModel.timePeriodItems.size()-1).isDayEditable()
                && dataModel.timePeriodItems.get(dataModel.timePeriodItems.size()-1).isParentTmePeriodEditable())
        {
            setEnable(true);
        }else
            setEnable(false);*/
        if(iSubmittableListener.isSubmittable())
        {
            submitTextview.setEnabled(true);
            includeView.setEnabled(true);
            includeView.setBackgroundColor(getResources().getColor(R.color.mbo_theme_blue_primary));
        }else{
            submitTextview.setEnabled(false);
            includeView.setEnabled(false);
            includeView.setBackgroundColor(getResources().getColor(R.color.mbo_lght_blue_disable));
        }
        ;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getWeeklyTimeSheetData() {
        if (mListener!=null) {
            TimeLogDescriptor timeLogDescriptor = mListener.getTimeLogDescriptor();
            TimePeriod timePeriod = timeLogDescriptor.getTimePeriod();
            dataModel.timePeriodItems = Converter.timeLogDescriptor_2_WeeklyTimeSheet(timeLogDescriptor);
            dataModel.companyName = timeLogDescriptor.getCompany().getName();
            dataModel.workOrderName = timeLogDescriptor.getWorkOrderName();
            dataModel.timePeriodDates = DateUtil.getFullFormattedPeriodAsString(timePeriod.getStartDate(), timePeriod.getEndDate());
            dataModel.totalSum = Converter.countHours(timePeriod.getTimeEntries());
            dataModel.timeEntryAllowed = timeLogDescriptor.getTimeEntryAllowed();
        }
    }

    public void onDaySelected(Date date) {
        if (mListener != null) {
            mListener.onDaySelected(date);
        }
    }

    public interface OnFragmentInteractionListener {
        TimeLogDescriptor getTimeLogDescriptor();
        void onDaySelected(Date date);
        void onSubmitTimeSheet(boolean flag);
    }

    class DataModel {
        List<WeeklyTimeSheetItem> timePeriodItems;
        String companyName;
        String workOrderName;
        String timePeriodDates;
        Double totalSum;
        boolean timeEntryAllowed;

        public DataModel() {
            this.timePeriodItems = Collections.EMPTY_LIST;
            this.companyName = "Loading...";
            this.workOrderName = "Loading...";
            this.timePeriodDates = "Loading...";
            this.totalSum = 0.0;
            this.timeEntryAllowed = false;
        }
    }

    public void setEnable(boolean flag)
    {
        if(flag)
        {
            //submitTextview.setEnabled(flag);
            includeView.setBackgroundColor(getResources().getColor(R.color.mbo_theme_blue_primary));
        }else{
            //submitTextview.setEnabled(flag);
            includeView.setBackgroundColor(getResources().getColor(R.color.mbo_lght_blue_disable));
        }

    } public void setFont(View...args)
    {
        TextView headingtextView1=(TextView)args[0];
        TextView canceltextView1=(TextView)args[1];
        TextView submittextView1=(TextView)args[2];
        TextView msgTextview=(TextView)args[3];
        Typeface typeface_regular=Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Regular.ttf");
        Typeface typeface_bold=Typeface.createFromAsset(getActivity().getAssets(),"font/Roboto-Bold.ttf");
        canceltextView1.setTypeface(typeface_regular);
        submittextView1.setTypeface(typeface_regular);
        headingtextView1.setTypeface(typeface_bold);
        messageTextview.setTypeface(typeface_regular);
    }

    private ISubmittableListener iSubmittableListener;
    public interface  ISubmittableListener{
        boolean isSubmittable();
    }

}

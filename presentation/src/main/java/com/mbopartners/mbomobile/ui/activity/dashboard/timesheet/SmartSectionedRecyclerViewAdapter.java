package com.mbopartners.mbomobile.ui.activity.dashboard.timesheet;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.TimeSheetItem;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.com.mobidev.android.framework.util.UiUtils;

public class SmartSectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FontController{

    private List<ItemHolder> itemHolderList;
    private OnItemClickListener mItemClickListener;
    private IAddTimeCallbackListener addTimeCallbackListener;
    private Context context;
    private TimePageFragment timePageFragment;
    public SmartSectionedRecyclerViewAdapter(List<TimeSheetItem> timeSheet,Context context,TimePageFragment timePageFragment) {
        setDataSource(timeSheet);
        this.context=context;
        this.addTimeCallbackListener= (IAddTimeCallbackListener) context;
        this.timePageFragment=timePageFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                fillParent(parent, view);
                return new FullStubViewHolder(view);
            }
            case 2: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dashboard_time, parent, false);
                return new SmartViewHolder(view);
            }
            case 3: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            }
            case 4: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empy_data_section, parent, false);
                return new SectionStubViewHolder(view);
            }
            case 5: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list, parent, false);
                fillParent(parent, view);
                return new FullStubViewHolder(view);
            }
            default: {
                throw new IllegalStateException("Unknown ViewHolder type");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder element = itemHolderList.get(position);
        switch (element.viewType) {
            case LOADING: {
                bindLoadingViewHolder(holder, element);
                break;
            }
            case DATA: {
                bindDataViewHolder(holder, element);
                break;
            }
            case SECTION_HEADER: {
                bindSectionViewHolder(holder, element);
                break;
            }
            case DATA_STUB: {
                bindStubViewHolder(holder, element);
                break;
            }
            case FULL_STUB: {
                bindFullStubViewHolder(holder, element);
                break;
            }
            default: {
                throw new IllegalStateException("Unknown ViewHolder type");

            }
        }
    }

    @Override
    public int getItemCount() {
        return itemHolderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemHolderList.get(position).viewType.typeValue;
    }

    @Override
    public long getItemId(int position) {
        return itemHolderList.get(position).itemId;
    }

    public void setDataSource(List<TimeSheetItem> timeSheet) {
        itemHolderList = new ArrayList<>();
        int nonDataItemCounter = Integer.MAX_VALUE;

        if (timeSheet == null) {
            addLoadingItem();
            nonDataItemCounter--;
            return;
        } else if (timeSheet.size() == 0) {
            addFullDataStub();
            nonDataItemCounter--;
            return;
        }

        addSectionHeader("Current", nonDataItemCounter);
        nonDataItemCounter--;

        if (isSectionEmpty(timeSheet, true)) {
            addSectionDataStub("No current timesheets", nonDataItemCounter);
            nonDataItemCounter--;
        } else {
            for (TimeSheetItem timePeriod : timeSheet) {
                if (timePeriod.getTimePeriodCurrent()) {
                    addDataItem(timePeriod);

                }
            }
        }

        addSectionHeader("Previous", nonDataItemCounter);
        nonDataItemCounter--;

        if (isSectionEmpty(timeSheet, false)) {
            addSectionDataStub("No previous timesheets", nonDataItemCounter);
            nonDataItemCounter--;
        } else {
            for (TimeSheetItem timePeriod : timeSheet) {
                if (!timePeriod.getTimePeriodCurrent()) {
                    addDataItem(timePeriod);

                }
            }
        }
    }

    public void updateDataSource(List<TimeSheetItem> timeSheet) {
        setDataSource(timeSheet);
        notifyDataSetChanged();
    }

    private boolean isSectionEmpty(List<TimeSheetItem> timeSheet, boolean value) {
        for (TimeSheetItem timePeriod : timeSheet) {
            if (timePeriod.getTimePeriodCurrent() == value) {
                return false;
            }
        }
        return true;
    }

    private void addDataItem(TimeSheetItem timePeriod) {
        ItemHolder svh = new ItemHolder();
        svh.viewType = SmartViewType.DATA;
        svh.timeSheetItem = timePeriod;
        svh.itemId = RecyclerView.NO_ID;
        itemHolderList.add(svh);
    }

    private void addSectionHeader(String name, long itemId) {
        HeaderItem headerItem = new HeaderItem(name);
        ItemHolder svh = new ItemHolder();
        svh.viewType = SmartViewType.SECTION_HEADER;
        svh.headerItem = headerItem;
        svh.itemId = itemId;
        itemHolderList.add(svh);
    }

    private void addSectionDataStub(String message, long itemId) {
        DataStubItem dataStubItem = new DataStubItem(message);
        ItemHolder svh = new ItemHolder();
        svh.viewType = SmartViewType.DATA_STUB;
        svh.dataStubItem = dataStubItem;
        svh.itemId = itemId;
        itemHolderList.add(svh);
    }
    private void addLoadingItem() {
        ItemHolder svh = new ItemHolder();
        svh.viewType = SmartViewType.LOADING;
        itemHolderList.add(svh);
    }

    private void addFullDataStub() {
        ItemHolder svh = new ItemHolder();
        svh.viewType = SmartViewType.FULL_STUB;
        itemHolderList.add(svh);
    }


    private void bindLoadingViewHolder(RecyclerView.ViewHolder holder, ItemHolder element) {
    }

    private void bindDataViewHolder(RecyclerView.ViewHolder holder, ItemHolder element) {
        SmartViewHolder smartViewHolder = (SmartViewHolder) holder;
        smartViewHolder.companyNameTextView.setText(element.timeSheetItem.getCompanyName());
        smartViewHolder.workOrderNameTextView.setText(element.timeSheetItem.getWorkOrderName());
        smartViewHolder.timePeriodTextView.setText(element.timeSheetItem.getPeriodAsString());
        smartViewHolder.hoursTotalTextView.setText(element.timeSheetItem.getTotalHours());

        if (element.timeSheetItem.isViewable()) {
            UiUtils.disableEnableTreeLeafs(element.timeSheetItem.isEditable(), smartViewHolder.itemView);
        } else {
            UiUtils.disableEnableViewTree(false, smartViewHolder.itemView);
        }

    }

    private void bindSectionViewHolder(RecyclerView.ViewHolder holder, ItemHolder element) {
        ((SectionHeaderViewHolder) holder).title.setText(element.headerItem.name);
    }

    private void bindStubViewHolder(RecyclerView.ViewHolder holder, ItemHolder element) {
        ((SectionStubViewHolder) holder).title.setText(element.dataStubItem.message);
    }

    private void bindFullStubViewHolder(RecyclerView.ViewHolder holder, ItemHolder element) {
    }

    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }

    private class HeaderItem {
        String name;

        public HeaderItem(String name) {
            this.name = name;
        }
    }

    private class DataStubItem {
        String message;

        public DataStubItem(String message) {
            this.message = message;
        }
    }

    private enum SmartViewType {
        LOADING(1),
        DATA(2),
        SECTION_HEADER(3),
        DATA_STUB(4),
        FULL_STUB(5);

        public int typeValue;

        SmartViewType(int viewType) {
            this.typeValue = viewType;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(String workOrderId, String timePeriodId,boolean isSubmittable);
    }

    public interface IAddTimeCallbackListener{
        public void callBackLogTime(String workOrderId,String timePeriodId,Date startDate,int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private class ItemHolder {
        TimeSheetItem timeSheetItem;
        HeaderItem headerItem;
        DataStubItem dataStubItem;

        SmartViewType viewType;
        long itemId;
    }

    public class SmartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View itemView;
        public final TextView companyNameTextView;
        public final TextView workOrderNameTextView;
        public final TextView timePeriodTextView;
        public final TextView hoursTotalTextView;
        public final TextView addTimeButton;
        public SmartViewHolder(View view) {
            super(view);
            this.itemView = view;
            this.companyNameTextView = (TextView) view.findViewById(R.id.company_name_TextView);
            this.workOrderNameTextView = (TextView) view.findViewById(R.id.work_order_name_TextView);
            this.timePeriodTextView = (TextView) view.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.hoursTotalTextView = (TextView) view.findViewById(R.id.mbo_timesheet_totalhours_period_TextView);
            View includeview=view.findViewById(R.id.includeview);
            this.addTimeButton=(TextView)includeview.findViewById(R.id.addTime);
            setFont((View)companyNameTextView,(View)addTimeButton);
            view.setOnClickListener(this);
            this.addTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ItemHolder element = itemHolderList.get(position);
                    switch (element.viewType) {
                        case DATA: {
                            if(addTimeCallbackListener!=null){
                                if(position==1)
                                {
                                    position=position-1;
                                }else
                                position=position-2;
                                addTimeCallbackListener.callBackLogTime(element.timeSheetItem.getWorkOrderId(), element.timeSheetItem.getTimePeriodId(),element.timeSheetItem.getTimePeriodStartDate(),position);
                            }
                        }
                    }

                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ItemHolder element = itemHolderList.get(position);
            switch (element.viewType) {
                case DATA: {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(element.timeSheetItem.getWorkOrderId(), element.timeSheetItem.getTimePeriodId(),element.timeSheetItem.getTimePeriodSubmittable());
                    }
                    break;
                }
            }

        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public SectionHeaderViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.section_text);
            setFont((View)title);

        }
    }

    public static class SectionStubViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public SectionStubViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.empty_section_TextView);
        }
    }

    @Override
    public void setFont(View... args) {

        for(int i=0;i<args.length;i++)
        {
            TextView textView1=(TextView)args[i];
            Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"font/Roboto-Medium.ttf");
            textView1.setTypeface(typeface1);
        }
    }

    public static class FullStubViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public FullStubViewHolder(View view) {
            super(view);
        }
    }
}

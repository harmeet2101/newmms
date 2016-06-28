package com.mbopartners.mbomobile.ui.activity.weekly_timesheet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.WeeklyTimeSheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.MboFormatter;
import com.mbopartners.mbomobile.ui.util.WorkTrackingUiUtils;

import java.util.Date;
import java.util.List;

public class WeeklyTimeSheetRecyclerViewAdapter extends RecyclerView.Adapter<WeeklyTimeSheetRecyclerViewAdapter.ViewHolder> {
    List<WeeklyTimeSheetItem> weeklyTimeSheetItemList;
    private OnItemClickListener mItemClickListener;

    private boolean timeEntryAllowed;
    private Context context;
    public WeeklyTimeSheetRecyclerViewAdapter(List<WeeklyTimeSheetItem> weeklyTimeSheetItemList, boolean timeEntryAllowed,Context context) {
        this.weeklyTimeSheetItemList = weeklyTimeSheetItemList;
        this.timeEntryAllowed = timeEntryAllowed;
        this.context=context;

    }

    @Override
    public WeeklyTimeSheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_weekly_time_sheet, parent, false);
        return new WeeklyTimeSheetRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeeklyTimeSheetRecyclerViewAdapter.ViewHolder holder, int position) {
        WeeklyTimeSheetItem dayRow = weeklyTimeSheetItemList.get(position);

        holder.dayOfWeekTextView.setText(DateUtil.getDayOfWeek(dayRow.getDate()));
        holder.shortDateTextView.setText(DateUtil.getShortDate(dayRow.getDate()));
        holder.totalHoursOfDayTextView.setText(MboFormatter.Hours.formatHours(dayRow.getTotalHours()));

        boolean dayEditable = false;


        /*if (dayRow.isNotInFuture()) {
            dayEditable = timeEntryAllowed && dayRow.isDayEditable() && dayRow.isParentTmePeriodEditable();

        }*/
        dayEditable = timeEntryAllowed && dayRow.isDayEditable() && dayRow.isParentTmePeriodEditable();
        if(dayEditable)
            WorkTrackingUiUtils.disableEnableTreeLeafs(dayEditable, holder.itemView, context);
        else {
            // We should not be able view details in future
            WorkTrackingUiUtils.disableEnableViewTree(false, holder.itemView, context);
        };
    }

    @Override
    public int getItemCount() {
        return weeklyTimeSheetItemList.size();
    }

    public void updateDataSource(List<WeeklyTimeSheetItem> fields, boolean timeEntryAllowed) {
        this.weeklyTimeSheetItemList = fields;
        this.timeEntryAllowed = timeEntryAllowed;
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Date date);
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View itemView;
        public TextView dayOfWeekTextView;
        public TextView shortDateTextView;
        public TextView totalHoursOfDayTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.dayOfWeekTextView = (TextView) itemView.findViewById(R.id.day_of_week_TextView);
            this.shortDateTextView = (TextView) itemView.findViewById(R.id.date_TextView);
            this.totalHoursOfDayTextView = (TextView) itemView.findViewById(R.id.sum_hours_on_day_TextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            WeeklyTimeSheetItem element = weeklyTimeSheetItemList.get(position);
            Log.d("date",""+element.getDate());
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(element.getDate());
            }
        }
    }
}

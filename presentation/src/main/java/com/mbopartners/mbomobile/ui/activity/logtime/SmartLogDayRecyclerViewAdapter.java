package com.mbopartners.mbomobile.ui.activity.logtime;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.TimeEntryToSave;
import com.mbopartners.mbomobile.ui.converter.Converter;
import com.mbopartners.mbomobile.ui.util.DecimalDigitsInputFilter;
import com.mbopartners.mbomobile.ui.util.MboFormatter;
import com.mbopartners.mbomobile.ui.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import ua.com.mobidev.android.framework.ui.keyboard.KeyboardUtil;

public class    SmartLogDayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SmartLogDayRecyclerViewAdapter.class.getSimpleName();
    private static final int ITEM_NAV_HEADER = 0;
    private static final int ITEM_HOURS_TITLE = 1;
    private static final int ITEM_HOURS = 2;
    private static final int ITEM_NOTE_TITLE = 3;
    private static final int ITEM_NOTE = 4;
    private static final int ITEM_SAVE_BUTTON=5;

    private List<TimeEntryToSave> timeEntriesToSave;
    private OnItemChangeListener mItemChangeListener;
    private TimeEntryToSave entryWithNote;
    private boolean timePeriodIsEditable;
    private boolean dayEditable;
    private Context context;
    private ISaveTimeLogListener iSaveTimeLogListener;
    private LogTimeActivity logActivity;

    public SmartLogDayRecyclerViewAdapter(Activity activity, List<TimeEntryToSave> timeEntriesToSave, String timeTaskIdForSavingNote, boolean timePeriodIsEditable, LogTimeActivity logTimeActivity) {
        this.timeEntriesToSave = timeEntriesToSave;
        this.entryWithNote = Converter.findTimeEntryToSaveOnTaskId(timeEntriesToSave, timeTaskIdForSavingNote);
        this.timePeriodIsEditable = timePeriodIsEditable;
        this.dayEditable = isDayEditable(timeEntriesToSave);
        this.iSaveTimeLogListener= (ISaveTimeLogListener) logTimeActivity;
        logActivity = (LogTimeActivity) activity;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return  ITEM_HOURS_TITLE;
        }

        if (position > 0 && position < timeEntriesToSave.size() + 1) {
            return  ITEM_HOURS;
        }

        if (position == timeEntriesToSave.size() + 1) {
            return ITEM_NOTE_TITLE;
        }
        if (position == timeEntriesToSave.size() + 2) {
            return  ITEM_NOTE;
        }
        if (position == timeEntriesToSave.size() + 3) {
            return  ITEM_SAVE_BUTTON;
        }

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HOURS_TITLE: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_log_day_section_header_updated, parent, false);
                view.setPadding(0, (int) SizeUtils.convertDpToPixel(25, view.getContext()),0,0);
                return new SectionHeaderViewHolder(view);
            }
            case ITEM_HOURS: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_day_hours, parent, false);
                return new HoursViewHolder(view);
            }
            case ITEM_NOTE_TITLE: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notes_header, parent, false);
                return new SectionHeaderViewHolder(view);
            }
            case ITEM_NOTE: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_day_note, parent, false);
                return new NoteViewHolder(view);
            }
            case ITEM_SAVE_BUTTON:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_weekly_save_button, parent, false);
                return new SaveButtonViewHolder(view);
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case ITEM_HOURS_TITLE: {
                SectionHeaderViewHolder hoursSectionTitle = (SectionHeaderViewHolder) holder;
	            hoursSectionTitle.sectionTitleTextView.setTextColor(hoursSectionTitle.itemView.getResources().getColor(R.color.mbo_color_secondary_text));
                hoursSectionTitle.sectionTitleTextView.setText("Hours");
                hoursSectionTitle.sectionImageView.setImageResource(R.drawable.hours_mdpi);
                break;
            }
            case ITEM_HOURS: {
                HoursViewHolder hoursItem = (HoursViewHolder) holder;
                int itemPosition = convertAdapterPosToDataSourcePos(position);
                TimeEntryToSave timeEntryToSave = timeEntriesToSave.get(itemPosition);
                hoursItem.timeTaskTextView.setVisibility(View.GONE);
                //hoursItem.timeTaskTextView.setText(timeEntryToSave.getTimeTaskName());
                if(itemPosition==1)
                {
                    hoursItem.timeTaskTextView.setVisibility(View.VISIBLE);
                    hoursItem.timeTaskTextView.setText(timeEntryToSave.getTimeTaskName());
                    //hoursItem.hoursEditText.setVisibility(View.GONE);
                    //hoursItem.view.setVisibility(View.GONE);
                }
                if (timeEntryToSave.getTimeEntry().getHours() == 0.f) {
	                hoursItem.hoursEditText.setText(null);
                } else {
	                hoursItem.hoursEditText.setText(MboFormatter.Hours.formatHours(timeEntryToSave.getTimeEntry().getHours()));
                }
                if (timePeriodIsEditable && dayEditable) {
                    hoursItem.hoursEditText.setEnabled(true);
                } else {
                    hoursItem.hoursEditText.setEnabled(false);
                }
                break;
            }
            case ITEM_NOTE_TITLE: {
                SectionHeaderViewHolder noteSectionTitle = (SectionHeaderViewHolder) holder;
	            noteSectionTitle.sectionTitleTextView.setTextColor(noteSectionTitle.itemView.getResources().getColor(R.color.mbo_color_secondary_text));
                noteSectionTitle.sectionTitleTextView.setText("Notes");
                noteSectionTitle.sectionImageView.setImageResource(R.drawable.notes_mdpi);
                break;
            }
            case ITEM_NOTE: {
                NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                noteViewHolder.noteEditText.setText(entryWithNote.getTimeEntry().getNote());

                if (timePeriodIsEditable && dayEditable) {
                    noteViewHolder.noteEditText.setEnabled(true);
                } else {
                    noteViewHolder.noteEditText.setEnabled(false);
                }
                break;
            }
            case ITEM_SAVE_BUTTON:
            {
                SaveButtonViewHolder saveButtonViewHolder=(SaveButtonViewHolder)holder;
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return timeEntriesToSave.size() + 4;
    }

    public void SetOnItemChangeListener(final OnItemChangeListener mItemChangeListener) {
        this.mItemChangeListener = mItemChangeListener;
    }

    public interface OnItemChangeListener {
        void onItemChanged();
    }

    private boolean isDayEditable(List<TimeEntryToSave> timeEntriesToSave) {
        List<TimeEntry> timeEntries = new ArrayList<>(timeEntriesToSave.size());
        for (TimeEntryToSave entryToSave : timeEntriesToSave) {
            timeEntries.add(entryToSave.getTimeEntry());
        }
        return Converter.checkIsEditableTimeEntries(timeEntries);
    }

    public class HoursViewHolder extends RecyclerView.ViewHolder implements TextWatcher, View.OnFocusChangeListener, View.OnClickListener{
        private TextView timeTaskTextView;
        private EditText hoursEditText;
        private View view;
        public HoursViewHolder(View itemView) {
            super(itemView);
            this.timeTaskTextView = (TextView) itemView.findViewById(R.id.time_task_name_TextView);
            this.hoursEditText = (EditText) itemView.findViewById(R.id.hours_EditText);
            this.view=itemView.findViewById(R.id.view);
            this.hoursEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
            this.hoursEditText.addTextChangedListener(this);
            this.hoursEditText.setOnFocusChangeListener(this);
            this.timeTaskTextView.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            double newValue = 0.0d;
            try {
                newValue = Double.parseDouble(s.toString());
            } catch (Throwable e) {
            } finally {
                TimeEntryToSave timeEntryToSave = timeEntriesToSave.get(convertAdapterPosToDataSourcePos(getAdapterPosition()));
                timeEntryToSave.getTimeEntry().setHours(newValue);
            }
        }
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
//
//            if(!hasFocus){
//                String userInput = hoursEditText.getText().toString();
//
//                int dotPos = -1;
//
//                for (int i = 0; i < userInput.length(); i++) {
//                    char c = userInput.charAt(i);
//                    if (c == '.') {
//                        dotPos = i;
//                    }
//                }
//
//                if (dotPos == -1&& !userInput.equals("")){
//                    hoursEditText.setText(userInput + ".00");
//                }else if(dotPos == -1&& userInput.equals(""))
//                    hoursEditText.setText(userInput + "0.00");
//                else if(dotPos==userInput.length()-1 && !userInput.equals(""))
//                    hoursEditText.setText("0"+userInput + "00");
//                else if(dotPos==userInput.length()-2 && !userInput.equals(""))
//                    hoursEditText.setText(userInput + "0");
//                this.hoursEditText.clearFocus();
//            }
        }
        @Override
        public void onClick(View v) {
            KeyboardUtil.htryToHideKeyboard(logActivity);
        }
    }

    private int convertAdapterPosToDataSourcePos(int positionInRecyclerView) {
        int positionInAdapter = positionInRecyclerView - 1;
        return positionInAdapter;
    }

    private class SectionHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView sectionTitleTextView;
        private ImageView sectionImageView;
        private SectionHeaderViewHolder(View itemView) {
            super(itemView);
            this.sectionTitleTextView = (TextView) itemView.findViewById(R.id.section_label_TextView);
            this.sectionImageView=(ImageView)itemView.findViewById(R.id.image);
            this.sectionTitleTextView.setOnClickListener(this);
            this.sectionImageView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            KeyboardUtil.htryToHideKeyboard(logActivity);
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        private EditText noteEditText;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.noteEditText = (EditText) itemView.findViewById(R.id.day_noteEditText);
            this.noteEditText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            entryWithNote.getTimeEntry().setNote(s.toString());
        }
    }
    private class NavHeaderHolder extends RecyclerView.ViewHolder
    {
        public NavHeaderHolder(View view)
        {
            super(view);
        }
    }

    private class SaveButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView saveTextview;
        public SaveButtonViewHolder(View itemView) {
            super(itemView);
            this.saveTextview=(TextView)itemView.findViewById(R.id.saveButton);
            this.saveTextview.setOnClickListener(this);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.htryToHideKeyboard(logActivity);
                }
            });
        }

        @Override
        public void onClick(View v) {
            iSaveTimeLogListener.saveTime();
        }
    }

    public interface ISaveTimeLogListener{
        void saveTime();
    }
}

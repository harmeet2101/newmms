package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;

public class BaseFieldGovernor {
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field) {
        BaseFieldViewHolder baseFieldViewHolder = (BaseFieldViewHolder) holder;
        Log.d("title", field.getName());
        baseFieldViewHolder.fieldTitleTextView.setText(field.getSpannableTitle());
    }

}

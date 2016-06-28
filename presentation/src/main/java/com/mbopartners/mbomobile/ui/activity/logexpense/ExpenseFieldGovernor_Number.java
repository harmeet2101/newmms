package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;

public class ExpenseFieldGovernor_Number extends BaseFieldGovernor {

    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
        super.onBindViewHolder(holder, field);
        ExpenseFieldViewHolder_Number holder_Number = (ExpenseFieldViewHolder_Number) holder;

        holder_Number.fieldValueEditText.setText(value != null ? value : "");
        if (editable) {
            holder_Number.fieldValueEditText.setEnabled(true);
            holder_Number.fieldValueEditText.setTextColor(Color.BLACK);
        } else {
            holder_Number.fieldValueEditText.setEnabled(false);
            holder_Number.fieldValueEditText.setTextColor(Color.GRAY);
        }
    }

}


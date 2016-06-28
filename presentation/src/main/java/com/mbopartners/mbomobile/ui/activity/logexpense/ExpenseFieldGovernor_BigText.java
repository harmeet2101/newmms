package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;

public class ExpenseFieldGovernor_BigText extends BaseFieldGovernor {

    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
        super.onBindViewHolder(holder, field);
        ExpenseFieldViewHolder_BigText holder_BigText = (ExpenseFieldViewHolder_BigText) holder;

        holder_BigText.fieldValueEditText.setText(value != null ? value : "");
        if (editable) {
            holder_BigText.fieldValueEditText.setEnabled(true);
            holder_BigText.fieldValueEditText.setTextColor(Color.BLACK);
        } else {
            holder_BigText.fieldValueEditText.setEnabled(false);
            holder_BigText.fieldValueEditText.setTextColor(Color.GRAY);
        }
    }

}

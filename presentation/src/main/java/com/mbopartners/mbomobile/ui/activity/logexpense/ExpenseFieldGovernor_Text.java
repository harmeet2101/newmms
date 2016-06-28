package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;

public class ExpenseFieldGovernor_Text extends BaseFieldGovernor {

    static ExpenseFieldViewHolder_Text holder_Text;
    private static Context context;
    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable,Context context) {
        super.onBindViewHolder(holder, field);
        holder_Text = (ExpenseFieldViewHolder_Text) holder;
        this.context=context;
        holder_Text.fieldValueEditText.setText(value != null ? value : "");
        if (editable) {
            holder_Text.fieldValueEditText.setEnabled(true);
            holder_Text.fieldValueEditText.setTextColor(Color.BLACK);
        } else {
            holder_Text.fieldValueEditText.setEnabled(false);
            holder_Text.fieldValueEditText.setTextColor(Color.GRAY);
        }
    }



}

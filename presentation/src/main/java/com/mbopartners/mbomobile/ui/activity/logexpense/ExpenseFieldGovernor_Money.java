package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.ui.util.MboFormatter;

public class ExpenseFieldGovernor_Money extends BaseFieldGovernor {

    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
        super.onBindViewHolder(holder, field);
        ExpenseFieldViewHolder_Money holder_Money = (ExpenseFieldViewHolder_Money) holder;

        if (value == null) {
           // holder_Money.fieldValueEditText.setText("$0.00");
        } else {

            holder_Money.fieldValueEditText.setText((value));
        }

        if (editable) {
            holder_Money.fieldValueEditText.setEnabled(true);
            holder_Money.fieldValueEditText.setTextColor(Color.BLACK);
        } else {
            holder_Money.fieldValueEditText.setEnabled(false);
            holder_Money.fieldValueEditText.setTextColor(Color.GRAY);
        }


    }

}

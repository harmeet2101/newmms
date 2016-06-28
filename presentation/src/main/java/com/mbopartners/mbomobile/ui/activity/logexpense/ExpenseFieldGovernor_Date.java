package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.Date;

public class ExpenseFieldGovernor_Date extends BaseFieldGovernor {

    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
        super.onBindViewHolder(holder, field);
        ExpenseFieldViewHolder_Date holder_Date = (ExpenseFieldViewHolder_Date) holder;

        if (value != null) {
            Date dateFromServer = DateUtil.parseFrom_yyyymmdd(value);
            String dateToShow = "";
            if (dateFromServer != null) {
                dateToShow = DateUtil.getDateFormatted_mmddyyyy(dateFromServer);
            }
            holder_Date.fieldValueEditText.setText(dateToShow);
        } else {
            holder_Date.fieldValueEditText.setText("");
        }

        if (editable) {
            holder_Date.fieldValueEditText.setEnabled(true);
            holder_Date.fieldValueEditText.setTextColor(Color.BLACK);
        } else {
            holder_Date.fieldValueEditText.setEnabled(false);
            holder_Date.fieldValueEditText.setTextColor(Color.GRAY);
        }
    }

}

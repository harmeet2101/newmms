package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

public class BaseFieldViewHolder extends RecyclerView.ViewHolder {

    protected View itemView;
    protected TextView fieldTitleTextView;
    ExpenseForEdit expenseForEdit;

    public BaseFieldViewHolder(View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView);
        this.itemView = itemView;
        this.expenseForEdit = expenseForEdit;
        this.fieldTitleTextView = (TextView) itemView.findViewById(R.id.expenseFieldTitle_TextView);
    }
}

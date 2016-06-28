package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mbopartners.mbomobile.rest.model.response.Receipt;

import java.util.List;

public class ExpenseReceiptsListGovernor {

    public void onBindViewHolder(RecyclerView.ViewHolder holder, List<Receipt> receipts, LogExpenseActivityFragment.OnFragmentInteractionListener receiptActionListener, boolean editable, Context context) {
        ExpenseReceiptsViewHolder expenseReceiptsViewHolder = (ExpenseReceiptsViewHolder) holder;
        expenseReceiptsViewHolder.setData(receipts, receiptActionListener,context);

        if (editable) {
            expenseReceiptsViewHolder.itemList.setEnabled(true);
        } else {
            expenseReceiptsViewHolder.itemList.setEnabled(false);
        }
    }
}
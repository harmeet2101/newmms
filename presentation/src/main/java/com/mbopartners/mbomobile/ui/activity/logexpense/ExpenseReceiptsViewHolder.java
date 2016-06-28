package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

import java.util.List;

public class ExpenseReceiptsViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemList;
    ExpenseForEdit expenseForEdit;

    public ExpenseReceiptsViewHolder(View view, ExpenseForEdit expenseForEdit, Context context) {
        super(view);
        this.expenseForEdit = expenseForEdit;
        this.itemList = (LinearLayout) view.findViewById(R.id.receipt_items_LinearLayout);

    }

    public void setData(List<Receipt> receipts, final LogExpenseActivityFragment.OnFragmentInteractionListener receiptActionListener, Context context) {
        itemList.removeAllViews();
        for (Receipt receipt : receipts) {
            View itemView =LayoutInflater.from(itemList.getContext()).inflate(R.layout.item_log_expense_receipt, itemList, false);
            final TextView textView=(TextView)itemView.findViewById(R.id.imagesText);
            ImageView imageView=(ImageView)itemView.findViewById(R.id.deleteImage);
            textView.setEnabled(expenseForEdit.editable);
            textView.setText(receipt.getFilename());
            if(expenseForEdit.editable)
            textView.setTextColor(context.getResources().getColor(R.color.mbo_simple_button_bg__color_normal));
            else
            textView.setTextColor(Color.GRAY);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    receiptActionListener.onDeleteReceipt(((TextView) v).getText().toString());
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    receiptActionListener.onDeleteReceipt(textView.getText().toString());

                }
            });
            itemList.addView(itemView);
        }
        itemList.invalidate();
    }
}
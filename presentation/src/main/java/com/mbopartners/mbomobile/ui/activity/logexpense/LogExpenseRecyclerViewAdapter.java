package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

import java.util.ArrayList;

public class LogExpenseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ExpenseForEdit expenseForEdit;
    private ExpenseFieldHolderHelper fieldHelper;
    private Context context;
    private ArrayList<Integer> shownFieldsIndices = new ArrayList<>();

    public LogExpenseRecyclerViewAdapter(ExpenseForEdit expenseForEdit) {
        this.expenseForEdit = expenseForEdit;
        checkForPurposeField(this.expenseForEdit);
        this.fieldHelper.setExpense(expenseForEdit,shownFieldsIndices);
    }

    public LogExpenseRecyclerViewAdapter(Context context,ExpenseForEdit expenseForEdit) {
        this.context=context;
        fieldHelper = new ExpenseFieldHolderHelper(context);
        this.expenseForEdit = expenseForEdit;
        if (this.expenseForEdit != null) {
            checkForPurposeField(this.expenseForEdit);
        }else{
            shownFieldsIndices.add(0);
        }
        this.fieldHelper.setExpense(expenseForEdit,shownFieldsIndices);
    }
    @Override
    public int getItemCount() {
        int itemCount = -1;
        if (expenseForEdit != null) {
            int numExpenseFields = shownFieldsIndices.size();
            
            return numExpenseFields + 1;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return fieldHelper.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return fieldHelper.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.fieldHelper.onBindViewHolder(holder, position);
    }

    public void updateDataSource(ExpenseForEdit expenseForEdit) {
        this.expenseForEdit = expenseForEdit;
        if (this.expenseForEdit != null) {
            checkForPurposeField(this.expenseForEdit);
        }else{
            shownFieldsIndices.add(0);
        }
        this.fieldHelper.setExpense(expenseForEdit,shownFieldsIndices);
        notifyDataSetChanged();
    }

    public void setOnReceiptLongClickListener(LogExpenseActivityFragment.OnFragmentInteractionListener onReceiptActionListener) {
        fieldHelper.setOnReceiptActionListener(onReceiptActionListener);
    }

    public static class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void checkForPurposeField(ExpenseForEdit expense){
        shownFieldsIndices.clear();
        for (ExpenseField expenseField : expense.expenseType.getFields()){
            if (expenseField.getMboId().equalsIgnoreCase("purposeId") || expenseField.getMboId().equalsIgnoreCase("purpose")){
                if (expense.mboWorkOrderId == null){
                    shownFieldsIndices.add(expense.expenseType.getFields().indexOf(expenseField));
                }
            }else {
                shownFieldsIndices.add(expense.expenseType.getFields().indexOf(expenseField));
            }
        }
    }
}

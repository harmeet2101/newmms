package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

public class ExpenseFieldViewHolder_Menu extends BaseFieldViewHolder implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    public Spinner spinner;
    private Context context;

    public ExpenseFieldViewHolder_Menu(Context context, View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView, expenseForEdit);
        this.spinner = (Spinner) itemView.findViewById(R.id.expenseFieldValue);
        this.spinner.setOnFocusChangeListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int itemPosition = getAdapterPosition();

        if (itemPosition >= 0) {
            ExpenseField expenseField = expenseForEdit.expenseType.getFields().get(itemPosition);
            String expenseFieldMboId = expenseField.getMboId();
            expenseForEdit.putFieldValue(expenseFieldMboId, expenseField.getValues().get(position).getMboId());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){

            fieldTitleTextView.setTextColor(context.getResources().getColor(R.color.mbo_theme_blue_primary));

        }
        else{
            fieldTitleTextView.setTextColor(context.getResources().getColor(R.color.mbo_theme_black_primary_dark));
        }
    }
}
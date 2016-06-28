package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;
import com.mbopartners.mbomobile.ui.util.DecimalDigitsInputFilter;

public class ExpenseFieldViewHolder_Number extends BaseFieldViewHolder implements TextWatcher {

    public EditText fieldValueEditText;

    public ExpenseFieldViewHolder_Number(View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView, expenseForEdit);
        this.fieldValueEditText = (EditText) itemView.findViewById(R.id.expenseFieldValue);
        this.fieldValueEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(0)});
        this.fieldValueEditText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int itemPosition = getAdapterPosition();
        String expenseFieldMboId = expenseForEdit.expenseType.getFields().get(itemPosition).getMboId();
        expenseForEdit.putFieldValue(expenseFieldMboId, s.toString());
    }
}
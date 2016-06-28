package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

public class ExpenseFieldViewHolder_Text extends BaseFieldViewHolder implements TextWatcher,View.OnFocusChangeListener {

    public EditText fieldValueEditText;
    private Context context;

    public ExpenseFieldViewHolder_Text(Context context, View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView, expenseForEdit);
        this.fieldValueEditText = (EditText) itemView.findViewById(R.id.expenseFieldValue);
        this.fieldValueEditText.setHint("Name");
        this.fieldValueEditText.addTextChangedListener(this);
        this.fieldValueEditText.setOnFocusChangeListener(this);
        this.context = context;
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

package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;
import com.mbopartners.mbomobile.ui.util.DecimalDigitsInputFilter;

public class ExpenseFieldViewHolder_Money extends BaseFieldViewHolder implements TextWatcher,View.OnFocusChangeListener{

    public EditText fieldValueEditText;
    private Context context;

    public ExpenseFieldViewHolder_Money(Context context,View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView, expenseForEdit);
        this.fieldValueEditText = (EditText) itemView.findViewById(R.id.expenseFieldValue);
        this.fieldValueEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        this.fieldValueEditText.addTextChangedListener(this);
        this.fieldValueEditText.setOnFocusChangeListener(this);
        this.context=context;
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
//        if(!hasFocus){
//            String userInput = fieldValueEditText.getText().toString();
//
//            int dotPos = -1;
//
//            for (int i = 0; i < userInput.length(); i++) {
//                char c = userInput.charAt(i);
//                if (c == '.') {
//                    dotPos = i;
//                }
//            }
///*Making Money editField get trailing values after the decimal*/
//            if (dotPos == -1&& !userInput.equals("")){
//                fieldValueEditText.setText(userInput + ".00");
//            }else if(dotPos == -1&& userInput.equals(""))
//                fieldValueEditText.setText(userInput + "0.00");
//            else if(dotPos==userInput.length()-1 && !userInput.equals(""))
//                fieldValueEditText.setText("0"+userInput + "00");
//            else if(dotPos==userInput.length()-2 && !userInput.equals(""))
//                fieldValueEditText.setText(userInput + "0");
//            this.fieldValueEditText.clearFocus();
//            fieldTitleTextView.setTextColor(context.getResources().getColor(R.color.mbo_theme_black_primary_dark));
//        }
        if(hasFocus){

            fieldTitleTextView.setTextColor(context.getResources().getColor(R.color.mbo_theme_blue_primary));

        }

    }
}




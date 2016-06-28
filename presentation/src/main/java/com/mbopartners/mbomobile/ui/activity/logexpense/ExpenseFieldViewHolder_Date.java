package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class ExpenseFieldViewHolder_Date
        extends BaseFieldViewHolder
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public TextView fieldValueEditText;

    public ExpenseFieldViewHolder_Date(View itemView, ExpenseForEdit expenseForEdit) {
        super(itemView, expenseForEdit);
        this.fieldValueEditText = (TextView) itemView.findViewById(R.id.expenseFieldValue);
        this.fieldValueEditText.setHint("MM/DD/YYYY");
        this.fieldValueEditText.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showTimePickerDialog(v);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date selectedDate = calendar.getTime();

        int itemPosition = getAdapterPosition();
        String expenseFieldMboId = expenseForEdit.expenseType.getFields().get(itemPosition).getMboId();

        String enteredDate = DateUtil.getDateFormatted_yyyymmdd(selectedDate);
        expenseForEdit.putFieldValue(expenseFieldMboId, enteredDate);
        String formattedDate = DateUtil.getDateFormatted_mmddyyyy(selectedDate);
        this.fieldValueEditText.setText(formattedDate);
    }

    public void showTimePickerDialog(View v) {

        int itemPosition = getAdapterPosition();
        String expenseFieldMboId = expenseForEdit.expenseType.getFields().get(itemPosition).getMboId();
        String dateStr = expenseForEdit.getFieldValue(expenseFieldMboId);

        Date date = DateUtil.parseFrom_yyyymmdd(dateStr);
        date = (date != null ? date : DateUtil.getCurrentDate());

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Context context = this.itemView.getContext();
        DatePickerDialog.OnDateSetListener listener = this;
        int theme = R.style.mbo_AppTheme_green;

//        DatePickerDialog dialog = new DatePickerDialog(context, theme, listener, year, month, day);
// This will choose the theme for the calendar
        DatePickerDialog dialog = null; /*new DatePickerDialog(context, listener, year, month, day);*/
        dialog = new DatePickerDialog(context, R.style.MyDatePickerDialogTheme, listener, year, month, day);

        //dialog.getDatePicker().setMinDate(...);
        //dialog.getDatePicker().setMaxDate(...);
        dialog.show();
    }
}
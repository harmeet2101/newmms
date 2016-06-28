package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseFieldType;
import com.mbopartners.mbomobile.ui.model.ExpenseForEdit;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFieldHolderHelper {

    public static final int ITEM_VIEW_TYPE__UNKNOWN = 0;
    public static final int ITEM_VIEW_TYPE__LOADING = 1;
//    public static final int ITEM_VIEW_TYPE__AMOUNT = 2;

    public static final int ITEM_VIEW_TYPE__FIELD_TEXT = 10;
    public static final int ITEM_VIEW_TYPE__FIELD_BIG_TEXT = 11;
    public static final int ITEM_VIEW_TYPE__FIELD_DATE = 12;
    public static final int ITEM_VIEW_TYPE__FIELD_MONEY = 13;
    public static final int ITEM_VIEW_TYPE__FIELD_MENU = 14;
    public static final int ITEM_VIEW_TYPE__FIELD_CITY_STATE = 15;
    public static final int ITEM_VIEW_TYPE__FIELD_NUMBER = 16;
    public static final int ITEM_VIEW_TYPE__RECEIPTS_LIST = 20;

    private ExpenseForEdit expenseForEdit = null;
    private ArrayList<Integer> shownFieldsList;
    private LogExpenseActivityFragment.OnFragmentInteractionListener receiptActionListener;
    private Context context;
    ArrayList<Integer> shownFields = new ArrayList<>();
    public void setExpense(ExpenseForEdit expenseForEdit,ArrayList<Integer> shownFields) {
        this.expenseForEdit = expenseForEdit;
        shownFieldsList = shownFields;
    }

    public ExpenseFieldHolderHelper(Context context)
    {
        this.context=context;
    }

    public int getItemViewType(int position) {
        if (expenseForEdit == null) {
            return ITEM_VIEW_TYPE__LOADING;
        } else if (position == shownFieldsList.size()) {
            return ITEM_VIEW_TYPE__RECEIPTS_LIST;
        }

        int viewType;
        ExpenseField expenseField = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
        ExpenseFieldType fieldType = ExpenseFieldType.getValueOf(expenseField.getType());
        switch (fieldType) {
            case TEXT : {
                viewType = ITEM_VIEW_TYPE__FIELD_TEXT;
                break;
            }
            case BIG_TEXT : {
                viewType = ITEM_VIEW_TYPE__FIELD_BIG_TEXT;
                break;
            }
            case DATE : {
                viewType = ITEM_VIEW_TYPE__FIELD_DATE;
                break;
            }
            case MONEY: {
                viewType = ITEM_VIEW_TYPE__FIELD_MONEY;
                break;
            }
            case MENU : {
                viewType = ITEM_VIEW_TYPE__FIELD_MENU;
                break;
            }
            case CITY_STATE: {
                viewType = ITEM_VIEW_TYPE__FIELD_CITY_STATE;
                break;
            }
            case NUMBER: {
                viewType = ITEM_VIEW_TYPE__FIELD_NUMBER;
                break;
            }
            default: {
                viewType = ITEM_VIEW_TYPE__UNKNOWN;
            }
        }
        return viewType;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case ITEM_VIEW_TYPE__LOADING : {
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_TEXT : {
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editableExpense = expenseForEdit.editable;
                boolean notFixedPurpose = ! expenseForEdit.isItFixedPurpose(field);
                boolean editable = editableExpense && notFixedPurpose;
                (new ExpenseFieldGovernor_Text()).onBindViewHolder(holder, field, value, editable, context);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_BIG_TEXT :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editable = expenseForEdit.editable;
                (new ExpenseFieldGovernor_BigText()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_DATE :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editable = expenseForEdit.editable;
                (new ExpenseFieldGovernor_Date()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_MONEY :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editable = expenseForEdit.editable;
                (new ExpenseFieldGovernor_Money()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_MENU :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editableExpense = expenseForEdit.editable;
                boolean notFixedPurpose = ! expenseForEdit.isItFixedPurpose(field);
                boolean editable = editableExpense && notFixedPurpose;
                (new ExpenseFieldGovernor_Menu()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_CITY_STATE :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editable = expenseForEdit.editable;
                (new ExpenseFieldGovernor_CityState()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__FIELD_NUMBER :{
                ExpenseField field = expenseForEdit.expenseType.getFields().get(shownFieldsList.get(position));
                String value = expenseForEdit.getFieldValue(field.getMboId());
                boolean editable = expenseForEdit.editable;
                (new ExpenseFieldGovernor_Number()).onBindViewHolder(holder, field, value, editable);
                break;
            }
            case ITEM_VIEW_TYPE__RECEIPTS_LIST : {
                boolean editable = expenseForEdit.editable;
                (new ExpenseReceiptsListGovernor()).onBindViewHolder(holder, expenseForEdit.receipts, receiptActionListener, editable, context);
                break;
            }
            default : {
            }
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE__LOADING : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                return new LogExpenseRecyclerViewAdapter.BulkViewHolder(itemView);
            }
            case ITEM_VIEW_TYPE__FIELD_TEXT : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__text, parent, false);
                return new ExpenseFieldViewHolder_Text(context, itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_BIG_TEXT : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__big_text, parent, false);
                return new ExpenseFieldViewHolder_BigText(context, itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_DATE : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__date, parent, false);
                return new ExpenseFieldViewHolder_Date(itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_MONEY : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__money, parent, false);
                return new ExpenseFieldViewHolder_Money(context, itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_MENU : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__menu, parent, false);
                return new ExpenseFieldViewHolder_Menu(context, itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_CITY_STATE : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__citystate, parent, false);
                return new ExpenseFieldViewHolder_CityState(context,itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__FIELD_NUMBER : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__number, parent, false);
                return new ExpenseFieldViewHolder_Number(itemView, expenseForEdit);
            }
            case ITEM_VIEW_TYPE__RECEIPTS_LIST : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_expense_field__receipts, parent, false);
                return new ExpenseReceiptsViewHolder(itemView, expenseForEdit, context);
            }
            default : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                return new LogExpenseRecyclerViewAdapter.BulkViewHolder(itemView);
            }
        }
    }

    public void setOnReceiptActionListener(LogExpenseActivityFragment.OnFragmentInteractionListener receiptActionListener) {
        this.receiptActionListener = receiptActionListener;
    }

}

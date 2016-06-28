package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.rest.model.response.ExpenseFieldValue;
import com.mbopartners.mbomobile.ui.R;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFieldGovernor_Menu extends BaseFieldGovernor {

    private boolean is_Editable;

    public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
        super.onBindViewHolder(holder, field);
        ExpenseFieldViewHolder_Menu holder_Menu = (ExpenseFieldViewHolder_Menu) holder;

        Context context = holder_Menu.spinner.getContext();

        MenuFieldAdapter adapter = new MenuFieldAdapter(context, field.getValues());
        holder_Menu.spinner.setPrompt("choose" + holder_Menu.fieldTitleTextView.getText());
        holder_Menu.spinner.setAdapter(adapter);
        String normalizedValue =  value != null ? value : "";
        holder_Menu.spinner.setSelection(getValueIndex(field, normalizedValue));
        if (editable) {
            is_Editable=true;
            holder_Menu.spinner.setEnabled(true);
        } else {
            is_Editable=false;
            holder_Menu.spinner.setEnabled(false);
        }
    }

    private int getValueIndex(ExpenseField field, String value) {
        for (int counter = 0; counter < field.getValues().size(); counter++) {
            if (field.getValues().get(counter).getMboId().equals(value)) {
                return counter;
            }
        }
        return -1;
    }

    public static List<String> getMboIdVariants(List<ExpenseFieldValue> expenseFieldValues) {
        List<String> variants = new ArrayList<>(expenseFieldValues.size());
        for (ExpenseFieldValue expenseFieldValue : expenseFieldValues) {
            variants.add(expenseFieldValue.getMboId());
        }
        return variants;
    }

    class MenuFieldAdapter extends BaseAdapter {

        private int mLayout;
        private LayoutInflater mInflater;

        private List<ExpenseFieldValue> menuFieldValues;

        public MenuFieldAdapter(Context context, List<ExpenseFieldValue> menuFieldValues) {
            this.menuFieldValues = menuFieldValues;
//            mLayout = android.R.layout.simple_spinner_dropdown_item;
            mLayout = R.layout.multiline_spinner_dropdown_item;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return menuFieldValues.size();
        }

        @Override
        public Object getItem(int position) {
            return menuFieldValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void bindView(View view, final ExpenseFieldValue expenseFieldValue) {
            final TextView textView = (TextView) view.findViewById(android.R.id.text1);
            if(is_Editable)
            {
                textView.setTextColor(Color.BLACK);
            }else {
                textView.setTextColor(Color.GRAY);
            }
            textView.setText(expenseFieldValue.getValue());

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = mInflater.inflate(mLayout, parent, false);
            } else {
                v = convertView;
            }

            bindView(v, (ExpenseFieldValue) getItem(position));
            return v;
        }
    }
}

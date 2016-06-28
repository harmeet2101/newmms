package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseSpinner;

import java.util.ArrayList;

/**
 * Created by vinove on 17/5/16.
 */
public class LogExpenseSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExpenseSpinner> expenseSpinnerArrayList;

    public  LogExpenseSpinnerAdapter(Context context,ArrayList<ExpenseSpinner> expenseSpinnerArrayList)
    {
        this.context=context;
        this.expenseSpinnerArrayList=expenseSpinnerArrayList;
    }
    @Override
    public int getCount() {
        return expenseSpinnerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseSpinnerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.layout_spinner_items_textview,null);
        TextView spinnerItem=(TextView)convertView.findViewById(R.id.spinneritems);
        spinnerItem.setText(expenseSpinnerArrayList.get(position).getItem().getName());
        return convertView;
    }
}

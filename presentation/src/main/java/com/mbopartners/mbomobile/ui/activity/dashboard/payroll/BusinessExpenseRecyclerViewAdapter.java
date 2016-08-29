package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessExpenses;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

public class BusinessExpenseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__DATA = 2;
    private List<BusinessExpenses> businessExpensesList;


    public BusinessExpenseRecyclerViewAdapter(Context context){

        this.context=context;
    }

    public BusinessExpenseRecyclerViewAdapter(Context context,List<BusinessExpenses> businessExpensesList){

        this.context=context;
        this.businessExpensesList=businessExpensesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case ITEM_VIEW_TYPE__LOADING:{

                final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list_simple,parent,false);
                fillParent(parent,view);
                viewHolder=new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__EMPTY_LIST:{
                final View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list,parent,false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText("");
                fillParent(parent,view);
                viewHolder=new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__DATA:{
                final View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_holding_recyclerview,parent,false);
                viewHolder=new ExpenseViewHolder(view);
                break;
            }
            default:{
                viewHolder=null;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(businessExpensesList==null){

        }else if(businessExpensesList.isEmpty()){

        }
        else
            bindViewHolder_ExpenseView((ExpenseViewHolder)holder,position);
    }

    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }
    @Override
    public int getItemCount() {

        int count = 0;
        if (businessExpensesList == null) {
            count = 1;
        } else if (businessExpensesList.isEmpty()) {
            count = 1;
        } else {
            count = businessExpensesList.size();
        }
        return count;
    }

    public int getItemViewType(int position){

        int itemViewType = -1;
        if (businessExpensesList == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (businessExpensesList.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        } else {
            itemViewType = ITEM_VIEW_TYPE__DATA;
        }
        return itemViewType;
    }

    private boolean isChecked;

    public void updateDataSource(boolean isChecked) {
        this.isChecked = isChecked;
        notifyDataSetChanged();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView value;
        public ExpenseViewHolder(View view){
            super(view);

            this.name=(TextView)view.findViewById(R.id.textview_name);
            this.value=(TextView)view.findViewById(R.id.textview_value);
        }
    }


    public void bindViewHolder_ExpenseView(ExpenseViewHolder viewHolder,int position)
    {


        viewHolder.name.setText(businessExpensesList.get(position).getName());
        if(isChecked)
            viewHolder.value.setText("$"+ String.format("%.2f", businessExpensesList.get(position).getAmountYtd()));
        else {
            ;
            viewHolder.value.setText("$"+ String.format("%.2f",businessExpensesList.get(position).getAmount()));
        }
    }

    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }






}

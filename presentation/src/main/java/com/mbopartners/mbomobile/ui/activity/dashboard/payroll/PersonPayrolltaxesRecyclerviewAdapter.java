package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonPayrollTaxes;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

public class PersonPayrolltaxesRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__DATA = 2;
    private List<PersonPayrollTaxes> personPayrollTaxes;

    public PersonPayrolltaxesRecyclerviewAdapter(Context context){

        this.context=context;
    }

    public PersonPayrolltaxesRecyclerviewAdapter(Context context,List<PersonPayrollTaxes> personPayrollTaxes){

        this.context=context;
        this.personPayrollTaxes=personPayrollTaxes;
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
                final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list,parent,false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText("");
                fillParent(parent,view);
                viewHolder=new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__DATA:{
                final View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_holding_recyclerview,parent,false);
                viewHolder=new ExpenseReimbersementsViewHolder(view);
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

        if(personPayrollTaxes==null){

        }else if(personPayrollTaxes.isEmpty()){

        }
        else
            bindViewHolder_ExpenseReimbersementsView((ExpenseReimbersementsViewHolder)holder,position);
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
        if (personPayrollTaxes == null) {
            count = 1;
        } else if (personPayrollTaxes.isEmpty()) {
            count = 1;
        } else {
            count = personPayrollTaxes.size();
        }
        return count;
    }

    public int getItemViewType(int position){

        int itemViewType = -1;
        if (personPayrollTaxes == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (personPayrollTaxes.isEmpty()) {
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

    public class ExpenseReimbersementsViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView value;
        public ExpenseReimbersementsViewHolder(View view){
            super(view);

            this.name=(TextView)view.findViewById(R.id.text);
            this.name=(TextView)view.findViewById(R.id.textview_name);
            this.value=(TextView)view.findViewById(R.id.textview_value);
        }
    }


    public void bindViewHolder_ExpenseReimbersementsView(ExpenseReimbersementsViewHolder viewHolder,int position)
    {



        /*if(isChecked) {
            viewHolder.name.setText(personPayrollTaxes.get(position).getName());
            viewHolder.value.setText("$" + String.format("%.2f", personPayrollTaxes.get(position).getAmountYtd()));

        }else if(!isChecked){

            viewHolder.name.setText(personPayrollTaxes.get(position).getName());
            viewHolder.value.setText("$" + String.format("%.2f",personPayrollTaxes.get(position).getAmount()));
        }*/

        viewHolder.name.setText(personPayrollTaxes.get(position).getName());
        viewHolder.value.setText("$" + String.format("%.2f",personPayrollTaxes.get(position).getAmount()));
    }



    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }


}


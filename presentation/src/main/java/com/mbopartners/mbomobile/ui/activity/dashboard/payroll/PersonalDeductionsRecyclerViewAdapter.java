package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonPayrollTaxes;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonalDeductions;
import com.mbopartners.mbomobile.ui.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by MboAdil on 4/8/16.
 */
public class PersonalDeductionsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__DATA = 2;
    private List<PersonalDeductions> personalDeductionsList;
    private List<PersonPayrollTaxes> payrollTaxesList;
    private HashMap<Integer,Double> values=new HashMap();
    private HashMap<Integer,Double> values_ytd=new HashMap();
    private HashMap<Integer,String> names=new HashMap();

    public PersonalDeductionsRecyclerViewAdapter(Context context){

        this.context=context;
    }

    public PersonalDeductionsRecyclerViewAdapter(Context context,List<PersonalDeductions> personalDeductionsList,List<PersonPayrollTaxes> personPayrollTaxesList){

        this.context=context;
        this.personalDeductionsList=personalDeductionsList;
        payrollTaxesList=personPayrollTaxesList;
        setValues(personalDeductionsList,payrollTaxesList);
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

        if(personalDeductionsList==null){

        }else if(personalDeductionsList.isEmpty()){

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
        if (personalDeductionsList == null) {
            count = 1;
        } else if (personalDeductionsList.isEmpty()) {
            count = 1;
        } else {
            count = getTotalCount(personalDeductionsList,payrollTaxesList);
        }
        return count;
    }

    public int getItemViewType(int position){

        int itemViewType = -1;
        if (personalDeductionsList == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (personalDeductionsList.isEmpty()) {
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

            this.name=(TextView)view.findViewById(R.id.textview_name);
            this.value=(TextView)view.findViewById(R.id.textview_value);
        }
    }


    public void bindViewHolder_ExpenseReimbersementsView(ExpenseReimbersementsViewHolder viewHolder,int position)
    {



        if(isChecked) {
            viewHolder.name.setText(names.get(position));
            viewHolder.value.setText("$" + String.format("%.2f", values_ytd.get(position)));

        }else if(!isChecked){

            viewHolder.name.setText(names.get(position));
            viewHolder.value.setText("$" + String.format("%.2f", values.get(position)));
        }
    }



    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void setValues(List<PersonalDeductions>personalDeductionsList,List<PersonPayrollTaxes> payrollTaxesList){

        int payrollTaxesCount=0;

        if(payrollTaxesList!=null){
            for (payrollTaxesCount=0;payrollTaxesCount < payrollTaxesList.size(); payrollTaxesCount++) {

                    names.put(payrollTaxesCount,payrollTaxesList.get(payrollTaxesCount).getName());
                    values.put(payrollTaxesCount, payrollTaxesList.get(payrollTaxesCount).getAmount());
                    values_ytd.put(payrollTaxesCount, payrollTaxesList.get(payrollTaxesCount).getAmountYtd());

            }
        }

        int personDeducationCount=0;
        if (personalDeductionsList != null) {
            for (personDeducationCount=0; personDeducationCount< personalDeductionsList.size(); personDeducationCount++) {

                    names.put(personDeducationCount+payrollTaxesCount,personalDeductionsList.get(personDeducationCount).getName());
                    values.put(personDeducationCount+payrollTaxesCount, personalDeductionsList.get(personDeducationCount).getAmount());
                    values_ytd.put(personDeducationCount+payrollTaxesCount, personalDeductionsList.get(personDeducationCount).getAmountYtd());

            }

        }
    }

    private int getTotalCount(List<PersonalDeductions>personalDeductionsList,List<PersonPayrollTaxes> payrollTaxesList){

        int count=0;
        if(personalDeductionsList!=null){
            count=personalDeductionsList.size();
        }
        if(payrollTaxesList!=null){
            count=count+payrollTaxesList.size();
        }
        return count;
    }

}


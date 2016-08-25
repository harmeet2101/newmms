package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonDeposits;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

public class PersonEarningsRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__DATA = 2;
    private List<PersonDeposits> personDepositsList;

    public PersonEarningsRecylerViewAdapter(Context context){

        this.context=context;
    }

    public PersonEarningsRecylerViewAdapter(Context context,List<PersonDeposits> personDepositsList){

        this.context=context;
        this.personDepositsList=personDepositsList;
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
                viewHolder=new EarningViewHolder(view);
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

        if(personDepositsList==null){

        }else if(personDepositsList.isEmpty()){

        }
        else
            bindViewHolder_Earningsview((EarningViewHolder) holder, position);
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
        if (personDepositsList == null) {
            count = 1;
        } else if (personDepositsList.isEmpty()) {
            count = 1;
        } else {
            count = personDepositsList.size();
        }
        return count;
    }

    public int getItemViewType(int position){

        int itemViewType = -1;
        if (personDepositsList == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (personDepositsList.isEmpty()) {
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

    public class EarningViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView value;
        public EarningViewHolder(View view){
            super(view);

            this.name=(TextView)view.findViewById(R.id.textview_name);
            this.value=(TextView)view.findViewById(R.id.textview_value);
        }
    }


    public void bindViewHolder_Earningsview(EarningViewHolder viewHolder,int position)
    {


        viewHolder.name.setText(personDepositsList.get(position).getName());
        viewHolder.value.setText("$"+String.format("%.2f",personDepositsList.get(position).getAmount()));
        /*if(isChecked) {


        }else if(!isChecked){


        }*/
    }



    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

}

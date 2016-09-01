package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.PreviousPayments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonDeposits;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.NumberFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MboAdil on 19/7/16.
 */
public class PreviousPaymentRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    private Context context;
    private OnItemClickListener mItemClickListener;
    private PersonWithHolding personWithHolding;
    private ArrayList<PayrollTransactions> payrollSummaryList;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__PAYROLL = 2;
    private static final int ITEM_VIEW_TYPE__REIMBERSEMENTS = 3;
    public PreviousPaymentRecylerViewAdapter(Context context,PersonWithHolding personWithHolding,List<PayrollTransactions> payrollSummaryList) {

        this.personWithHolding=personWithHolding;
        this.context=context;
        this.payrollSummaryList= (ArrayList<PayrollTransactions>) payrollSummaryList;
    }

    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType)
        {
            case ITEM_VIEW_TYPE__LOADING:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list_simple, parent, false);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__EMPTY_LIST:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list, parent, false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText(R.string.mbo_dashboard_revenue_empty_list);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__PAYROLL:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payroll_previous_payments_list_items, parent, false);
                viewHolder=new PreviousPaymentViewHolder(view);
                break;
            }
            default:{
                viewHolder=null;
            }

        }
        return viewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        if(payrollSummaryList==null)
            itemViewType=ITEM_VIEW_TYPE__LOADING;
        else if (payrollSummaryList.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        }
        else
        itemViewType=ITEM_VIEW_TYPE__PAYROLL;

        return itemViewType;
    }
    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (payrollSummaryList == null) {

        } else if (payrollSummaryList.isEmpty()) {

        }else
            bindViewHolder_payroll((PreviousPaymentViewHolder) holder, position);
    }

    public void bindViewHolder_Loading(RecyclerView.ViewHolder viewHolder) {

    }

    public void bindViewHolder_EmptyList(RecyclerView.ViewHolder viewHolder) {

    }

    public void bindViewHolder_payroll(PreviousPaymentViewHolder viewHolder, int position) {


        viewHolder.paymentNameTextview.setText(depositsList.get(position).getName());
        String temp=String.format("%.2f",depositsList.get(position).getAmount());
        viewHolder.paymentAmountTextView.setText("$"+NumberFormatUtils.getAmountWithCommas(temp));
        //viewHolder.paymentAmountTextView.setText("$" + TwoDecimalPlacesUtil.getAmount_uptoTwoDecimalPlaces(String.valueOf(depositsList.get(position).getAmount())));
                /*Adding date*/
        viewHolder.paymentDateTextView.setText(DateUtil.getDateFormatted_payroll(payrollSummaryList.get(position).getDate()));

        if(position==payrollSummaryList.size()-1)
            viewHolder.dividerLineView.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        int itemCount;
        if(payrollSummaryList==null)
        {
            itemCount=1;
        }
        else if (payrollSummaryList.isEmpty())  {

                itemCount=1;
        } else {
            itemCount = getTotalSize(payrollSummaryList);
        }
        return itemCount;
    }

    public void updateDataSource(List<PayrollTransactions> fields) {
        this.payrollSummaryList = (ArrayList<PayrollTransactions>) fields;
        notifyDataSetChanged();
    }
    public class PreviousPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View itemView;
        public TextView paymentNameTextview;
        public TextView paymentAmountTextView;
        public TextView paymentDateTextView;
        public View dividerLineView;
        public PreviousPaymentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            paymentNameTextview=(TextView)itemView.findViewById(R.id.paymentTextview);
            paymentAmountTextView=(TextView)itemView.findViewById(R.id.amount);
            paymentDateTextView=(TextView)itemView.findViewById(R.id.dateTextview);
            dividerLineView=itemView.findViewById(R.id.divider);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }
        }
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public int getTotalSize(List<PayrollTransactions> transactionsList)
    {
        int size=0;
        if(transactionsList!=null)
        {
            if(transactionsList.size()==1)
            {
                if(transactionsList.get(0).getPersonalWithholding().getDeposits()!=null)
                {
                    size=transactionsList.get(0).getPersonalWithholding().getDeposits().size();
                    depositsList=transactionsList.get(0).getPersonalWithholding().getDeposits();
                }
                return size;
            }else if(transactionsList.size()>0)
            {
                for (int i=0;i<transactionsList.size();i++)
                {
                    size=size+transactionsList.get(i).getPersonalWithholding().getDeposits().size();
                    depositsList.addAll(transactionsList.get(i).getPersonalWithholding().getDeposits());
                }
                return size;
            }
        }
        return size;
    }

    private List<PersonDeposits> depositsList=new ArrayList<PersonDeposits>();
}

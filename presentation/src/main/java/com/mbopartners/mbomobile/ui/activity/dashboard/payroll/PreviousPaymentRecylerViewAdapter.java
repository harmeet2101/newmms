package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MboAdil on 19/7/16.
 */
public class PreviousPaymentRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    private Context context;
    private OnItemClickListener mItemClickListener;
    private PersonWithHolding personWithHolding;
    private ArrayList<PayrollSummary> payrollSummaryList;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__PAYROLL = 2;
    private static final int ITEM_VIEW_TYPE__REIMBERSEMENTS = 3;
    public PreviousPaymentRecylerViewAdapter(Context context,PersonWithHolding personWithHolding,List<PayrollSummary> payrollSummaryList) {

        this.personWithHolding=personWithHolding;
        this.context=context;
        this.payrollSummaryList= (ArrayList<PayrollSummary>) payrollSummaryList;
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
            }/*
            case ITEM_VIEW_TYPE__EMPTY_LIST:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list, parent, false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText(R.string.mbo_dashboard_revenue_empty_list);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }*/
            case ITEM_VIEW_TYPE__PAYROLL:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payroll_previous_payments_list_items, parent, false);
                viewHolder=new PreviousPaymentViewHolder(view);
                break;
            }
            /*case ITEM_VIEW_TYPE__REIMBERSEMENTS:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payroll_previous_payments_list_items, parent, false);
                viewHolder=new ReimbersementPaymentViewHolder(view);
                break;
            }*/
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
        else
        itemViewType=ITEM_VIEW_TYPE__PAYROLL;
    /*    else if(position==1)
            itemViewType=ITEM_VIEW_TYPE__REIMBERSEMENTS;*/
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

        }else
            bindViewHolder_payroll((PreviousPaymentViewHolder)holder,position);


    }

    public void bindViewHolder_payroll(PreviousPaymentViewHolder viewHolder, int position) {

        viewHolder.paymentNameTextview.setText(payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getPayrollTaxes().get(position).getName());
        viewHolder.paymentAmountTextView.setText("$"+payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getPayrollTaxes().get(position).getAmount());
       /*Adiing date*/
        viewHolder.paymentDateTextView.setText(DateUtil.getDateFormatted_payroll(payrollSummaryList.get(0).getLast_payroll().getdate()));
        if(position==payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getPayrollTaxes().size()-1)
            viewHolder.dividerLineView.setVisibility(View.INVISIBLE);

    }
    public void bindViewHolder_Reimbersement(ReimbersementPaymentViewHolder viewHolder, int position) {

        viewHolder.paymentNameTextview.setText(payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getExpenseReimbursements().get(position).getName());
        viewHolder.paymentAmountTextView.setText("$"+payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getExpenseReimbursements().get(position).getAmount());
    }
    @Override
    public int getItemCount() {
        int itemCount;
        if(payrollSummaryList==null)
        {
            itemCount=0;
        }
        else if (payrollSummaryList != null)  {

                itemCount=payrollSummaryList.get(0).getLast_payroll().getPersonalWithholding().getPayrollTaxes().size();
        } else {
            itemCount = 0;
        }
        return itemCount;
    }

    public void updateDataSource(List<PayrollSummary> fields) {
        this.payrollSummaryList = (ArrayList<PayrollSummary>) fields;
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

    public class ReimbersementPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View itemView;
        public TextView paymentNameTextview;
        public TextView paymentAmountTextView;
        public TextView paymentDateTextView;
        public ReimbersementPaymentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            paymentNameTextview=(TextView)itemView.findViewById(R.id.paymentTextview);
            paymentAmountTextView=(TextView)itemView.findViewById(R.id.amount);
            paymentDateTextView=(TextView)itemView.findViewById(R.id.dateTextview);
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
}

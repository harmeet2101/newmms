package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings.ViewEstimatedWithHolding;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.NumberFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    private Context context;
    private OnItemClickListener mItemClickListener;
    private PersonWithHolding personWithHolding;
    private ArrayList<PayrollSummary> payrollSummaryList;
    private ArrayList<PayrollTransactions> payrollTransactionsList;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__PAYROLL_PAYMENT_DETAILS = 2;

    public PaymentDetailsRecyclerViewAdapter(Context context,PersonWithHolding personWithHolding,List<PayrollSummary> payrollSummaryList) {

        this.personWithHolding=personWithHolding;
        this.context=context;
        this.payrollSummaryList= (ArrayList<PayrollSummary>) payrollSummaryList;
    }

    public PaymentDetailsRecyclerViewAdapter(Context context,PersonWithHolding personWithHolding,List<PayrollSummary> payrollSummaryList,
                                             List<PayrollTransactions> payrollTransactionsList) {

        this.personWithHolding=personWithHolding;
        this.context=context;
        this.payrollSummaryList= (ArrayList<PayrollSummary>) payrollSummaryList;
        this.payrollTransactionsList= (ArrayList<PayrollTransactions>) payrollTransactionsList;
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
            case ITEM_VIEW_TYPE__PAYROLL_PAYMENT_DETAILS:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payroll_payment_details_rv_items, parent, false);
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
            itemViewType=ITEM_VIEW_TYPE__PAYROLL_PAYMENT_DETAILS;

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

        switch (position){
            case 0:
                viewHolder.textview1.setText("Payment Frequency");
                viewHolder.textview2.setText(payrollSummaryList.get(0).getNext_payroll().getFrequency());
                break;
            case 1:
                viewHolder.textview1.setText("Start Date");
                viewHolder.textview2.setText(DateUtil.getDateFormatted_payroll(payrollSummaryList.get(0).getNext_payroll().getStartDate()));
                break;
            case 2:
                viewHolder.textview1.setText("Payment Method");
                viewHolder.textview2.setText(payrollSummaryList.get(0).getNext_payroll().getCalculationMethod());
                break;
            case 3:
                viewHolder.textview1.setText("Payment Amount");
                if(payrollTransactionsList!=null && payrollTransactionsList.get(0).getPersonalWithholding()!=null) {
                    viewHolder.textview2.setText("Gross Pay: $" +
                            NumberFormatUtils.getAmountWithCommas(String.format("%.2f"
                                    , payrollTransactionsList.get(0).getPersonalWithholding().getGrossAmount().getAmountYtd())));
                    viewHolder.textview3.setText("Net Pay: $" +
                            NumberFormatUtils.getAmountWithCommas(String.format("%.2f"
                                    , payrollTransactionsList.get(0).getPersonalWithholding().getNetAmount().getAmountYtd())));
                    viewHolder.textview4.setText("Projected business account balance \nafter payment: $"+
                    NumberFormatUtils.getAmountWithCommas(String.format("%.2f",payrollSummaryList.get(0).getBalance())));
                }viewHolder.textview3.setVisibility(View.VISIBLE);
                viewHolder.textview4.setVisibility(View.VISIBLE);
                viewHolder.textview5.setVisibility(View.VISIBLE);

                break;
            case 4:
                viewHolder.textview1.setText("Payment Disbursment");
                viewHolder.textview2.setText("$400 to Savings");
                viewHolder.textview3.setText("Remainder to Cap one 5678");
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.textview4.setVisibility(View.GONE);
                viewHolder.textview5.setVisibility(View.GONE);
                viewHolder.textview3.setVisibility(View.VISIBLE);
                break;

        }
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
            itemCount = 5;
        }
        return itemCount;
    }

    public void updateDataSource(List<PayrollSummary> fields,List<PayrollTransactions> transactionsList) {
        this.payrollSummaryList = (ArrayList<PayrollSummary>) fields;
        this.payrollTransactionsList=(ArrayList<PayrollTransactions>)transactionsList;
        notifyDataSetChanged();
    }
    public class PreviousPaymentViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView textview1,textview2,textview3,textview4,textview5;
        public ImageView imageView;

        public PreviousPaymentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imageView=(ImageView)itemView.findViewById(R.id.editimageview);
            this.textview1=(TextView)itemView.findViewById(R.id.textView1);
            this.textview2=(TextView)itemView.findViewById(R.id.textView2);
            this.textview3=(TextView)itemView.findViewById(R.id.textView3);
            this.textview4=(TextView)itemView.findViewById(R.id.textView4);
            this.textview5=(TextView)itemView.findViewById(R.id.textView5);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPaymentDetails(getPosition());
                }
            });
            this.textview5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ViewEstimatedWithHolding.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("summaryList",payrollTransactionsList);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void editPaymentDetails(int position){

        switch (position){

            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:{

            }
        }
    }
}

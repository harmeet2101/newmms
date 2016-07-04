package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollField;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

/**
 * Created by MboAdil on 29/6/16.
 */
public class PayrollRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__NORMAL = 2;

    private static final String PAYROLL_BUSINESS_ACCOUNT = "Business Account Balance";
    private static final String PAYROLL_NEXT_PAYMENT = "Next Payment";
    private static final String PAYROLL_LAST_PAYMENT = "Last Payment";

    private Context context;
    private List<PayrollField> payrollFields;
    public PayrollRecyclerViewAdapter(Context context,List<PayrollField> payrollFields) {

        this.context=context;
        this.payrollFields=payrollFields;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_VIEW_TYPE__LOADING : {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list_simple, parent, false);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__EMPTY_LIST : {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list, parent, false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText(R.string.mbo_dashboard_revenue_empty_list);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            case ITEM_VIEW_TYPE__NORMAL : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dashboard_payroll, parent, false);
                viewHolder = new PayrollViewHolder(itemView);
                break;
            }

            default : {
                viewHolder = null;
            }

        }
        return viewHolder;
    }


    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
          bindViewHolder_Normal((PayrollViewHolder) viewHolder, position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (payrollFields == null) {
            count = 1;
        } else if (payrollFields.isEmpty()) {
            count = 1;
        } else {
            count = payrollFields.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = 2;
        if (payrollFields == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (payrollFields.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        } else {
            itemViewType = ITEM_VIEW_TYPE__NORMAL;
        }
        return itemViewType;
    }

    private int getPayrollImageId(String payroll) {
        int imageId = 0;
        switch (payroll) {
            case PAYROLL_BUSINESS_ACCOUNT: {
                imageId = R.drawable.ic_payroll_business_account_balance;
                break;
            }

            case PAYROLL_NEXT_PAYMENT : {
                imageId = R.drawable.ic_payroll_next_payment;
                break;
            }

            case PAYROLL_LAST_PAYMENT : {
                imageId = R.drawable.ic_payroll_last_payment;
                break;
            }

            default : {
                imageId = R.drawable.dashboard_logo;
            }
        }
        return imageId;
    };

    public void bindViewHolder_Normal(PayrollViewHolder viewHolder, int position) {

        PayrollField payrollField=payrollFields.get(position);
        viewHolder.payrollImageView.setImageResource(getPayrollImageId(payrollField.getName()));
        viewHolder.company_name_TextView.setText(payrollField.getName());
        boolean visibilityFlag=payrollField.getIsVisible();
        if (visibilityFlag)
            viewHolder.includeView.setVisibility(View.VISIBLE);
        else
            viewHolder.includeView.setVisibility(View.INVISIBLE);
        viewHolder.work_order_name_TextView.setText(payrollField.getBalanceField());
        viewHolder.periodTextview.setText(payrollField.getTimePeriodField());
    }
    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class PayrollViewHolder extends RecyclerView.ViewHolder {

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public View includeView;
        public PayrollViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
        }
    }
}
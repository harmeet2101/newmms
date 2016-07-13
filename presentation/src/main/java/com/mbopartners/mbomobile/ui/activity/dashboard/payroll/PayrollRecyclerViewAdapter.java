package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollField;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.DateUtil;

import java.util.List;

/**
 * Created by MboAdil on 29/6/16.
 */
public class PayrollRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__BUSINESS_CENTER = 2;
    private static final int ITEM_VIEW_TYPE__NEXT_PAYROLL = 3;
    private static final int ITEM_VIEW_TYPE__LAST_PAYROLL = 4;

    private static final String PAYROLL_BUSINESS_ACCOUNT = "Business Account Balance";
    private static final String PAYROLL_NEXT_PAYMENT = "Next Payment";
    private static final String PAYROLL_LAST_PAYMENT = "Last Payment";

    private Context context;
    private List<PayrollField> payrollFields;

    private List<PayrollSummary> payrollSummaryList;

    public PayrollRecyclerViewAdapter(Context context,List<PayrollSummary> payrollSummaryList) {

        this.context=context;
        this.payrollSummaryList=payrollSummaryList;
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
            case ITEM_VIEW_TYPE__BUSINESS_CENTER : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dashboard_payroll, parent, false);
                viewHolder = new BusinessCenterViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__NEXT_PAYROLL : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dashboard_payroll, parent, false);
                viewHolder = new NextPayrollViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__LAST_PAYROLL : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dashboard_payroll, parent, false);
                viewHolder = new LastPayrollViewHolder(itemView);
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
        if (payrollSummaryList == null) {}
        else if (payrollSummaryList.isEmpty()) {}
        else if(position==0)
            bindViewHolder_BusinessCenter((BusinessCenterViewHolder)viewHolder,position);
       else if(position==1)
            bindViewHolder_Next_Payroll((NextPayrollViewHolder) viewHolder, position);
        /*else if(position==2)
            bindViewHolder_Last_Payroll((LastPayrollViewHolder) viewHolder, position);*/

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (payrollSummaryList == null) {
            count = 1;
        } else if (payrollSummaryList.isEmpty()) {
            count = 1;
        } else if(payrollSummaryList.get(0).getNext_payroll()==null||payrollSummaryList.get(0).getLast_payroll()==null){
            count = 2;
        }
        return count;
    }

    public void updateDataSource(List<PayrollSummary> fields) {
        this.payrollSummaryList = fields;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        if (payrollSummaryList == null) {
           //return itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (payrollSummaryList.isEmpty()) {
           // return itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        }else if(position==0)
            itemViewType=ITEM_VIEW_TYPE__BUSINESS_CENTER;
        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__NEXT_PAYROLL;
        /*if (position==2)
            return itemViewType=ITEM_VIEW_TYPE__LAST_PAYROLL;*/
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

    public void bindViewHolder_BusinessCenter(BusinessCenterViewHolder viewHolder, int position) {

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_BUSINESS_ACCOUNT));
        viewHolder.company_name_TextView.setText(PAYROLL_BUSINESS_ACCOUNT);
        viewHolder.work_order_name_TextView.setText("$"+payrollSummaryList.get(0).getBalance().toString());
        viewHolder.includeView.setVisibility(View.GONE);
    }
    public void bindViewHolder_Next_Payroll(NextPayrollViewHolder viewHolder, int position) {


        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_NEXT_PAYMENT));
        viewHolder.company_name_TextView.setText(PAYROLL_NEXT_PAYMENT);
        viewHolder.work_order_name_TextView.setText("Unscheduled");
        //viewHolder.periodTextview.setText(payrollField.getTimePeriodField());

    }
    public void bindViewHolder_Last_Payroll(LastPayrollViewHolder viewHolder, int position) {

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_LAST_PAYMENT));
        viewHolder.company_name_TextView.setText(PAYROLL_LAST_PAYMENT);
        //viewHolder.work_order_name_TextView.setText("$" + payrollSummaryList.get(payrollSummaryList.size() - 1).getNext_payroll().getAmount().toString());
        //viewHolder.periodTextview.setText(DateUtil.getDateFormatted_payroll(payrollSummaryList.get(payrollSummaryList.size()-1).getNext_payroll().getStartDate()));
        viewHolder.includeView.setVisibility(View.GONE);
    }
    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class BusinessCenterViewHolder extends RecyclerView.ViewHolder {

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public View includeView;
        public BusinessCenterViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
        }
    }

    public static class NextPayrollViewHolder extends RecyclerView.ViewHolder {

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public View includeView;
        public NextPayrollViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
        }
    }

    public static class LastPayrollViewHolder extends RecyclerView.ViewHolder {

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public View includeView;
        public LastPayrollViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
        }
    }
}

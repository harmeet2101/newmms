package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;

/**
 * Created by MboAdil on 20/7/16.
 */
public class PersonWithholdingsRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;

    private static final int ITEM_VIEW_TYPE__EARNINGS = 2;
    private static final int ITEM_VIEW_TYPE__DEDUCTIONS = 3;
    private static final int ITEM_VIEW_TYPE__BUSINESS_CENTER_PAYROLL = 4;
    private static final String EARNINGS = "Earnings";
    private static final String DEDUCTIONS = "Deductions";
    private static final String BUSINESS_CENTER_PAYROLL = "Business Center Payroll Taxes";
    public PersonWithholdingsRecylerViewAdapter(Context context) {

        this.context=context;
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
            }case ITEM_VIEW_TYPE__EARNINGS : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_businessholdings_list_items, parent, false);
                viewHolder = new BusinessCenterViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__DEDUCTIONS : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_businessholdings_list_items, parent, false);
                viewHolder = new NextPayrollViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__BUSINESS_CENTER_PAYROLL : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_personwithholdings_include_view, parent, false);
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

        if(position==0)
            bindViewHolder_BusinessCenter((BusinessCenterViewHolder)viewHolder,position);
        else if(position==1)
            bindViewHolder_Next_Payroll((NextPayrollViewHolder) viewHolder, position);
        else if(position==2)
            bindViewHolder_Last_Payroll((LastPayrollViewHolder)viewHolder,position);
    }

    @Override
    public int getItemCount() {
        int count = 3;
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        /*if (payrollSummaryList == null) {
            return itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (payrollSummaryList.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        }*/
        if(position==0)
            return itemViewType=ITEM_VIEW_TYPE__EARNINGS;
        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__DEDUCTIONS;
        else if(position==2)
            return itemViewType=ITEM_VIEW_TYPE__BUSINESS_CENTER_PAYROLL;
        else
            return itemViewType;
    }

    private int getPayrollImageId(String payroll) {
        int imageId = 0;
        switch (payroll) {
            case EARNINGS: {
                imageId = R.drawable.ic_payroll_earnings;
                break;
            }

            case DEDUCTIONS : {
                imageId = R.drawable.ic_payroll_deductions;
                break;
            }


            default : {
                imageId = R.drawable.dashboard_logo;
            }
        }
        return imageId;
    };


    public void bindViewHolder_BusinessCenter(BusinessCenterViewHolder viewHolder, int position) {

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(EARNINGS));
        viewHolder.company_name_TextView.setText(EARNINGS);
        viewHolder.work_order_name_TextView.setText("$XXX.XX");
        viewHolder.periodTextview.setText("This Period");

    }
    public void bindViewHolder_Next_Payroll(NextPayrollViewHolder viewHolder, int position) {


        viewHolder.payrollImageView.setImageResource(getPayrollImageId(DEDUCTIONS));
        viewHolder.company_name_TextView.setText(DEDUCTIONS);
        viewHolder.work_order_name_TextView.setText("$XXX.XX");
        viewHolder.periodTextview.setText("This Period");


    }
    public void bindViewHolder_Last_Payroll(LastPayrollViewHolder viewHolder, int position) {


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
        public BusinessCenterViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
        }
    }

    public class NextPayrollViewHolder extends RecyclerView.ViewHolder{

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public NextPayrollViewHolder(View itemView) {
            super(itemView);

            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
        }

    }

    public static class LastPayrollViewHolder extends RecyclerView.ViewHolder {



        public LastPayrollViewHolder(View itemView) {
            super(itemView);

        }
    }


}

package com.mbopartners.mbomobile.ui.activity.dashboard.payroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollField;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.NumberFormatUtils;

import java.text.DateFormat;
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
    private static final int ITEM_VIEW_TYPE__NEED_FUNDS = 5;

    private static final String PAYROLL_BUSINESS_ACCOUNT = "Business Account Balance";
    private static final String PAYROLL_NEXT_PAYMENT = "Next Payment";
    private static final String PAYROLL_LAST_PAYMENT = "Last Payment";

    private Context context;
    private List<PayrollField> payrollFields;

    private List<PayrollSummary> payrollSummaryList;
    private List<PayrollTransactions> payrollTransactionsList;
    private IPreviousCallbackListener iPreviousCallbackListener;
    private IPaymentDetailsListener iPaymentDetailsListener;
    private String card_1_amount,card_2_amount,card_3_amount;
    private DateFormat  dateFormat;


    public PayrollRecyclerViewAdapter(Context context,List<PayrollSummary> payrollSummaryList) {

        this.context=context;
        this.payrollSummaryList=payrollSummaryList;
        this.iPreviousCallbackListener=(IPreviousCallbackListener)context;
        this.iPaymentDetailsListener=(IPaymentDetailsListener)context;
    }

    public PayrollRecyclerViewAdapter(Context context,List<PayrollSummary> payrollSummaryList,List<PayrollTransactions> payrollTransactionsList) {

        this.context=context;
        this.payrollSummaryList=payrollSummaryList;
        this.iPreviousCallbackListener=(IPreviousCallbackListener)context;
        this.payrollTransactionsList=payrollTransactionsList;
        this.iPaymentDetailsListener=(IPaymentDetailsListener)context;
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
            case ITEM_VIEW_TYPE__NEED_FUNDS : {
                View  itemView_funds = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_payroll_need_funds_text_layout, parent, false);
                viewHolder = new BulkViewHolder(itemView_funds);
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
        /*if (payrollSummaryList == null) {}
        else if (payrollSummaryList.isEmpty()) {}
        else  if(position==0)
            bindViewHolder_BusinessCenter((BusinessCenterViewHolder)viewHolder,position);
       else if(position==1)
            bindViewHolder_Next_Payroll((NextPayrollViewHolder) viewHolder, position);
        else if(position==2)
            bindViewHolder_Last_Payroll((LastPayrollViewHolder)viewHolder,position);
        else if(position==3)
        {

        }*/
        if (payrollTransactionsList == null) {}
        else if (payrollTransactionsList.isEmpty()) {}
        else  if(position==0)
            bindViewHolder_BusinessCenter((BusinessCenterViewHolder)viewHolder,position);
        else if(position==1)
            bindViewHolder_Next_Payroll((NextPayrollViewHolder) viewHolder, position);
        else if(position==2)
            bindViewHolder_Last_Payroll((LastPayrollViewHolder)viewHolder,position);
        else if(position==3)
        {

        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        /*if (payrollSummaryList == null) {
            count = 1;
        } else if (payrollSummaryList.isEmpty()) {
            count = 1;
        } else
            count = 4;

        return count;*/

        if (payrollTransactionsList == null) {
            count = 1;
        } else if (payrollTransactionsList.isEmpty()) {
            count = 1;
        } else
            count = 4;

        return count;
    }

    public void updateDataSource(List<PayrollSummary> fields,List<PayrollTransactions> transactions) {
        this.payrollSummaryList = fields;
        this.payrollTransactionsList=transactions;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        /*if (payrollSummaryList == null) {
           return itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (payrollSummaryList.isEmpty()) {
           itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        }else if(position==0)
            return itemViewType=ITEM_VIEW_TYPE__BUSINESS_CENTER;


        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__NEXT_PAYROLL;
        else if(position==2)
            return itemViewType=ITEM_VIEW_TYPE__LAST_PAYROLL;
        else if(position==3){
            return itemViewType=ITEM_VIEW_TYPE__NEED_FUNDS;
        }*/
        if (payrollTransactionsList == null) {
            return itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (payrollTransactionsList.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        }else if(position==0)
            return itemViewType=ITEM_VIEW_TYPE__BUSINESS_CENTER;


        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__NEXT_PAYROLL;
        else if(position==2)
            return itemViewType=ITEM_VIEW_TYPE__LAST_PAYROLL;
        else if(position==3){
            return itemViewType=ITEM_VIEW_TYPE__NEED_FUNDS;
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

    public void bindViewHolder_BusinessCenter(BusinessCenterViewHolder viewHolder, int position) {

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_BUSINESS_ACCOUNT));
        viewHolder.company_name_TextView.setText(PAYROLL_BUSINESS_ACCOUNT);
        if(payrollSummaryList!=null && payrollSummaryList.get(0).getBalance()!=null) {
            String temp = String.format("%.2f", payrollSummaryList.get(0).getBalance());
        /*card_1_amount=getAmount_uptoTwoDecimalPlaces(payrollSummaryList.get(0).getBalance().toString());*/
            card_1_amount = NumberFormatUtils.getAmountWithCommas(temp);
        }
        if(card_1_amount!=null)
        viewHolder.work_order_name_TextView.setText("$" + card_1_amount);
        else
            viewHolder.work_order_name_TextView.setText("N/A");
        viewHolder.includeView.setVisibility(View.GONE);
    }
    public void bindViewHolder_Next_Payroll(NextPayrollViewHolder viewHolder, int position) {


        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_NEXT_PAYMENT));
        viewHolder.company_name_TextView.setText(PAYROLL_NEXT_PAYMENT);
        try{
            if(payrollSummaryList!=null && payrollSummaryList.get(0).getNext_payroll()!=null &&
                    payrollSummaryList.get(0).getNext_payroll().getAmount()!=null||
                    payrollSummaryList.get(0).getNext_payroll().getAmount()!=0)
            {
                //card_2_amount=getAmount_uptoTwoDecimalPlaces(payrollSummaryList.get(0).getNext_payroll().getAmount().toString());
                String temp=String.format("%.2f",payrollSummaryList.get(0).getNext_payroll().getAmount());
                card_2_amount=NumberFormatUtils.getAmountWithCommas(temp); ;
            viewHolder.work_order_name_TextView.setText("$" + card_2_amount);
            viewHolder.includeView.setVisibility(View.GONE);

            }else {
            viewHolder.work_order_name_TextView.setText("Unscheduled");
            viewHolder.includeView.setVisibility(View.VISIBLE);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            viewHolder.work_order_name_TextView.setText("Unscheduled");
            viewHolder.includeView.setVisibility(View.VISIBLE);
        }

    }
    public void bindViewHolder_Last_Payroll(LastPayrollViewHolder viewHolder, int position) {

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(PAYROLL_LAST_PAYMENT));
        viewHolder.company_name_TextView.setText(PAYROLL_LAST_PAYMENT);
        viewHolder.includeView.setVisibility(View.GONE);
        /*if(payrollSummaryList.get(0).getLast_payroll()!=null&&
                payrollSummaryList.get(0).getLast_payroll().getBusinessWithholding() != null) {
            card_3_amount=getAmount_uptoTwoDecimalPlaces(String.valueOf(payrollSummaryList.get(0).getLast_payroll().
                    getBusinessWithholding().getPayrollAmount().getAmount()));

                viewHolder.work_order_name_TextView.setText("$" +card_3_amount);
        }else
            viewHolder.work_order_name_TextView.setText("N/A");*/

        if(payrollTransactionsList!=null&& payrollTransactionsList.get(0)!=null &&
                payrollTransactionsList.get(0).getBusinessWithholding() != null) {
            String temp=String.format("%.2f",payrollTransactionsList.get(0).getBusinessWithholding().getPayrollAmount().getAmount());
            //card_3_amount=getAmount_uptoTwoDecimalPlaces(String.valueOf(payrollTransactionsList.get(0).getBusinessWithholding().getPayrollAmount().getAmount()));
            card_3_amount=NumberFormatUtils.getAmountWithCommas(temp);
            viewHolder.work_order_name_TextView.setText("$" +card_3_amount);
        }else
            viewHolder.work_order_name_TextView.setText("N/A");
    }
    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BusinessCenterViewHolder extends RecyclerView.ViewHolder {

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

    public class NextPayrollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            this.itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            iPaymentDetailsListener.callPaymentDetails();
        }


    }

    public  class LastPayrollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            this.itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            iPreviousCallbackListener.callbackPrevious();
        }

    }


    public interface IPreviousCallbackListener{
        void callbackPrevious();
    }


    public interface IPaymentDetailsListener{
        void callPaymentDetails();
    }
    public String getAmount_uptoTwoDecimalPlaces(String amount)
    {
        int dotPos = -1;

        for (int i = 0; i < amount.length(); i++) {
            char c = amount.charAt(i);
            if (c == '.') {
                dotPos = i;
            }
        }
        /*Making Money  get trailing values after the decimal*/

        if (dotPos == -1&& !amount.equals("")){
            amount=amount + ".00";
        }else if(dotPos == -1&& amount.equals(""))
            amount=amount + "0.00";
        else if(dotPos==amount.length()-1 && !amount.equals(""))
            amount="0"+amount + "00";
        else if(dotPos==amount.length()-2 && !amount.equals(""))
            amount=amount + "0";

        return amount;
    }
}

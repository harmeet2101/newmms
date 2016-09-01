package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessExpenses;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessPayrollTaxes;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.dashboard.payroll.BusinessDeductionsRecyclerViewAdapter;
import com.mbopartners.mbomobile.ui.util.NumberFormatUtils;

import java.util.List;

public class BusinessViewEstimatedRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;

    private static final int ITEM_VIEW_TYPE__BUSINESS_CENTER_IMPACT = 2;
    private static final int ITEM_VIEW_TYPE__DEDUCTIONS = 3;
    private static final String BUSINESS_CENTER_IMPACT = "Business Center Impact";
    private static final String DEDUCTIONS = "Deductions";
    private List<BusinessWithHolding> businessWithHoldingList;
    private int position;
    int mheight;
    public boolean isBusiness_ytd_checked,isExpense_ytd_checked,isDeductions_ytd_checked;
    public BusinessViewEstimatedRecycleViewAdapter(Context context) {

        this.context=context;
    }
    public BusinessViewEstimatedRecycleViewAdapter(Context context,List<BusinessWithHolding> businessWithHoldingList,int position) {

        this.context=context;
        this.businessWithHoldingList=businessWithHoldingList;
        this.position=position;
        WindowManager windowmanager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dimension = new DisplayMetrics();
        windowmanager.getDefaultDisplay().getMetrics(dimension);
        mheight = dimension.heightPixels;
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
            }case ITEM_VIEW_TYPE__BUSINESS_CENTER_IMPACT : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_businessholdings_list_items, parent, false);
                viewHolder = new BusinessCenterViewHolder(itemView);
                break;
            }

            case ITEM_VIEW_TYPE__DEDUCTIONS : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_businessholdings_list_items_test, parent, false);
                viewHolder = new BusinessDeductionsViewHolder(itemView);
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
            bindViewHolder_business_deduction((BusinessDeductionsViewHolder) viewHolder, position);

    }

    @Override
    public int getItemCount() {
        int count = 2;
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
            return itemViewType=ITEM_VIEW_TYPE__BUSINESS_CENTER_IMPACT;
        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__DEDUCTIONS;

        return itemViewType;
    }

    private int getPayrollImageId(String payroll) {
        int imageId = 0;
        switch (payroll) {
            case BUSINESS_CENTER_IMPACT: {
                imageId = R.drawable.ic_payroll_business_center_payroll_taxes;
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

        viewHolder.payrollImageView.setImageResource(getPayrollImageId(BUSINESS_CENTER_IMPACT));
        viewHolder.company_name_TextView.setText(BUSINESS_CENTER_IMPACT);
        viewHolder.periodTextview.setText("This Period");
        viewHolder.textview1.setText("Expenses");
        viewHolder.textview2.setText("Deductions");
        viewHolder.textview3.setText("Gross Pay");
        viewHolder.work_order_name_TextView.setText("$" +NumberFormatUtils.getAmountWithCommas(
                String.format("%.2f", businessWithHoldingList.get(this.position).getPayrollAmount().getAmount())));
        viewHolder.periodTextview.setText("This Period");
        viewHolder.textview1_value.setText("$" + NumberFormatUtils.getAmountWithCommas(
                String.format("%.2f", getSumOfInsuranceExpenses(businessWithHoldingList.get(position)
                        .getBusinessExpenses(), isBusiness_ytd_checked))));
        viewHolder.textview2_value.setText("$" +NumberFormatUtils.getAmountWithCommas(
                String.format("%.2f", getSumOfPayrollTaxes(businessWithHoldingList.get(position)
                        .getPayrollTaxes(), isBusiness_ytd_checked))));
        viewHolder.textview3_value.setText("$" +NumberFormatUtils.getAmountWithCommas(
                String.format("%.2f", (businessWithHoldingList.get(position).getPayrollAmount().getAmount()))));

    }


    public void bindViewHolder_business_deduction(BusinessDeductionsViewHolder viewHolder, int position) {


        viewHolder.payrollImageView.setImageResource(getPayrollImageId(DEDUCTIONS));
        viewHolder.company_name_TextView.setText(DEDUCTIONS);

        viewHolder.periodTextview.setText("This Period");

            viewHolder.work_order_name_TextView.setText("$" + NumberFormatUtils.getAmountWithCommas(
                    String.format("%.2f",
                            getSumOfPayrollTaxes(businessWithHoldingList.get(this.position).getPayrollTaxes(), isDeductions_ytd_checked))));

    }




    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public  class BusinessCenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public CardView cardView;
        public View includeView;
        public int minHeight;
        public TextView textview1;
        public TextView textview1_value;
        public TextView textview2;
        public TextView textview2_value;
        public TextView textview3;
        public TextView textview3_value;
        public TextView thisPeriodTextview;
        public TextView ytdTextview;
        public Switch aSwitch;
        public BusinessCenterViewHolder(View itemView) {
            super(itemView);
            this.cardView=(CardView)itemView.findViewById(R.id.card_view);
            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.textview1=(TextView)itemView.findViewById(R.id.textview1);
            this.textview1_value=(TextView)itemView.findViewById(R.id.textview1_value);
            this.textview2=(TextView)itemView.findViewById(R.id.textview2);
            this.textview2_value=(TextView)itemView.findViewById(R.id.textview2_value);
            this.textview3=(TextView)itemView.findViewById(R.id.textview3);
            this.textview3_value=(TextView)itemView.findViewById(R.id.textview3_value);
            this.includeView=itemView.findViewById(R.id.includeview);
            this.ytdTextview=(TextView)itemView.findViewById(R.id.textview_year);
            this.thisPeriodTextview=(TextView)itemView.findViewById(R.id.textview_thisPeriod);
            this.aSwitch=(Switch)itemView.findViewById(R.id.switchbutton);
            this.aSwitch.setChecked(false);
            this.thisPeriodTextview.setVisibility(View.GONE);
            this.aSwitch.setVisibility(View.GONE);
            this.ytdTextview.setVisibility(View.GONE);

            this.itemView.setOnClickListener(this);
            cardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    cardView.getViewTreeObserver().removeOnPreDrawListener(this);
                    minHeight = cardView.getHeight();
                    ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                    layoutParams.height = minHeight;
                    cardView.setLayoutParams(layoutParams);

                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(includeView.getVisibility()==View.GONE)
            {
                expandView(mheight,cardView,includeView);

            }
            else if(includeView.getVisibility()==View.VISIBLE)
                collapseView(cardView,minHeight,includeView);

        }
    }



    public void collapseView(final CardView cardView,int minHeight,View includeView) {

        ValueAnimator anim = ValueAnimator.ofInt(cardView.getMeasuredHeightAndState(),
                minHeight);
        includeView.setVisibility(View.GONE);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                layoutParams.height = val;
                cardView.setLayoutParams(layoutParams);

            }
        });
        anim.start();
    }
    public void expandView(int height,final CardView cardView,View includeView) {

        ValueAnimator anim = ValueAnimator.ofInt(cardView.getMeasuredHeightAndState(),
                height);
        includeView.setVisibility(View.VISIBLE);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                layoutParams.height = val;
                cardView.setLayoutParams(layoutParams);
            }
        });
        anim.start();

    }
    public class BusinessDeductionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public CardView cardView;
        public View includeView;
        public int minHeight;
        public TextView textview1;
        public TextView textview1_value;
        public RecyclerView recyclerView;
        public TextView thisPeriodTextview;
        public TextView ytdTextview;
        public Switch aSwitch;
        public BusinessDeductionsViewHolder(View itemView) {
            super(itemView);
            this.cardView=(CardView)itemView.findViewById(R.id.card_view);
            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
            this.recyclerView=(RecyclerView)itemView.findViewById(R.id.recyclerView);
            this.textview1=(TextView)itemView.findViewById(R.id.textview1);
            this.textview1_value=(TextView)itemView.findViewById(R.id.textview1_value);
            this.textview1.setVisibility(View.GONE);
            this.textview1_value.setVisibility(View.GONE);
            final BusinessDeductionsRecyclerViewAdapter madapter=new BusinessDeductionsRecyclerViewAdapter(context,businessWithHoldingList.
                    get(position).getPayrollTaxes());
            this.recyclerView.setAdapter(madapter);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.recyclerView.setScrollContainer(true);
            this.itemView.setOnClickListener(this);
            this.ytdTextview=(TextView)itemView.findViewById(R.id.textview_year);
            this.thisPeriodTextview=(TextView)itemView.findViewById(R.id.textview_thisPeriod);

            this.aSwitch=(Switch)itemView.findViewById(R.id.switchbutton);
            this.aSwitch.setChecked(false);
            this.thisPeriodTextview.setVisibility(View.GONE);
            this.aSwitch.setVisibility(View.GONE);
            this.ytdTextview.setVisibility(View.GONE);
            cardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    cardView.getViewTreeObserver().removeOnPreDrawListener(this);
                    minHeight = cardView.getHeight();
                    ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                    layoutParams.height = minHeight;
                    cardView.setLayoutParams(layoutParams);

                    return true;
                }
            });
        }
        @Override
        public void onClick(final View v) {
            if(includeView.getVisibility() == View.GONE) {

                expandView(mheight,cardView,includeView);

            }
            else if(includeView.getVisibility()==View.VISIBLE)
                collapseView(cardView,minHeight,includeView);
        }

    }


    public double getSumOfInsuranceExpenses(List<BusinessExpenses> businessExpenses, boolean isChecked)
    {
        double total_expense=0.0;

        try {
            if (businessExpenses != null) {
                for (int i = 0; i < businessExpenses.size(); i++) {
                    if(isChecked)
                        total_expense = total_expense + businessExpenses.get(i).getAmountYtd();
                    else
                        total_expense = total_expense + businessExpenses.get(i).getAmount();
                }

            }

        }catch (NullPointerException e)
        {
            e.printStackTrace();
            total_expense=0.0;
        }
        return total_expense;
    }

    private double getSumOfPayrollTaxes(List<BusinessPayrollTaxes>  businessPayrollTaxes, boolean isChecked) {

        double total_expense=0.0;

        try {
            if (businessPayrollTaxes != null) {
                for (int i = 0; i < businessPayrollTaxes.size(); i++) {
                    if (isChecked)
                        total_expense = total_expense + businessPayrollTaxes.get(i).getAmountYtd();
                    else
                        total_expense = total_expense + businessPayrollTaxes.get(i).getAmount();
                }

            }

        }catch (NullPointerException e)
        {
            e.printStackTrace();
            total_expense=0.0;
        }
        return total_expense;
    }
}

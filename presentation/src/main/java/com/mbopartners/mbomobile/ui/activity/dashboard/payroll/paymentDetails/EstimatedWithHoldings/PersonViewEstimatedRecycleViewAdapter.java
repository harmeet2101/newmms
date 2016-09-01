package com.mbopartners.mbomobile.ui.activity.dashboard.payroll.paymentDetails.EstimatedWithHoldings;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.payroll_response.PersonWithHolding;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.NumberFormatUtils;

import java.util.List;

public class PersonViewEstimatedRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;

    public boolean isEarnings_ytd_checked,isExpense_ytd_checked,isDeductions_ytd_checked;
    private static final int ITEM_VIEW_TYPE__EARNINGS = 2;
    private static final int ITEM_VIEW_TYPE__DEDUCTIONS = 3;
    private static final int ITEM_VIEW_TYPE__REIMBERSEMENTS = 4;
    private static final String EARNINGS = "Earnings";
    private static final String DEDUCTIONS = "Deductions";
    private List<PersonWithHolding> personWithHoldingList;
    private int position;
    int mheight;
    public PersonViewEstimatedRecycleViewAdapter(Context context) {

        this.context=context;
    }
    public PersonViewEstimatedRecycleViewAdapter(Context context,List<PersonWithHolding> personWithHoldingList,int position) {

        this.context=context;
        this.personWithHoldingList=personWithHoldingList;
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
            }case ITEM_VIEW_TYPE__EARNINGS : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_businessholdings_list_items, parent, false);
                viewHolder = new BusinessCenterViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__DEDUCTIONS : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_personwithholdings_deductions_card_view, parent, false);
                viewHolder = new NextPayrollViewHolder(itemView);
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
            bindViewHolder_BusinessCenter((BusinessCenterViewHolder) viewHolder, position);
        else if(position==1)
            bindViewHolder_Next_Payroll((NextPayrollViewHolder) viewHolder, position);
    }

    @Override
    public int getItemCount() {
        int count = 2;
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;

        if(position==0)
            return itemViewType=ITEM_VIEW_TYPE__EARNINGS;
        else if(position==1)
            return itemViewType=ITEM_VIEW_TYPE__DEDUCTIONS;
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
        viewHolder.work_order_name_TextView.setText("$" +
                NumberFormatUtils.getAmountWithCommas(String.format("%.2f", personWithHoldingList.get(this.position).getGrossAmount().getAmount())));
        viewHolder.periodTextview.setText("This Period");
        viewHolder.textview1.setText(personWithHoldingList.get(this.position).getGrossAmount().getName());
        viewHolder.textview1_value.setText("$" +
                NumberFormatUtils.getAmountWithCommas(String.format("%.2f", personWithHoldingList.get(this.position).getGrossAmount().getAmount())));
        viewHolder.textview2.setText(personWithHoldingList.get(this.position).getNetAmount().getName());
        viewHolder.textview2_value.setText("$" +
                NumberFormatUtils.getAmountWithCommas(String.format("%.2f", personWithHoldingList.get(this.position).getNetAmount().getAmount())));
        viewHolder.textview3.setText(personWithHoldingList.get(this.position).getPaycheckAmount().getName());
        viewHolder.textview3_value.setText("$" + NumberFormatUtils.getAmountWithCommas(String.format("%.2f",
                (personWithHoldingList.get(this.position).getPaycheckAmount().getAmount() +
                        personWithHoldingList.get(this.position).getNetAmount().getAmount()))));
    }
    public void bindViewHolder_Next_Payroll(NextPayrollViewHolder viewHolder, int position) {


        viewHolder.payrollImageView.setImageResource(getPayrollImageId(DEDUCTIONS));
        viewHolder.company_name_TextView.setText(DEDUCTIONS);
        for(int i=0;i<personWithHoldingList.get(this.position).getPayrollTaxes().size();i++){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder.myView = inflater.inflate(R.layout.layout_holding_recyclerview, null);
            viewHolder.name=(TextView)viewHolder.myView.findViewById(R.id.textview_name);
            viewHolder.value=(TextView)viewHolder.myView.findViewById(R.id.textview_value);
            viewHolder.name.setText(personWithHoldingList.get(this.position).getPayrollTaxes().get(i).getName());
            viewHolder.value.setText("$" +NumberFormatUtils.getAmountWithCommas(
                    String.format("%.2f", personWithHoldingList.get(this.position).getPayrollTaxes().get(i).getAmount())));
            viewHolder.linearLayout.addView(viewHolder.myView);
        }
        for(int i=0;i<personWithHoldingList.get(this.position).getAfterTaxDeductions().size();i++){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder.myView2 = inflater.inflate(R.layout.layout_holding_recyclerview, null);
            viewHolder.name2=(TextView)viewHolder.myView2.findViewById(R.id.textview_name);
            viewHolder.value2=(TextView)viewHolder.myView2.findViewById(R.id.textview_value);
            viewHolder.name2.setText(personWithHoldingList.get(this.position).getAfterTaxDeductions().get(i).getName());
            viewHolder.value2.setText("$" +
                    NumberFormatUtils.getAmountWithCommas(
                            String.format("%.2f", personWithHoldingList.
                                    get(this.position).getAfterTaxDeductions().get(i).getAmount())));
            viewHolder.linearLayout2.addView(viewHolder.myView2);
        }
        viewHolder.textview1.setText("Statutory");
        viewHolder.work_order_name_TextView.setText("$" +
                    NumberFormatUtils.getAmountWithCommas(
                            String.format("%.2f", getSumOfPayrollTaxes(personWithHoldingList, isDeductions_ytd_checked) +
                                    getSumOfAfterTaxDeductions(personWithHoldingList, isDeductions_ytd_checked))));
            viewHolder.periodTextview.setText("This Period");
            viewHolder.textview1_value.setText("$" +
                    NumberFormatUtils.getAmountWithCommas(
                            String.format("%.2f", getSumOfPayrollTaxes(personWithHoldingList, isDeductions_ytd_checked))));
            viewHolder.textview2_value.setText("$" + NumberFormatUtils.getAmountWithCommas(
                    String.format("%.2f", getSumOfAfterTaxDeductions(personWithHoldingList, isDeductions_ytd_checked))));

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
            this.includeView=itemView.findViewById(R.id.includeview);
            this.ytdTextview=(TextView)itemView.findViewById(R.id.textview_year);
            this.thisPeriodTextview=(TextView)itemView.findViewById(R.id.textview_thisPeriod);
            this.aSwitch=(Switch)itemView.findViewById(R.id.switchbutton);
            this.aSwitch.setChecked(false);
            thisPeriodTextview.setVisibility(View.GONE);
            this.ytdTextview.setVisibility(View.GONE);
            this.aSwitch.setVisibility(View.GONE);
            /*this.includeView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mheight=includeView.getHeight();
                    includeView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    includeView.setVisibility(View.GONE);
                    Log.d("includeViewHeight", String.valueOf(mheight));
                    Log.d("cardViewHeight",String.valueOf(cardView.getHeight()));
                }
            });*/

            this.textview1=(TextView)itemView.findViewById(R.id.textview1);
            this.textview1_value=(TextView)itemView.findViewById(R.id.textview1_value);
            this.textview2=(TextView)itemView.findViewById(R.id.textview2);
            this.textview2_value=(TextView)itemView.findViewById(R.id.textview2_value);
            this.textview3=(TextView)itemView.findViewById(R.id.textview3);
            this.textview3_value=(TextView)itemView.findViewById(R.id.textview3_value);

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
/*                relativeLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        expandView(relativeLayout.getHeight()*3,cardView,includeView);
                    }
                });*/
            }
            else if(includeView.getVisibility()==View.VISIBLE)
                collapseView(cardView,minHeight,includeView);
        }
    }

    public class NextPayrollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView payrollImageView;
        public TextView company_name_TextView;
        public TextView work_order_name_TextView;
        public TextView periodTextview;
        public CardView cardView;
        public View includeView;
        public int minHeight;
        public TextView headingTextview;
        public TextView textview1;
        public TextView textview1_value,textview2_value;
        public RecyclerView recyclerView;
        public TextView thisPeriodTextview,name,value,name2,value2;
        public TextView ytdTextview;
        public Switch aSwitch;
        public LinearLayout linearLayout,linearLayout2;
        public View myView,myView2;

        public NextPayrollViewHolder(View itemView) {
            super(itemView);
            this.cardView=(CardView)itemView.findViewById(R.id.card_view);
            this.payrollImageView = (ImageView) itemView.findViewById(R.id.imageview);
            this.company_name_TextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.work_order_name_TextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            this.periodTextview=(TextView)itemView.findViewById(R.id.mbo_timesheet_time_period_TextView);
            this.includeView=itemView.findViewById(R.id.includeview);
            this.textview1=(TextView)itemView.findViewById(R.id.textview1);
            this.textview1_value=(TextView)itemView.findViewById(R.id.textview1_value);
            this.textview2_value=(TextView)itemView.findViewById(R.id.textview2_value);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.parentView);
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.layout_holding_recyclerview, null);
            linearLayout.addView(myView);
            name=(TextView)myView.findViewById(R.id.textview_name);
            value=(TextView)myView.findViewById(R.id.textview_value);
            linearLayout2 = (LinearLayout)itemView.findViewById(R.id.parentView2);
            LayoutInflater inflater2 =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView2 = inflater2.inflate(R.layout.layout_holding_recyclerview, null);
            linearLayout2.addView(myView2);
            name2=(TextView)myView2.findViewById(R.id.textview_name);
            value2=(TextView)myView2.findViewById(R.id.textview_value);
            this.headingTextview=(TextView)itemView.findViewById(R.id.headingNote);
            this.headingTextview.setVisibility(View.VISIBLE);
            this.itemView.setOnClickListener(this);
            /*this.recyclerView=(RecyclerView)itemView.findViewById(R.id.recyclerView);
            final PersonalDeductionsRecyclerViewAdapter madapter=new PersonalDeductionsRecyclerViewAdapter
                    (context,personWithHoldingList.get(position).getAfterTaxDeductions(),personWithHoldingList.get(position).getPayrollTaxes());

            this.recyclerView.setAdapter(madapter);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.recyclerView.setScrollContainer(true);*/
            this.ytdTextview=(TextView)itemView.findViewById(R.id.textview_year);
            this.thisPeriodTextview=(TextView)itemView.findViewById(R.id.textview_thisPeriod);
            this.aSwitch=(Switch)itemView.findViewById(R.id.switchbutton);

            this.aSwitch.setChecked(false);
            thisPeriodTextview.setVisibility(View.GONE);
            this.ytdTextview.setVisibility(View.GONE);
            this.aSwitch.setVisibility(View.GONE);
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
/*                    relativeLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            expandView(relativeLayout.getHeight()*3,cardView,includeView);
                        }
                    });*/


                try {
                    expandView(mheight, cardView, includeView);
                }
                catch (Exception e)
                {

                }
            }
            else if(includeView.getVisibility()==View.VISIBLE)
                collapseView(cardView, minHeight, includeView);
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

    private double getSumOfPayrollTaxes(List<PersonWithHolding> personWithHoldingList,boolean isChecked) {

        double total_expense=0.0;

        try {
            if (personWithHoldingList != null) {
                for (int i = 0; i < personWithHoldingList.get(this.position).getPayrollTaxes().size(); i++) {
                    if(isChecked)
                        total_expense = total_expense + personWithHoldingList.get(this.position).getPayrollTaxes().get(i).getAmountYtd();
                    else
                        total_expense = total_expense + personWithHoldingList.get(this.position).getPayrollTaxes().get(i).getAmount();
                }

            }

        }catch (NullPointerException e)
        {
            e.printStackTrace();
            total_expense=0.0;
        }
        return total_expense;
    }

    private double getSumOfAfterTaxDeductions(List<PersonWithHolding> personWithHoldingList,boolean isChecked) {

        double total_expense=0.0;

        try {
            if (personWithHoldingList != null) {
                for (int i = 0; i < personWithHoldingList.get(this.position).getAfterTaxDeductions().size()-1; i++) {
                    if(isChecked)
                        total_expense = total_expense + personWithHoldingList.get(this.position).getAfterTaxDeductions().get(i).getAmountYtd();
                    else
                        total_expense = total_expense + personWithHoldingList.get(this.position).getAfterTaxDeductions().get(i).getAmount();
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
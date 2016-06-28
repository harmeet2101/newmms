package com.mbopartners.mbomobile.ui.activity.dashboard.expense;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.model.ExpenseTimesheetItem;
import com.mbopartners.mbomobile.ui.util.DateUtil;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.Collections;
import java.util.List;

import ua.com.mobidev.android.framework.util.UiUtils;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FontController{
    private static final String TAG = ExpenseRecyclerViewAdapter.class.getSimpleName();

    private static final int ITEM_TYPE__UNKNOWN = 0;
    private static final int ITEM_TYPE__LOADING_LIST = 1;
    private static final int ITEM_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_TYPE__EXPENSE_ITEM = 3;

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    List<ExpenseTimesheetItem> expenseItemList;
    private OnItemClickListener mItemClickListener;
    private Context context;
    private IExpenseCallbackListener iExpenseCallbackListener;
    private IBottombarVisibility iBottombarVisibility;
    public ExpenseRecyclerViewAdapter(List<ExpenseTimesheetItem> expenseItemList,Context context,ExpensePageFragment expensePageFragment) {
        this.expenseItemList = expenseItemList;
        if (expenseItemList != null) {
            Collections.sort(expenseItemList);
        }
        this.context=context;
        iExpenseCallbackListener=(IExpenseCallbackListener)context;
        iBottombarVisibility = expensePageFragment;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = ITEM_TYPE__UNKNOWN;

        if (expenseItemList == null) {
            viewType = ITEM_TYPE__LOADING_LIST;
        } else if (expenseItemList.isEmpty()) {
            viewType = ITEM_TYPE__EMPTY_LIST;
        }else if (position == 0)
            viewType = ITEM_TYPE_HEADER;
        else
            viewType = ITEM_TYPE__EXPENSE_ITEM;
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ITEM_TYPE__LOADING_LIST: {
                iBottombarVisibility.setVisible(true);                              // Make the bottom bar visible when data is loading or data is not available
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            case ITEM_TYPE__EMPTY_LIST : {
                iBottombarVisibility.setVisible(true);                             // Make the bottom bar visible when data is loading or data is not available
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_timeshseet_list, parent, false);
                ((TextView) view.findViewById(R.id.empty_section_TextView)).setText(R.string.mbo_dashboard_expense_empty_list);
                fillParent(parent, view);
                viewHolder = new BulkViewHolder(view);
                break;
            }
            /* Adding header which contains New Expense button */
            case ITEM_TYPE_HEADER:{
                iBottombarVisibility.setVisible(false);                            // Make the bottom bar invisible when data is loaded and data is available
                final View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_expense_header,parent,false);
                viewHolder = new ExpenseHeader(view1);
                break;
            }
            case ITEM_TYPE__EXPENSE_ITEM: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_expense, parent, false);
                viewHolder = new ExpenseViewHolder(view);
                break;
            }

            default : {
                viewHolder = null;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case ITEM_TYPE__LOADING_LIST: {
                break;
            }
            case ITEM_TYPE__EMPTY_LIST: {
                break;
            }
            /* Adding heading which contains New Expense Button*/
            case ITEM_TYPE_HEADER:{
                break;
            }
            case ITEM_TYPE__EXPENSE_ITEM: {
                ExpenseViewHolder expenseHolder = (ExpenseViewHolder) holder;

                ExpenseTimesheetItem item = expenseItemList.get(position - 1);  //-1 is due to position is 1 not zero

                /*if (position ==0){
                    expenseHolder.includeView.setVisibility(View.VISIBLE);
                    //edit for git add
                }
                else
                {
                    expenseHolder.includeView.setVisibility(View.GONE);
                }*/
                //expenseHolder.expenseNameTextView.setText(item.getExpenseTypeName());
                String currencySignature = expenseHolder.amountTextView.getContext().getString(R.string.currency_signature);
                expenseHolder.amountTextView.setText(currencySignature + item.getAmount());
                if(item.isEditable()) {
                    expenseHolder.itemView.setAlpha((float)1.0);
                }else {
                    expenseHolder.itemView.setAlpha((float)0.5);
                }
                expenseHolder.vendorTextView.setText(item.getVendor());
                expenseHolder.expenseTypeTextView.setText(item.getExpenseTypeName());
                expenseHolder.workOrderNameTextView.setText(item.getWorkOrderName());

                if(item.getLastChangedDate()!=null)
                    expenseHolder.startDateTextview.setText(DateUtil.getFullFormattedAsString(item.getVirtualDate()) + " - " + DateUtil.parseFrom_yyyymmdd01(item.getLastChangedDate()));
                else
                    expenseHolder.startDateTextview.setText( DateUtil.getFullFormattedAsString(item.getVirtualDate()));
                UiUtils.disableEnableTreeLeafs(item.isEditable(), expenseHolder.itemView);
                break;
            }
            default: {
            }
        }
    }

    @Override
    public int getItemCount() {
        if (expenseItemList == null || expenseItemList.isEmpty()) {
            return 1;
        } else {
            return expenseItemList.size()+1;        // Adding 1 is due to header we need for the layout
        }
    }

    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }

    public void updateDataSource(List<ExpenseTimesheetItem> fields) {
        this.expenseItemList = fields;
        if (this.expenseItemList != null) {
            Collections.sort(this.expenseItemList);
        }
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(String expenseMboId);
    }

    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
            /*Make the bottom bar visible when data is loading or data is not available */
//            iBottombarVisibility.setVisible(true);

        }
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View itemView;
        //public TextView expenseNameTextView;
        public TextView amountTextView;
        public TextView vendorTextView;
        public TextView expenseTypeTextView;
        public TextView workOrderNameTextView;
        //public ImageView expenseImage;
        public TextView startDateTextview;
        public View includeView;
        public TextView RecentExpensesTextview;
        public TextView expenseButton;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iBottombarVisibility.setVisible(false);
            this.includeView=itemView.findViewById(R.id.includeview);
            //this.expenseButton=(TextView)includeView.findViewById(R.id.newExpenseButton);
            //this.expenseNameTextView = (TextView) itemView.findViewById(R.id.expense_name_TextView);
            this.amountTextView = (TextView) itemView.findViewById(R.id.amount_TextView);
            this.vendorTextView = (TextView) itemView.findViewById(R.id.vendor_TextView);
            //this.RecentExpensesTextview = (TextView) itemView.findViewById(R.id.mbo_weekly_timesheet_CompanyName_TextView);
            this.expenseTypeTextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.workOrderNameTextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            //this.expenseImage = (ImageView) itemView.findViewById(R.id.expense_ImageView);
            this.startDateTextview=(TextView)itemView.findViewById(R.id.work_order_startDate_TextView);
            setFont((View)amountTextView,(View)vendorTextView,(View)expenseTypeTextView,(View)workOrderNameTextView);
            itemView.setOnClickListener(this);
            /*this.expenseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iExpenseCallbackListener.callbackExpense();
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ExpenseTimesheetItem expenseTimesheetItem = expenseItemList.get(position - 1);
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(expenseTimesheetItem.getExpenseMboId());
            }

        }
    }
    public  class ExpenseHeader extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView expenseButton;
        public ExpenseHeader(View view){
            super(view);
            itemView = view;
            this.expenseButton=(TextView)itemView.findViewById(R.id.newExpenseButton);
            this.expenseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iExpenseCallbackListener.callbackExpense();
            }
            });
        }
    }

    @Override
    public void setFont(View... args) {

        TextView textView1=(TextView)args[0];
        TextView textView2=(TextView)args[1];
        TextView textView3=(TextView)args[2];
        TextView textView4=(TextView)args[3];
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"font/Roboto-Medium.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(),"font/Roboto-Regular.ttf");
        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface2);
        textView4.setTypeface(typeface2);
    }


    public interface IExpenseCallbackListener{
        public void callbackExpense();
    }
/*Creating the interface to enable or disable the bottom bar*/
    public interface IBottombarVisibility{
        void setVisible(boolean flag);
}
}

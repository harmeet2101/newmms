package com.mbopartners.mbomobile.ui.activity.dashboard.revenue;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.DashboardField;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.List;

public class RevenueRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FontController{

    private static final int ITEM_VIEW_TYPE__LOADING = 0;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 1;
    private static final int ITEM_VIEW_TYPE__NORMAL = 2;

    private static final String REVENUE_BILLABLE__UPPER_CASE = "BILLABLE";
    private static final String REVENUE_INVOICED__UPPER_CASE = "INVOICED";
    private static final String REVENUE_PAID__UPPER_CASE = "PAID";

    private List<DashboardField> fields;
    private String period;
    private Context context;
    public RevenueRecyclerViewAdapter(Context context,List<DashboardField> fields, String period) {
        this.fields = fields;
        this.period = period;
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
            }
            case ITEM_VIEW_TYPE__NORMAL : {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dashboard_revenue, parent, false);
                viewHolder = new RevenueViewHolder(itemView);
                break;
            }

            default : {
                viewHolder = null;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (fields == null) {

        } else if (fields.isEmpty()) {

        } else {
            bindViewHolder_Normal((RevenueViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (fields == null) {
            count = 1;
        } else if (fields.isEmpty()) {
            count = 1;
        } else {
            count = fields.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        if (fields == null) {
            itemViewType = ITEM_VIEW_TYPE__LOADING;
        } else if (fields.isEmpty()) {
            itemViewType = ITEM_VIEW_TYPE__EMPTY_LIST;
        } else {
            itemViewType = ITEM_VIEW_TYPE__NORMAL;
        }
        return itemViewType;
    }

    public void updateDataSource(List<DashboardField> fields, String period) {
        this.fields = fields;
        this.period = period;
        notifyDataSetChanged();
    }

    public void bindViewHolder_Loading(RecyclerView.ViewHolder viewHolder) {

    }

    public void bindViewHolder_EmptyList(RecyclerView.ViewHolder viewHolder) {

    }

    public void bindViewHolder_Normal(RevenueViewHolder viewHolder, int position) {
        DashboardField singleField = fields.get(position);
        viewHolder.moneyImageView.setImageResource(getRevenueImageId(singleField.getName()));
        viewHolder.moneyTypeTextView.setText(singleField.getName());
        setFont(viewHolder.moneyTypeTextView, viewHolder.moneyAmountTextView,viewHolder.periodTextView);

        String moneyAmount = viewHolder.moneyAmountTextView.getContext().getString(R.string.currency_signature)
                +  singleField.getValue();
        viewHolder.moneyAmountTextView.setText(moneyAmount);
        if(position==2)
        {
            viewHolder.dividerLine.setVisibility(View.INVISIBLE);
        }
        if (singleField.getName().toUpperCase().equals(REVENUE_PAID__UPPER_CASE)) {
            viewHolder.periodTextView.setVisibility(View.VISIBLE);
            viewHolder.periodTextView.setText(period);
        } else {
            viewHolder.periodTextView.setVisibility(View.INVISIBLE);
            viewHolder.periodTextView.setText("");
        }
    }





    // ------------------------------------------------------------------------

    private int getRevenueImageId(String revenue) {
        int imageId = 0;
        switch (revenue.toUpperCase()) {
            case REVENUE_BILLABLE__UPPER_CASE: {
                imageId = R.drawable.dashboard_billable;
                break;
            }

            case REVENUE_INVOICED__UPPER_CASE : {
                imageId = R.drawable.dashboard_invoiced;
                break;
            }

            case REVENUE_PAID__UPPER_CASE : {
                imageId = R.drawable.dashboard_paid;
                break;
            }

            default : {
                imageId = R.drawable.dashboard_logo;
            }
        }
        return imageId;
    };

    private void fillParent(ViewGroup parent, View view) {
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.height = parentHeight;
        view.setLayoutParams(viewParams);
    }

    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        public ImageView moneyImageView;
        public TextView moneyTypeTextView;
        public TextView moneyAmountTextView;
        public TextView periodTextView;
        public View dividerLine;

        public RevenueViewHolder(View itemView) {
            super(itemView);
            this.moneyImageView = (ImageView) itemView.findViewById(R.id.money_img);
            this.moneyTypeTextView = (TextView) itemView.findViewById(R.id.money_type);
            this.moneyAmountTextView = (TextView) itemView.findViewById(R.id.money_amount);
            this.periodTextView = (TextView) itemView.findViewById(R.id.mbo_dashboard_revenue_period_textView);
            this.dividerLine=itemView.findViewById(R.id.dividerLine);
        }
    }



    public void setFont(View...args)
    {
        TextView textView1=(TextView)args[0];
        TextView textView2=(TextView)args[1];
        TextView textView3=(TextView)args[2];
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"font/Roboto-Regular.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
    }
}

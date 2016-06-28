package com.mbopartners.mbomobile.ui.activity.choose_workorder;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.ui.R;

import java.util.List;

public class ChoseWorkOrderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MODE__LOADING = 0;
    private static final int MODE__EMPTY_LIST = 1;
    private static final int MODE__UNBILLABLE_ONLY = 2;
    private static final int MODE__GENERAL_LIST = 3;

    private static final int ITEM_VIEW_TYPE__UNKNOWN = 0;
    private static final int ITEM_VIEW_TYPE__LOADING = 1;
    private static final int ITEM_VIEW_TYPE__EMPTY_LIST = 2;
    private static final int ITEM_VIEW_TYPE__WORK_ORDER_FOR_UNBILLABLE_EXPENSE = 3;
    private static final int ITEM_VIEW_TYPE__GENERAL_ITEM = 4;


    private List<WorkOrder> workOrders;
    private OnItemClickListener mItemClickListener;

    public ChoseWorkOrderRecyclerViewAdapter(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case ITEM_VIEW_TYPE__LOADING : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                viewHolder = new BulkViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__EMPTY_LIST : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_workorders_list,parent, false);
                viewHolder = new BulkViewHolder(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__WORK_ORDER_FOR_UNBILLABLE_EXPENSE : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_unbillable_expense, parent, false);
                viewHolder = new BulkViewHolderSelectable(itemView);
                break;
            }
            case ITEM_VIEW_TYPE__GENERAL_ITEM : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_order, parent, false);
                viewHolder = new WorkOrderViewHolder(itemView);
                break;
            }
            default : {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_list, parent, false);
                viewHolder = new BulkViewHolder(itemView);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE__GENERAL_ITEM : {
                WorkOrder workOrder = workOrders.get(position);
                WorkOrderViewHolder workOrderViewHolder = (WorkOrderViewHolder) holder;
                workOrderViewHolder.companyNameTextView.setText(workOrder.getCompany().getName());
                workOrderViewHolder.workOrderNameTextView.setText(workOrder.getName());
            }
            default : {
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemCount;
        switch (figureOutMode(workOrders)) {
            case MODE__LOADING : {
                itemCount = 1; // "Loading..." Stub
             break;
            }
            case MODE__EMPTY_LIST : {
                itemCount = 1; // "No active work orders" Stub
                break;
            }
            case MODE__UNBILLABLE_ONLY : {
                itemCount = 2; // "Unbillable Expense" + "No active work orders" Stub
                break;
            }
            case MODE__GENERAL_LIST : {
                itemCount = workOrders.size();
                break;
            }
            default : {
                itemCount = 0;
            }
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = ITEM_VIEW_TYPE__UNKNOWN;
        switch (figureOutMode(workOrders)) {
            case MODE__LOADING : {
                viewType = ITEM_VIEW_TYPE__LOADING;
                break;
            }
            case MODE__EMPTY_LIST : {
                viewType = ITEM_VIEW_TYPE__EMPTY_LIST;
                break;
            }
            case MODE__UNBILLABLE_ONLY : {
                if (position == 0) {
                    viewType = ITEM_VIEW_TYPE__WORK_ORDER_FOR_UNBILLABLE_EXPENSE;
                } else {
                    viewType = ITEM_VIEW_TYPE__EMPTY_LIST;
                }
                break;
            }
            case MODE__GENERAL_LIST : {
                if (workOrders.get(position).getId() == null) {
                    viewType = ITEM_VIEW_TYPE__WORK_ORDER_FOR_UNBILLABLE_EXPENSE;
                } else {
                    viewType = ITEM_VIEW_TYPE__GENERAL_ITEM;
                }
                break;
            }
            default : {
                viewType = ITEM_VIEW_TYPE__LOADING;
            }
        }
        return viewType;
    }

    public void updateDataSource(List<WorkOrder> timePeriods) {
        this.workOrders = timePeriods;
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private int figureOutMode(List<WorkOrder> workOrders) {
        int mode;

        if (workOrders == null) {
            mode = MODE__LOADING;
        } else if (workOrders.size() == 0) {
            mode = MODE__EMPTY_LIST; // Stub
        } else if (workOrders.size() == 1 && workOrders.get(0).getId() == null) {
            mode = MODE__UNBILLABLE_ONLY; // (Unbillable + Stub)
        } else {
            mode = MODE__GENERAL_LIST;
        }
    return mode;
    }

    public interface OnItemClickListener {
        void onWorkOrderSelected(String workOrderId);
    }

    public class BulkViewHolder extends RecyclerView.ViewHolder {
        public BulkViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BulkViewHolderSelectable extends RecyclerView.ViewHolder implements View.OnClickListener {
        public BulkViewHolderSelectable(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onWorkOrderSelected(null);
            }
        }
    }

    public class WorkOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView companyNameTextView;
        public TextView workOrderNameTextView;

        public WorkOrderViewHolder(View itemView) {
            super(itemView);
            this.companyNameTextView = (TextView) itemView.findViewById(R.id.company_name_TextView);
            this.workOrderNameTextView = (TextView) itemView.findViewById(R.id.work_order_name_TextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onWorkOrderSelected(workOrders.get(getAdapterPosition()).getId());
            }
        }
    }

}

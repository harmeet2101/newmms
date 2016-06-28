package com.mbopartners.mbomobile.ui.activity.choose_expense_type;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.util.List;

public class ExpenseTypeRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseTypeRecyclerViewAdapter.ExpenseTypeViewHolder> implements FontController
{
    List<ExpenseType> expenseTypesList = null;
    private Context context;
    public ExpenseTypeRecyclerViewAdapter(List<ExpenseType> expenseTypesList,Context context) {
        this.expenseTypesList = expenseTypesList;
        this.context=context;
    }

    private OnItemClickListener mItemClickListener;
    @Override
    public ExpenseTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_choose_expense_type, parent, false);
        return new ExpenseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseTypeViewHolder holder, int position) {
        ExpenseType item = expenseTypesList.get(position);
        holder.expenseNameTextView.setText(item.getName());
        //holder.expenseImage.setImageResource(ChooseExpenseTypeActivityFragment.getExpenseImageIdOne(item.getMboId()));
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (expenseTypesList != null)  {
            itemCount =  expenseTypesList.size();
        } else {
            itemCount = 0;
        }
        return itemCount;
    }

    public void updateDataSource(List<ExpenseType> fields) {
        this.expenseTypesList = fields;
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String expenseTypeId, int position);
    }

    public class ExpenseTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View itemView;
        public ImageView expenseImage;
        public TextView expenseNameTextView;

        public ExpenseTypeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //this.expenseImage = (ImageView) itemView.findViewById(R.id.expense_ImageView);
            this.expenseNameTextView = (TextView) itemView.findViewById(R.id.expense_name_TextView);
            itemView.setOnClickListener(this);
            setFont((View) expenseNameTextView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ExpenseType item = expenseTypesList.get(position);
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(item.getMboId(), position);
            }

        }
    }

    @Override
    public void setFont(View... args) {
        for(int i=0;i<args.length;i++)
        {
            TextView textView1=(TextView)args[i];
            Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"font/Roboto-Regular.ttf");
            textView1.setTypeface(typeface1);
        }
    }
}

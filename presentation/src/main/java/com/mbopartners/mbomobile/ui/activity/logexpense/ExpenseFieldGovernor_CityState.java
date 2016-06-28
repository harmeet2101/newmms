package com.mbopartners.mbomobile.ui.activity.logexpense;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mbopartners.mbomobile.rest.model.response.ExpenseField;

public class ExpenseFieldGovernor_CityState extends BaseFieldGovernor {

	public void onBindViewHolder(RecyclerView.ViewHolder holder, ExpenseField field, String value, boolean editable) {
		super.onBindViewHolder(holder, field);
		ExpenseFieldViewHolder_CityState holder_CityState = (ExpenseFieldViewHolder_CityState) holder;
		holder_CityState.fieldValueEditText.setText(value != null ? value : "");
		if (editable) {
			holder_CityState.fieldValueEditText.setEnabled(true);
		} else {
			holder_CityState.fieldValueEditText.setEnabled(false);
		}
	}

}

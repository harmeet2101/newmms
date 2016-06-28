package com.mbopartners.mbomobile.ui.model;

import com.mbopartners.mbomobile.rest.model.response.WorkOrder;

/**
 * Created by vinove on 17/5/16.
 */
public class ExpenseSpinner {


    private WorkOrder workOrder;

    public ExpenseSpinner(){}

    public ExpenseSpinner(WorkOrder item)
    {
        workOrder=item;
    }

    public WorkOrder getItem() {
        return workOrder;
    }

    public void setItem(WorkOrder item) {
        workOrder = item;
    }
}

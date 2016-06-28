package com.mbopartners.mbomobile.ui.activity.choose_expense_type;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mbopartners.mbomobile.rest.model.Converter;
import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.ui.model.Dao;

import java.util.List;

public class ExpenseTypesAsyncLoader extends AsyncTaskLoader<List<ExpenseType>> {
    private Application application;

    public ExpenseTypesAsyncLoader(Application application) {
        super((Context) application);
        this.application = application;
    }

    @Override
    public List<ExpenseType> loadInBackground() {
        return Converter.toWeb_ExpenseType(Dao.loadExpenseTypes(this.application));
    }
}

package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.response.Expense;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class PostExpenseRequest extends AbstractRestRequest<Expense> {
    public PostExpenseRequest(String baseUrl, Expense expense) {
        super(RestApiContract.Method.createExpense, baseUrl);
        this.setRequestBody(expense);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}

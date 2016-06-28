package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.response.Expense;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class PutExpenseRequest extends AbstractRestRequest<Expense> {
    private final String mboExpenseId;

    public PutExpenseRequest(String baseUrl, String mboExpenseId, Expense expense) {
        super(RestApiContract.Method.updateExpense, baseUrl);
        this.setRequestBody(expense);
        this.mboExpenseId = mboExpenseId;
    }

    @Override
    public String getParsedUrl() {
        String parsedUrl = this.getRequestDescriptor().getRestMethod().getUrl();
        parsedUrl = parsedUrl.replace(RestApiContract.Key.EXPENSE_ID, mboExpenseId);
        return parsedUrl;
    }
}

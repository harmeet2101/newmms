package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.response.Receipt;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class DeleteExpenseReceiptRequest extends AbstractRestRequest<Receipt> {
    private final String mboExpenseId;
    private final String receiptFilename;

    public DeleteExpenseReceiptRequest(String baseUrl, String mboExpenseId, String receiptFilename) {
        super(RestApiContract.Method.deleteReceiptExpense, baseUrl);
        this.mboExpenseId = mboExpenseId;
        this.receiptFilename = receiptFilename;
    }

    @Override
    public String getParsedUrl() {
        String parsedUrl = this.getRequestDescriptor().getRestMethod().getUrl();
        parsedUrl = parsedUrl.replace(RestApiContract.Key.EXPENSE_ID, mboExpenseId);
        parsedUrl = parsedUrl.replace(RestApiContract.Key.EXPENSE_FILENAME, receiptFilename);
        return parsedUrl;
    }

    public String getMboExpenseId() {
        return mboExpenseId;
    }

    public String getReceiptFilename() {
        return receiptFilename;
    }
}

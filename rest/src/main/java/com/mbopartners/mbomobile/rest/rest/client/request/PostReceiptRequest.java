package com.mbopartners.mbomobile.rest.rest.client.request;

import android.net.Uri;
import android.util.Pair;

import java.io.File;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class PostReceiptRequest extends AbstractRestRequest<File> {
    private final String mboExpenseId;

    public PostReceiptRequest(String baseUrl, String mboExpenseId, File receiptImageFile) {
        super(RestApiContract.Method.createReceipt, baseUrl);
        this.mboExpenseId = mboExpenseId;
        this.setRequestBody(receiptImageFile);
    }

    @Override
    public String getParsedUrl() {
        String parsedUrl = this.getRequestDescriptor().getRestMethod().getUrl();
        parsedUrl = parsedUrl.replace(RestApiContract.Key.EXPENSE_ID, mboExpenseId);
        return parsedUrl;
    }
}

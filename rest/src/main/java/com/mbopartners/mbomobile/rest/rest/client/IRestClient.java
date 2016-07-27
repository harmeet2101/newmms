package com.mbopartners.mbomobile.rest.rest.client;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;

import java.io.File;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.RequestDescriptor;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

/**
 * All implementations of this interface have to :
 *   - work asynchronously
 *   - return empty(!) object or void
 *
 */
public interface IRestClient {

    interface Callback {
        void onComplete(UniversalRestResponse response);
    }

    //--------------------------------------------------------------------------------
    //
    // Common methods
    //
    //--------------------------------------------------------------------------------
    void registerCallback(RestMethod restMethod, Callback callback);
    void unRegisterCallback(RestMethod restMethod);
    void clearCallbacks();
    void onReceiveResponse(UniversalRestResponse response);

    //--------------------------------------------------------------------------------
    //
    // REST API invocation methods
    //
    //--------------------------------------------------------------------------------

    RequestDescriptor authenticateOauth(Context context, String username, String password, String grantType);
    RequestDescriptor getUserProfile(Context context);
    RequestDescriptor getDashboardsList(Context context);
    RequestDescriptor getWorkOrdersList(Context context);
    RequestDescriptor submitTimePeriod(Context context, String workOrderId, String periodId);
    RequestDescriptor saveTimeEntries(Context context, String workOrderId, String periodId, List<TimeEntry> timeEntries);

    RequestDescriptor getExpensesList(Context context);
    RequestDescriptor getExpenseTypesList(Context context);
    RequestDescriptor createExpense(Context context, Expense expense);
    RequestDescriptor updateExpense(Context context, String mboExpenseId, Expense expense);

    RequestDescriptor postExpenseReceipt(Context context, String mboExpenseId, File receiptImageFile);
    RequestDescriptor deleteExpenseReceipt(Context context, String mboExpenseId, String receiptFilename);
    RequestDescriptor getBusinessCenterList(Context context);
    RequestDescriptor getPayrollSummaryList(Context context);
    RequestDescriptor getPayrollTransactionsList(Context context);

}

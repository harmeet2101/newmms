package com.mbopartners.mbomobile.rest.rest.client;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.configuration.NetworkingConstants;
import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.request.DashboardsRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.DeleteExpenseReceiptRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.GetExpenseTypesRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.GetExpensesRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.GetUserProfileRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.GetWorkOrdersRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.OAuthAuthenticateRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.PostExpenseRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.PostReceiptRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.PutExpenseRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.SaveTimeEntriesRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.SubmitTimePeriodRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.BusinessCenterRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PayrollSummaryRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PayrollTransactionRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PreviewsRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.WebRestService;
import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpParams;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.request.RequestDescriptor;
import ua.com.mobidev.android.mdrest.web.rest.request.RequestId;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class RestServiceHelper extends AbstractApplicationController implements IRestClient {
    private static final String TAG = RestServiceHelper.class.getSimpleName();

    private Map<RestMethod, Callback> callbacks = new HashMap<>();
    private Map<RequestId, AbstractRestRequest> pendingRequests = new HashMap<>();

    private ConfigurationController configurationController;
    private SharedPreferencesController sharedPreferencesController;

    public RestServiceHelper(ConfigurationController configurationController, SharedPreferencesController sharedPreferencesController) {
        super(Controllers.CONTROLLER__REST_SERVICE_HELPER);
        this.configurationController = configurationController;
        this.sharedPreferencesController = sharedPreferencesController;
    }

    //--------------------------------------------------------------------------------
    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    //--------------------------------------------------------------------------------

    @Override
    public RequestDescriptor authenticateOauth(Context context, String username, String password, String grantType) {
        OAuthBodyEntity params = new OAuthBodyEntity();
        params.username = username;
        params.password = password;
        params.grantType = grantType;

        ConfigurationController.EnvironmentVariables environmentVariables = configurationController.getCurrentEnvironmentVariables();

        String oAuthServerUrl = environmentVariables.oAuthHostname;
        OAuthAuthenticateRequest request = new OAuthAuthenticateRequest(params, oAuthServerUrl);

        String clientId = environmentVariables.oAuth_ClientId;
        String clientSecret = environmentVariables.oAuth_ClientSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.putHeader("Content-Type", "application/x-www-form-urlencoded");
        String auth = "Basic" + " "
                + Base64.encodeToString((clientId
                + ":"
                + clientSecret).getBytes(), Base64.NO_WRAP);
        headers.putHeader("Authorization", auth);
        request.setHttpHeaders(headers);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getPayrollPreviewsList(Context context, String payrollDate, String method, String amount) {

        /*PayrollPreviewsEntity params=new PayrollPreviewsEntity();
        params.payrollDate=payrollDate;
        params.method=method;
        params.amount=amount;*/

        PreviewsRequest request=new PreviewsRequest(NetworkingConstants.PAYROLL_DEV_HOSTNAME);
        HttpParams httpParams=new HttpParams();
        httpParams.putParam("payrollDate",payrollDate);
        httpParams.putParam("method",method);
        httpParams.putParam("amount",amount);
        request.setHttpParams(httpParams);

        addStandardRequesrHeaders(request);
        sendRequest(request,context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getUserProfile(Context context) {
        GetUserProfileRequest request = new GetUserProfileRequest(configurationController.getCurrentEnvironmentVariables().hostname);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getDashboardsList(Context context) {
        DashboardsRequest request = new DashboardsRequest(configurationController.getCurrentEnvironmentVariables().hostname);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getBusinessCenterList(Context context) {
        BusinessCenterRequest request = new BusinessCenterRequest(NetworkingConstants.PAYROLL_DEV_HOSTNAME);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }
    @Override
    public RequestDescriptor getPayrollSummaryList(Context context) {
        PayrollSummaryRequest request = new PayrollSummaryRequest(NetworkingConstants.PAYROLL_DEV_HOSTNAME);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getPayrollTransactionsList(Context context) {

        PayrollTransactionRequest request = new PayrollTransactionRequest(NetworkingConstants.PAYROLL_DEV_HOSTNAME);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getWorkOrdersList(Context context) {
        GetWorkOrdersRequest request = new GetWorkOrdersRequest(configurationController.getCurrentEnvironmentVariables().hostname);
        addStandardRequesrHeaders(request);
        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor submitTimePeriod(Context context, String workOrderId, String periodId) {
        SubmitTimePeriodRequest request = new SubmitTimePeriodRequest(configurationController.getCurrentEnvironmentVariables().hostname, workOrderId, periodId);
        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor saveTimeEntries(Context context, String workOrderId, String periodId, List<TimeEntry> timeEntries) {
        SaveTimeEntriesRequest request = new SaveTimeEntriesRequest(configurationController.getCurrentEnvironmentVariables().hostname, workOrderId, periodId, timeEntries);
        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getExpensesList(Context context) {
        GetExpensesRequest request = new GetExpensesRequest(configurationController.getCurrentEnvironmentVariables().hostname);

        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor getExpenseTypesList(Context context) {
        GetExpenseTypesRequest request = new GetExpenseTypesRequest(configurationController.getCurrentEnvironmentVariables().hostname);

        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor createExpense(Context context, Expense expense) {
        PostExpenseRequest request = new PostExpenseRequest(configurationController.getCurrentEnvironmentVariables().hostname, expense);

        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor updateExpense(Context context, String mboExpenseId, Expense expense) {
        PutExpenseRequest request = new PutExpenseRequest(configurationController.getCurrentEnvironmentVariables().hostname, mboExpenseId, expense);

        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor postExpenseReceipt(Context context, String mboExpenseId, File receiptImageFile) {
        PostReceiptRequest request = new PostReceiptRequest(configurationController.getCurrentEnvironmentVariables().hostname, mboExpenseId, receiptImageFile);

        addStandardRequesrHeaders(request);
        request.getHttpHeaders().putHeader("Content-Type", "multipart/form-data");

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    @Override
    public RequestDescriptor deleteExpenseReceipt(Context context, String mboExpenseId, String receiptFilename) {
        DeleteExpenseReceiptRequest request = new DeleteExpenseReceiptRequest(configurationController.getCurrentEnvironmentVariables().hostname, mboExpenseId, receiptFilename);

        addStandardRequesrHeaders(request);

        sendRequest(request, context);
        return request.getRequestDescriptor();
    }

    //--------------------------------------------------------------------------------

    @Override
    public void registerCallback(RestMethod restMethod, Callback callback) {
        callbacks.put(restMethod, callback);
        Log.d(TAG, "Registered callbacks : " + callbacks.size());
    }

    @Override
    public void unRegisterCallback(RestMethod restMethod) {
        callbacks.remove(restMethod);
    }

    @Override
    public void clearCallbacks() {
        callbacks.clear();
    }

    @Override
    public void onReceiveResponse(UniversalRestResponse response) {
        removePendingRequest(response);
        callCallBack(response);
    }

    //--------------------------------------------------------------------------------

    private void sendRequest(AbstractRestRequest request, Context context) {

        //TODO register request and it's callback
        RequestId requestId = request.getRequestDescriptor().getRequestId();
        pendingRequests.put(requestId, request);

        Intent intent = WebRestService.buildRequestIntent(context, request);
        context.startService(intent);
    }

    public void loadPendingRequests() {

    }

    public void savePendingRequests() {

    }

    //================================================================================

    private void removePendingRequest(UniversalRestResponse response) {
        RequestId requestId = response.getRequest().getRequestDescriptor().getRequestId();
        pendingRequests.remove(requestId);
    }

    private void callCallBack(UniversalRestResponse response) {
        RestMethod method = response.getRequest().getRequestDescriptor().getRestMethod();
        Callback callback = callbacks.get(method);
        if (callback != null) {
            callback.onComplete(response);
        }
    }

    private void addStandardRequesrHeaders(AbstractRestRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putHeader("Content-Type", "application/json");
        httpHeaders.putHeader("Cache-Control", "no-cache");
        request.setHttpHeaders(httpHeaders);
    }

    public static void updateJwtAuthenticationCredentials(AbstractRestRequest request, String accessToken) {
        HttpHeaders httpHeaders = request.getHttpHeaders();
        String auth = "Bearer" + " " + accessToken;
        httpHeaders.putHeader("Authorization", auth);
    }
}

package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;
import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;
import com.mbopartners.mbomobile.rest.model.response.Dashboard;
import com.mbopartners.mbomobile.rest.model.response.EmptyResponseEntity;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.model.response.UserProfile;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthApiException;
import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthToken;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.BusinessCenterRequest;
import com.mbopartners.mbomobile.rest.rest.storage.handler.DashboardsDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.DeleteExpenseReceiptDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.GetExpenseTypesDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.GetExpensesDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.GetUserProfileDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.GetWorkOrdersDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.OAuthAuthenticateDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.PostExpenseDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.PostReceiptDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.PutExpenseDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.SaveTimeEntriesDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.SubmitTimePeriodDbHandler;
import com.mbopartners.mbomobile.rest.rest.storage.handler.payroll.BusinessCenterDbHandler;

import java.io.File;

import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpMethod;

public class RestApiContract {

    public static final String CHARSET_NAME = "UTF-8";

    private String mboServerBaseUrl;

    public RestApiContract(String mboServerBaseUrl) {
        this.mboServerBaseUrl = mboServerBaseUrl;
    }

    private static class Resource {
        private static final String OAUTH = "";
        private static final String USER_PROFILE = "users/me/";
        private static final String FORGOT_PASSWORD = "users/forgotPassword";
        private static final String DASHBOARDS = "dashboards/timeAndExpenses";
        private static final String WORK_ORDERS_LIST = "workOrders";
        private static final String SUBMIT_TIME_PERIOD = "workOrders/" + Key.WORK_ORDER_ID + "/timePeriods/" + Key.TIME_PERIOD_ID + "/submit/";
        private static final String SAVE_TIME_ENTRIES = "workOrders/" + Key.WORK_ORDER_ID + "/timePeriods/" + Key.TIME_PERIOD_ID + "/timeEntries/";
        private static final String EXPENSES_LIST = "expenses/";

        private static final String EXPENSE = "expenses/" + Key.EXPENSE_ID + "/";
        private static final String EXPENSE_RECEIPTS_LIST = "expenses/" + Key.EXPENSE_ID + "/receipts/";
        private static final String EXPENSE_RECEIPT = "expenses/" + Key.EXPENSE_ID + "/receipts/" + Key.EXPENSE_FILENAME + "/";
        private static final String EXPENSE_TYPES_LIST = "expenseTypes/ ";
        private static final String BUSINESS_CENTER_LIST="business-centers/v1/business-centers/";
    }

    public static class Key {
        public static final String WORK_ORDER_ID = "{WorkOrderId}";
        public static final String TIME_PERIOD_ID = "{TimePeriodId}";
        public static final String EXPENSE_ID = "{ExpenseId}";
        public static final String EXPENSE_FILENAME = "{ExpenseFileName}";
    }

    public static class Name {
        public static final String oAuth = "OAuth_2.0";
        public static final String getUserProfile = "Get_User_Profile";
        public static final String forgotPassword = "Forgot_password";
        public static final String dashboards = "Get_Dashboards_list";
        public static final String getWorkOrdersList = "Get_WorkOrders_list";
        public static final String submitTimePeriod = "Submit_Time_Period";
        public static final String saveTimeEntries = "Save_TimeEntries_Of_TimePeriod";
        public static final String getExpensesList = "Get_Expenses_list";
        public static final String getExpenseTypesList = "Get_ExpenseTypes_list";
        public static final String createExpense = "Post_Expense";
        public static final String updateExpense = "Put_Expense";
        public static final String createReceipt = "Post_Receipt";
        public static final String deleteReceipt = "Delete_Receipt";
        public static final String getReceiptsList = "Get_Receipts_List";
        public static final String getBusinessCenterList="Get_Business_Center_list";
    }

    public static class Method {
        public static final RestMethod<OAuthAuthenticateRequest, OAuthBodyEntity, OAuthToken, OAuthApiException, OAuthAuthenticateDbHandler> oAuth =
                new RestMethod<>(Name.oAuth, HttpMethod.POST, Resource.OAUTH, OAuthBodyEntity.class, OAuthToken.class, OAuthApiException.class, OAuthAuthenticateDbHandler.class);

        public static final RestMethod< GetUserProfileRequest, EmptyRequestEntity, UserProfile, RestServerApiException, GetUserProfileDbHandler> getUserProfile =
                new RestMethod<>(Name.getUserProfile, HttpMethod.GET, Resource.USER_PROFILE, EmptyRequestEntity.class, UserProfile.class, RestServerApiException.class, GetUserProfileDbHandler.class);

        public static final RestMethod<DashboardsRequest, EmptyRequestEntity, Dashboard, RestServerApiException, DashboardsDbHandler> getDashboards =
                new RestMethod<>(Name.dashboards, HttpMethod.GET, Resource.DASHBOARDS, EmptyRequestEntity.class, Dashboard.class, RestServerApiException.class, DashboardsDbHandler.class);

        public static final RestMethod<GetWorkOrdersRequest, EmptyRequestEntity, WorkOrder[], RestServerApiException, GetWorkOrdersDbHandler> getWorkOrdersList =
                new RestMethod<>(Name.getWorkOrdersList, HttpMethod.GET, Resource.WORK_ORDERS_LIST, EmptyRequestEntity.class, WorkOrder[].class, RestServerApiException.class, GetWorkOrdersDbHandler.class);

        public static final RestMethod<SubmitTimePeriodRequest, EmptyRequestEntity, EmptyResponseEntity, RestServerApiException, SubmitTimePeriodDbHandler> submitTimePeriod =
                new RestMethod<>(Name.submitTimePeriod, HttpMethod.POST, Resource.SUBMIT_TIME_PERIOD, EmptyRequestEntity.class, EmptyResponseEntity.class, RestServerApiException.class, SubmitTimePeriodDbHandler.class);

        public static final RestMethod<SaveTimeEntriesRequest, TimeEntry[], TimeEntry[],  RestServerApiException, SaveTimeEntriesDbHandler> saveTimeEntries =
                new RestMethod<>(Name.saveTimeEntries, HttpMethod.PUT, Resource.SAVE_TIME_ENTRIES, TimeEntry[].class, TimeEntry[].class, RestServerApiException.class, SaveTimeEntriesDbHandler.class);

        public static final RestMethod<GetExpensesRequest, EmptyRequestEntity, Expense[], RestServerApiException, GetExpensesDbHandler> getExpensesList =
                new RestMethod<>(Name.getExpensesList, HttpMethod.GET, Resource.EXPENSES_LIST, EmptyRequestEntity.class, Expense[].class, RestServerApiException.class, GetExpensesDbHandler.class);

        public static final RestMethod<GetExpenseTypesRequest, EmptyRequestEntity, ExpenseType[], RestServerApiException, GetExpenseTypesDbHandler> getExpenseTypesList =
                new RestMethod<>(Name.getExpenseTypesList, HttpMethod.GET, Resource.EXPENSE_TYPES_LIST, EmptyRequestEntity.class, ExpenseType[].class, RestServerApiException.class, GetExpenseTypesDbHandler.class);

        public static final RestMethod<PostExpenseRequest, Expense, Expense, RestServerApiException, PostExpenseDbHandler> createExpense =
                new RestMethod<>(Name.createExpense, HttpMethod.POST, Resource.EXPENSES_LIST, Expense.class, Expense.class, RestServerApiException.class, PostExpenseDbHandler.class);

        public static final RestMethod<PutExpenseRequest, Expense, Expense, RestServerApiException, PutExpenseDbHandler> updateExpense =
                new RestMethod<>(Name.updateExpense, HttpMethod.PUT, Resource.EXPENSE, Expense.class, Expense.class, RestServerApiException.class, PutExpenseDbHandler.class);

        public static final RestMethod<DeleteExpenseReceiptRequest, Receipt, EmptyResponseEntity, RestServerApiException, DeleteExpenseReceiptDbHandler> deleteReceiptExpense =
                new RestMethod<>(Name.deleteReceipt, HttpMethod.DELETE, Resource.EXPENSE_RECEIPT, Receipt.class, EmptyResponseEntity.class, RestServerApiException.class, DeleteExpenseReceiptDbHandler.class);

        public static final RestMethod<PostReceiptRequest, File, Receipt, RestServerApiException, PostReceiptDbHandler> createReceipt =
                new RestMethod<>(Name.createReceipt, HttpMethod.POST, Resource.EXPENSE_RECEIPTS_LIST, File.class, Receipt.class, RestServerApiException.class, PostReceiptDbHandler.class);

        // TODO getReceipts()

        public static final RestMethod<BusinessCenterRequest, EmptyRequestEntity, BusinessCenter[], RestServerApiException, BusinessCenterDbHandler> getBusinessCenterList =
                new RestMethod<>(Name.getBusinessCenterList, HttpMethod.GET, Resource.BUSINESS_CENTER_LIST, EmptyRequestEntity.class, BusinessCenter[].class, RestServerApiException.class, BusinessCenterDbHandler.class);

          }
}



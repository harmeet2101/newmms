package com.mbopartners.mbomobile.ui.util;

import android.content.Context;

import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.ui.dialog.ApiErrorDialog;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;
import com.mbopartners.mbomobile.ui.toast.ErrorToaster;

import java.net.HttpURLConnection;

import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class DefaultRestClientResponseHandler implements IRestClient.Callback {
    private static final String TAG = DefaultRestClientResponseHandler.class.getSimpleName();

    private Context context;

    public DefaultRestClientResponseHandler(Context context) {
        this.context = context;
    }

    @Override
    public synchronized void onComplete(UniversalRestResponse response) {
        String message = "Response received : " + response.getRequest().getRequestDescriptor().getRestMethod().getName();
        switch (response.getClientResult()) {
            case Ok : {
                break;
            }

            case EntityValidationError : {
                ApiErrorDialog.showApi_DataValidationNotPassedError(context);
                break;
            }

            case HttpError : {
                int httpErrorCode = response.getClientResult().getStatusCode();
                switch (httpErrorCode) {
                    case HttpURLConnection.HTTP_UNAUTHORIZED : {
                        ApiErrorDialog.showApi_ExpiredCredentialsError(context);
                        break;
                    }
                    default : {
                        ApiErrorDialog.showApi_GeneralServerError(context);
                    }
                }
                break;
            }

            case NoConnectionError : {
                ApiErrorDialog.showApi_NoInternetConnectionError(context);
                break;
            }
            case NetworkError :
            case TimeoutError :
            case ParseError :
            case Unknown : {
                ApiErrorDialog.showApi_GeneralServerError(context);
                break;
            }

            default: {
                ApiErrorDialog.showApi_GeneralServerError(context);
            }
        }
    }
}

package ua.com.mobidev.android.mdrest.web.rest.processor;

import ua.com.mobidev.android.mdrest.web.rest.method.IRestHttpClient;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.storage.IRestStore;

/**
 *
 */
public interface IHttpRequestProcessor {

    void cancelAllRequests();
    void process(AbstractRestRequest universalRestRequest);

    void setRestHttpClient(IRestHttpClient restHttpClient);
    void setCallbackListener(Callback callbackListener);

    interface Callback {
        void onComplete(UniversalRestResponse restResponse);
        void onError(UniversalRestResponse restResponse);
    }
}

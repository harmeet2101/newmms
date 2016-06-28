package ua.com.mobidev.android.mdrest.web.rest.method;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

/**
 *
 */
public interface IRestHttpClient {

    void setHttpClientResponseProcessor(Callback processor);

    <Type_ParamEntity, Type_Response extends UniversalRestResponse, Type_DbHandler> void start(AbstractRestRequest<Type_ParamEntity> restRequest);

    void shutDown();

    void cancelAllRequests();

    interface Callback {
        void processSuccessResponse(UniversalRestResponse universalRestResponse);
        void processErrorResponse(UniversalRestResponse universalRestResponse);
    }
}

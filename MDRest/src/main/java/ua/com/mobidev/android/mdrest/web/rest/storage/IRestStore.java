package ua.com.mobidev.android.mdrest.web.rest.storage;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

/**
 *
 */
public interface IRestStore {

    OperationResult registerRequest(AbstractRestRequest universalRestRequest);
    OperationResult notifySuccess(UniversalRestResponse universalRestResponse);
    OperationResult notifyError(UniversalRestResponse universalRestResponse);

    class OperationResult {
        
    }


}

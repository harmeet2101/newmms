package com.mbopartners.mbomobile.app.application.controller.prestore;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public interface PreStoreListener {
    void onMethodBefore(AbstractRestRequest request);
    void onMethodOk(UniversalRestResponse response);
    void onMethodError(UniversalRestResponse response);


}

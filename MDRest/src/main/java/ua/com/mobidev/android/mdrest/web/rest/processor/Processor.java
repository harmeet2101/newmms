package ua.com.mobidev.android.mdrest.web.rest.processor;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.method.IRestHttpClient;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.storage.IRestStore;

public class Processor extends AbstractApplicationController
        implements IHttpRequestProcessor, IRestHttpClient.Callback {

    private static final String TAG = Processor.class.getSimpleName();

    private static final boolean STRICT_MODE = true;
    private static final int NUMBER_OF_THREADS = 1;


    IRestHttpClient restHttpClient;
    IRestStore restStore;
    Callback serviceCallbackListener;
    ExecutorService executorService;

    public Processor() {
        super(Controllers.CONTROLLER__REST_PROCESSOR);
        this.executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    //
    // AbstractApplicationController Implementation
    //--------------------------------------------------------------------------------

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    //
    // IHttpRequestProcessor Implementation
    //--------------------------------------------------------------------------------
    @Override
    public void setRestHttpClient(IRestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    @Override
    public void setCallbackListener(Callback callbackListener) {
        this.serviceCallbackListener = callbackListener;
    }

    @Override
    public void cancelAllRequests() {
        this.restHttpClient.cancelAllRequests();
    }

    @Override
    public void process(AbstractRestRequest universalRestRequest) {
        executorService.submit(new RegisterRequestRunner(universalRestRequest));
    }

    //================================================================================
    //================================================================================

    //
    // IHttpClintResponseProcessor Implementation
    //--------------------------------------------------------------------------------
    @Override
    public void processSuccessResponse(UniversalRestResponse universalRestResponse) {
        executorService.submit(new SuccessResponseRunner(universalRestResponse));
    }

    @Override
    public void processErrorResponse(UniversalRestResponse universalRestResponse) {
        executorService.submit(new ErrorResponseRunner(universalRestResponse));
    }

    //--------------------------------------------------------------------------------

    public void setRestStore(IRestStore restStore) {
        this.restStore = restStore;
    }

    //--------------------------------------------------------------------------------

    private void validateProcessorConfiguration() {
        if (restHttpClient == null) {
            throw new IllegalStateException("Internal variable not set!");
        }

        if (restStore == null) {
            throw new IllegalStateException("Internal variable not set!");
        }

        if (serviceCallbackListener == null) {
            throw new IllegalStateException("Internal variable not set!");
        }
    }

    private class RegisterRequestRunner implements Runnable {
        private AbstractRestRequest universalRestRequest;

        public RegisterRequestRunner(AbstractRestRequest universalRestRequest) {
            this.universalRestRequest = universalRestRequest;
        }

        @Override
        public void run() {
            validateProcessorConfiguration();

            try {
                restStore.registerRequest(this.universalRestRequest);
                restHttpClient.start(this.universalRestRequest);
            } catch (Exception e) {
                Log.e(TAG, "Error caught during register Request", e);
                if (STRICT_MODE) {
                    throw e;
                }
            } catch (Throwable th) {
                Log.e(TAG, "ERROR caught during register Request", th);
            }
        }
    }

    private class SuccessResponseRunner implements Runnable {
        private UniversalRestResponse universalRestResponse;

        public SuccessResponseRunner(UniversalRestResponse universalRestResponse) {
            this.universalRestResponse = universalRestResponse;
        }

        @Override
        public void run() {
            validateProcessorConfiguration();

            try {
                restStore.notifySuccess(this.universalRestResponse);
                serviceCallbackListener.onComplete(this.universalRestResponse);
            } catch (Exception e) {
                Log.e(TAG, "Error caught during handling success Response", e);
                if (STRICT_MODE) {
                    throw e;
                }
            } catch (Throwable th) {
                Log.e(TAG, "ERROR caught during handling success Response", th);
            }
        }
    }

    private class ErrorResponseRunner implements Runnable {
        private UniversalRestResponse universalRestResponse;

        public ErrorResponseRunner(UniversalRestResponse universalRestResponse) {
            this.universalRestResponse = universalRestResponse;
        }

        @Override
        public void run() {
            validateProcessorConfiguration();

            try {
                restStore.notifyError(universalRestResponse);
                serviceCallbackListener.onError(universalRestResponse);
            } catch (Exception e) {
                Log.e(TAG, "Error caught during handling error Response", e);
                if (STRICT_MODE) {
                    throw e;
                }
            } catch (Throwable th) {
                Log.e(TAG, "ERROR caught during handling error Response", th);
            }
        }
    }
}

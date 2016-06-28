package ua.com.mobidev.android.mdrest.web.rest.method;

/**
 *
 */
public abstract class AbstractHttpRestMethod implements IRestHttpClient {
    protected Callback responseProcessor;

    @Override
    public void setHttpClientResponseProcessor(Callback processor) {
        if (processor != null) {

        } else {
            throw new IllegalArgumentException("Need not null value.");
        }
        this.responseProcessor = processor;
    }

    @Override
    public void shutDown() {
        this.responseProcessor = null;
    }
}

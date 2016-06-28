package ua.com.mobidev.android.mdrest.web.rest.client;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.rest.http.HttpMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

public class RestMethod <
            Type_Request extends AbstractRestRequest,
            Type_ParamEntity,
            Type_ResponseEntity,
            Type_ErrorEntity,
            Type_DbHandler extends AbstractDbHandler<Type_Request, Type_ResponseEntity, Type_ErrorEntity>
        >
        implements Serializable {

    /** Human readable method name */
    public final String name;

    protected HttpMethod httpMethod;

    /** Non parsed URL tale. Without base server URL !!! */
    protected String url;
    protected final Class<Type_ParamEntity> paramEntityClass;
    protected final Class<Type_ResponseEntity> responseEntityClass;
    protected final Class<Type_ErrorEntity> errorEntityClass;
    protected final Class<Type_DbHandler> dbHandlerClass;


    public RestMethod(
            String name,
            HttpMethod httpMethod,
            String url,
            Class<Type_ParamEntity> paramEntityClass,
            Class<Type_ResponseEntity> responseEntityClass,
            Class<Type_ErrorEntity> errorEntityClass,
            Class<Type_DbHandler> dbHandlerClass) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.url = url;
        this.paramEntityClass = paramEntityClass;
        this.responseEntityClass = responseEntityClass;
        this.errorEntityClass = errorEntityClass;
        this.dbHandlerClass = dbHandlerClass;
    }

    public String getName() {
        return name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public Class<Type_ParamEntity> getParamEntityClass() {
        return this.paramEntityClass;
    }

    public Class<Type_ResponseEntity> getResponseEntityClass() {
        return responseEntityClass;
    }

    public Class<Type_ErrorEntity> getErrorEntityClass() {
        return errorEntityClass;
    }

    public Type_DbHandler getDbHandler() {
        try {
            return this.dbHandlerClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Need default constructor");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Need default constructor");
        }
    }

//================================================================================

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestMethod<?, ?, ?, ?, ?> that = (RestMethod<?, ?, ?, ?, ?>) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (httpMethod != that.httpMethod) return false;
        return !(url != null ? !url.equals(that.url) : that.url != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (httpMethod != null ? httpMethod.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

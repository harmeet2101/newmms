//package com.mbopartners.mbomobile.rest.method.request;
//
//public abstract class AbstractRestRequest<Type_RequestEntity, Type_Response extends UniversalRestResponse> implements Serializable {
//    private final RequestDescriptor requestDescriptor;
//    private HttpHeaders httpHeaders;
//    private Type_RequestEntity requestBody;
//
//    public AbstractRestRequest(RestMethod restMethod) {
//        this.requestDescriptor = new RequestDescriptor(RequestId.build(), restMethod);
//        this.httpHeaders = new HttpHeaders(Collections.<String, String>emptyMap());
//        this.requestBody = null;
//    }
//
//    public abstract String getUrl();
//
//    public abstract Type_DbHandler getDbHandler();
//
//    public void setHttpHeaders(HttpHeaders httpHeaders) {
//        this.httpHeaders = httpHeaders;
//    }
//
//    public void setRequestBody(Type_RequestEntity requestBody) {
//        this.requestBody = requestBody;
//    }
//
//    public RequestDescriptor getRequestDescriptor() {
//        return requestDescriptor;
//    }
//
//    public HttpHeaders getHttpHeaders() {
//        return httpHeaders;
//    }
//
//    public Type_RequestEntity getRequestBody() {
//        return requestBody;
//    }
//}

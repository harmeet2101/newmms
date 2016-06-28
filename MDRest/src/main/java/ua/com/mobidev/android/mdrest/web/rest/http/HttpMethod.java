package ua.com.mobidev.android.mdrest.web.rest.http;

public enum HttpMethod {

    GET    ("GET"),
    POST   ("POST"),
    PUT    ("PUT"),
    DELETE ("DELETE");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    private String getName() { return name; }

}
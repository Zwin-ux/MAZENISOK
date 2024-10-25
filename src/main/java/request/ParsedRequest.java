package request;

import java.util.Map;

public class ParsedRequest {
    private final String method;
    private final String path;
    private final Map<String, String> queryParams;
    private final String body;
    private final String fullRequest;

    public ParsedRequest(String method, String path, Map<String, String> queryParams, String body, String rawRequest) {
        this.method = method;
        this.path = path;
        this.queryParams = queryParams;
        this.body = body;
        this.fullRequest = rawRequest;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryParam(String key) {
        return queryParams.get(key);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    public String getFullRequest() {
        return fullRequest;
    }

    @Override
    public String toString() {
        return "ParsedRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParams=" + queryParams +
                ", body='" + body + '\'' +
                ", fullRequest='" + fullRequest + '\'' +
                '}';
    }
}

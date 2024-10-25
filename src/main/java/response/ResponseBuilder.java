package response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private String status = "200 OK";
    private String version = "HTTP/1.1";
    private final Map<String, String> headers = new HashMap<>();
    private String body = "";

    public ResponseBuilder setStatus(int statusCode) {
        switch (statusCode) {
            case 200:
                this.status = "200 OK";
                break;
            case 201:
                this.status = "201 Created";
                break;
            case 400:
                this.status = "400 Bad Request";
                break;
            case 404:
                this.status = "404 Not Found";
                break;
            default:
                this.status = statusCode + " Unknown";
        }
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ResponseBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public CustomHttpResponse build() {
        return new CustomHttpResponse(headers, status, version, body);
    }

    @Override
    public String toString() {
        return "ResponseBuilder{" +
                "status=" + status +
                ", body='" + body + '\'' +
                '}';
    }

    public ResponseBuilder setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
}

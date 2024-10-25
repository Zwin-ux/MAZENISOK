package response;

import java.util.Map;
import java.util.stream.Collectors;

public class CustomHttpResponse {
    public final Map<String, String> headers;
    public final String status;
    public final String version;
    public final String body;

    public CustomHttpResponse(Map<String, String> headers, String status, String version,
                              String body) {
        this.headers = headers;
        this.status = status;
        this.version = version;
        this.body = body;
    }

    @Override
    public String toString() {
        String headerString = headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
        return version + " " + status + "\n" + headerString + "\n\n" + body;
    }
}

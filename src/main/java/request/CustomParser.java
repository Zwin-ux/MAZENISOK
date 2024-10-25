package request;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class CustomParser {

    // extract java useable values from a raw http request string
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
    public static ParsedRequest parse(String request) {
        String[] lines = request.split("\n");
        String[] firstLine = lines[0].split(" ");

        String method = firstLine[0];
        String fullPath = firstLine[1];

        String path;
        Map<String, String> queryParams = new HashMap<>();
        String body = null;

        if (fullPath.contains("?")) {
            String[] pathParts = fullPath.split("\\?", 2);
            path = pathParts[0];
            String[] params = pathParts[1].split("&");
            for (String param : params) {
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        } else {
            path = fullPath;
        }

        // Extract body if present
        int emptyLineIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().isEmpty()) {
                emptyLineIndex = i;
                break;
            }
        }
        if (emptyLineIndex != -1 && emptyLineIndex < lines.length - 1) {
            body = String.join("\n", Arrays.copyOfRange(lines, emptyLineIndex + 1, lines.length));
        }

        return new ParsedRequest(method, path, queryParams, body, request);
    }
}

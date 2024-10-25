package response;

public class RestApiAppResponse<T> {
    public final boolean success;
    public final String message;
    public final T data;

    public RestApiAppResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}

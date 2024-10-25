package handler;

import request.ParsedRequest;
import response.ResponseBuilder;

public class NotFoundHandler implements BaseHandler {
    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        return new ResponseBuilder()
            .setStatus(404)
            .setBody("404 Not Found");
    }
}

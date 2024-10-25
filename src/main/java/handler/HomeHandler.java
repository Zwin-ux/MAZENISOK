package handler;

import request.ParsedRequest;
import response.ResponseBuilder;

public class HomeHandler implements BaseHandler {
    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        return new ResponseBuilder()
            .setStatus(200)
            .setBody("Welcome to the API home page");
    }
}

package handler;

import com.google.gson.Gson;
import dao.UserDao;
import dto.UserDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.Optional;

public class GetUserHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        String userId = request.getQueryParam("userId");
        if (userId == null || userId.isEmpty()) {
            return new ResponseBuilder()
                .setStatus(400)
                .setBody(gson.toJson(new RestApiAppResponse<>(false, "User ID is required", null)));
        }

        Optional<UserDto> userOptional = UserDao.getInstance().get(userId);
        if (!userOptional.isPresent()) {
            return new ResponseBuilder()
                .setStatus(404)
                .setBody(gson.toJson(new RestApiAppResponse<>(false, "User not found", null)));
        }

        UserDto user = userOptional.get();
        return new ResponseBuilder()
            .setStatus(200)
            .setBody(gson.toJson(new RestApiAppResponse<>(true, "User retrieved successfully", user)));
    }
}

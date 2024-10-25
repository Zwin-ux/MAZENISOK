package handler;

import com.google.gson.Gson;
import dao.UserDao;
import dto.UserDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateUserHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        UserDto userDto;
        try {
            userDto = gson.fromJson(request.getBody(), UserDto.class);
        } catch (Exception e) {
            return new ResponseBuilder().setStatus(400).setBody("Invalid user data");
        }

        if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            return new ResponseBuilder().setStatus(400).setBody("Missing username");
        }

        String uniqueId = UUID.randomUUID().toString();
        userDto.setUniqueId(uniqueId);
        userDto.setBalance(BigDecimal.ZERO);

        UserDao.getInstance().put(userDto);

        RestApiAppResponse<UserDto> response = new RestApiAppResponse<>(true, "User created successfully", userDto);
        return new ResponseBuilder().setStatus(200).setBody(gson.toJson(response));
    }
}

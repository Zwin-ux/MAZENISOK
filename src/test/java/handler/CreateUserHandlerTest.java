package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UserDao;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;

import java.util.List;

public class CreateUserHandlerTest {

  private static final Gson gson = new Gson();

  @Test(singleThreaded = true)
  public void createUserTest() {
    UserDao.reset();
    String user1 = String.valueOf(Math.random());
    UserDto user = new UserDto();
    user.setUserName(user1);
    String test1 = "POST /api/users HTTP/1.1\n"
        + "Host: test\n"
        + "Connection: Keep-Alive\n"
        + "Content-Type: application/json\n"
        + "\n"
        + gson.toJson(user);
    CustomHttpResponse response = Server.processRequest(test1);
    Assert.assertEquals(response.status, "200 OK");
    RestApiAppResponse<UserDto> appResponse = gson.fromJson(response.body, new TypeToken<RestApiAppResponse<UserDto>>(){}.getType());
    Assert.assertTrue(appResponse.success);
    List<UserDto> users = UserDao.getInstance().getAll();
    Assert.assertEquals(users.size(), 1);
    Assert.assertNotNull(users.get(0).getUniqueId());
    Assert.assertEquals(users.get(0).getUserName(), user1);
    Assert.assertEquals(users.get(0).getBalance().doubleValue(), 0.0);
  }
}

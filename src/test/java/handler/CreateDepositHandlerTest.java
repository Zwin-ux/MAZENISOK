package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import server.Server;
import com.google.gson.Gson;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.List;

public class CreateDepositHandlerTest {

  private static final Gson gson = new Gson();

  @Test(singleThreaded = true)
  public void createDepositTest(){
    TransactionDao.reset();
    UserDao.reset();

    UserDao userDao = UserDao.getInstance();
    String userId = String.valueOf(Math.random());
    UserDto user = new UserDto(userId, "TestUser", "test@example.com");
    user.setBalance(BigDecimal.ZERO);
    userDao.put(user);

    BigDecimal depositAmount = BigDecimal.valueOf(100.00);

    String test1 = "POST /api/transactions/deposit?userId=" + userId + "&amount=" + depositAmount + " HTTP/1.1\n"
        + "Host: test\n"
        + "Connection: Keep-Alive\n"
        + "\n";

    CustomHttpResponse response = Server.processRequest(test1);
    Assert.assertEquals(response.status, "201 Created");

    RestApiAppResponse<TransactionDto> appResponse = gson.fromJson(response.body, new TypeToken<RestApiAppResponse<TransactionDto>>(){}.getType());
    Assert.assertTrue(appResponse.success);

    List<TransactionDto> transactionDtos = TransactionDao.getInstance().getAll();
    Assert.assertEquals(transactionDtos.size(), 1);
    Assert.assertNotNull(transactionDtos.get(0).getUniqueId());
    Assert.assertEquals(transactionDtos.get(0).getAmount(), depositAmount);

    UserDto updatedUser = userDao.get(userId).orElse(null);
    Assert.assertNotNull(updatedUser);
    Assert.assertEquals(updatedUser.getBalance(), depositAmount);
  }
}

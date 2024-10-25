package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UserDao;
import dto.TransferRequestDto;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;
import testutils.TestUtils;
import java.math.BigDecimal;

public class TransferSuccessTest {
    private static final Gson gson = new Gson();

    @Test(singleThreaded = true)
    public void transferTest5() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        UserDto user2 = TestUtils.createUser();

        BigDecimal randomSeed = BigDecimal.valueOf(Math.random());
        BigDecimal amountToTransfer = randomSeed.multiply(BigDecimal.valueOf(100));
        BigDecimal amountToDeposit = randomSeed.multiply(BigDecimal.valueOf(200));

        UserDao userDao = UserDao.getInstance();
        user1.setBalance(amountToDeposit);
        userDao.put(user1);

        TransferRequestDto requestDto = new TransferRequestDto(amountToTransfer, user1.getUniqueId(), user2.getUniqueId());
        String test1 = "POST /api/transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "Content-Type: application/json\n"
                + "\n"
                + gson.toJson(requestDto);
        CustomHttpResponse response = Server.processRequest(test1);
        Assert.assertEquals(response.status, "200 OK");
        RestApiAppResponse<?> userRes = gson.fromJson(response.body,
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, null);

        user1 = userDao.get(user1.getUniqueId()).orElseThrow();
        user2 = userDao.get(user2.getUniqueId()).orElseThrow();
        Assert.assertEquals(user1.getBalance(), amountToDeposit.subtract(amountToTransfer));
        Assert.assertEquals(user2.getBalance(), amountToTransfer);
    }
}

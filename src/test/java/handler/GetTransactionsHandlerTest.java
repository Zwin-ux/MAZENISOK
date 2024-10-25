package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.TransactionDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.TransactionListDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;

import java.math.BigDecimal;

public class GetTransactionsHandlerTest {
    private static final Gson gson = new Gson();

    @Test(singleThreaded = true)
    public void getTransactionsTest() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());

        String messageId1 = String.valueOf(Math.random());
        TransactionDto transactionDto1 = new TransactionDto(messageId1, userId, null, BigDecimal.valueOf(Math.random()), TransactionType.Deposit);

        TransactionDto transactionDto2 = new TransactionDto(String.valueOf(Math.random()), userId, null, BigDecimal.valueOf(Math.random()), TransactionType.Withdraw);

        TransactionDao transactionDao = TransactionDao.getInstance();

        transactionDao.put(transactionDto1);
        transactionDao.put(transactionDto2);

        String test1 = "GET /api/transactions?userId=" + userId + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        CustomHttpResponse response = Server.processRequest(test1);
        RestApiAppResponse<TransactionListDto> messages = gson.fromJson(response.body,
                new TypeToken<RestApiAppResponse<TransactionListDto>>() {}.getType());
        Assert.assertEquals(messages.data.getTransactions().size(), 2);
    }

    @Test(singleThreaded = true)
    public void getTransactionsTestNoTransactions() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());

        String test1 = "GET /api/transactions?userId=" + userId + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        CustomHttpResponse response = Server.processRequest(test1);
        RestApiAppResponse<TransactionListDto> messages = gson.fromJson(response.body,
                new TypeToken<RestApiAppResponse<TransactionListDto>>() {}.getType());
        Assert.assertEquals(messages.data.getTransactions().size(), 0);
    }
}

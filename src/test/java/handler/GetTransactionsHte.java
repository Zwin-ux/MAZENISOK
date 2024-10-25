package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.TransactionDao;
import dto.TransactionDto;
import dto.TransactionType;
import response.CustomHttpResponse;
import response.RestApiAppResponse;
import server.Server;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class GetTransactionsHte {
    private static final Gson gson = new Gson();

    @Test(singleThreaded = true)
    public void getTransactionsTest2() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());

        String messageId1 = String.valueOf(Math.random());
        TransactionDto transactionDto1 = new TransactionDto(messageId1, userId, null, BigDecimal.valueOf(Math.random()), TransactionType.Deposit);

        TransactionDto transactionDto2 = new TransactionDto(String.valueOf(Math.random()), userId, null, BigDecimal.valueOf(Math.random()), TransactionType.Withdraw);
        TransactionDao transactionDao = TransactionDao.getInstance();

        transactionDao.put(transactionDto1);
        transactionDao.put(transactionDto2);
        
        String randomUserId = String.valueOf(Math.random());
        String test1 = "GET /api/transactions?userId=" + randomUserId + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        CustomHttpResponse response = Server.processRequest(test1);
        RestApiAppResponse<dto.TransactionListDto> messages = gson.fromJson(response.body,
                new TypeToken<RestApiAppResponse<dto.TransactionListDto>>() {}.getType());
        Assert.assertEquals(messages.data.getTransactions().size(), 0);
    }
}

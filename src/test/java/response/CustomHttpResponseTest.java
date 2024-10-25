package response;

import dto.TransactionDto;
import dto.TransactionType;
import com.google.gson.Gson;
import java.util.Map;
import java.math.BigDecimal;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomHttpResponseTest {

  private static final Gson gson = new Gson();

  @Test
  public void testHttpRes(){
    String randKey = String.valueOf(Math.random());
    String rand = String.valueOf(Math.random());
    TransactionDto transactionDto = new TransactionDto(
        String.valueOf(Math.random()), // uniqueId
        String.valueOf(Math.random()), // userId
        null, // toUserId (null for deposit)
        BigDecimal.valueOf(100.00), // amount
        TransactionType.Deposit // type
    );
    RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(true,
        "Transaction created", transactionDto);
    CustomHttpResponse res = new ResponseBuilder()
        .setStatus(200) // Changed to int
        .setVersion("HTTP/1.1")
        .setBody(gson.toJson(response))
        .setHeaders(Map.of(randKey, rand))
        .build();
    Assert.assertEquals(res.toString(), "HTTP/1.1 200 OK\n"
        + randKey + ": " + rand +"\n\n" + gson.toJson(response));
  }
}

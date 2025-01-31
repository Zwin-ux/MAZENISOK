package handler;

import com.google.gson.Gson;
import dao.TransactionDao;
import dto.TransactionDto;
import dto.TransactionListDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class GetTransactionsHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        String userId = request.getQueryParam("userId");
        
        if (userId == null || userId.isEmpty()) {
            return new ResponseBuilder().setStatus(400).setBody("Missing userId parameter");
        }

        List<TransactionDto> transactions = TransactionDao.getInstance().getByUserId(userId);
        TransactionListDto transactionListDto = new TransactionListDto(transactions);
        
        RestApiAppResponse<TransactionListDto> response = new RestApiAppResponse<>(
            true,
            "Transactions retrieved successfully",
            transactionListDto
        );
        
        return new ResponseBuilder().setStatus(200).setBody(gson.toJson(response));
    }
}

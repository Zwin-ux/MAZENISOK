package handler;

import com.google.gson.Gson;
import dao.TransactionDao;
import dto.TransactionDto;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class TransactionHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        String method = request.getMethod();
        
        if (method.equals("GET")) {
            return handleGetTransactions(request);
        } else {
            return new ResponseBuilder().setStatus(405).setBody("Method not allowed");
        }
    }

    private ResponseBuilder handleGetTransactions(ParsedRequest request) {
        String userId = request.getQueryParam("userId");
        
        if (userId == null || userId.isEmpty()) {
            return new ResponseBuilder().setStatus(400).setBody("Missing userId parameter");
        }

        List<TransactionDto> transactions = TransactionDao.getInstance().getByUserId(userId);
        
        RestApiAppResponse<List<TransactionDto>> response = new RestApiAppResponse<>(
            true,
            "Transactions retrieved successfully",
            transactions
        );
        
        return new ResponseBuilder().setStatus(200).setBody(gson.toJson(response));
    }
}

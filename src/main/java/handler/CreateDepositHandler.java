package handler;

import dao.UserDao;
import dao.TransactionDao;
import dto.UserDto;
import dto.TransactionDto;
import dto.TransactionType;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateDepositHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        String userId = request.getQueryParam("userId");
        String amountStr = request.getQueryParam("amount");

        if (userId == null || amountStr == null) {
            return new ResponseBuilder().setStatus(400).setBody("Missing userId or amount");
        }

        UserDao userDao = UserDao.getInstance();
        UserDto user = userDao.get(userId).orElse(null);

        if (user == null) {
            return new ResponseBuilder().setStatus(404).setBody("User not found");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
        } catch (Exception e) {
            return new ResponseBuilder().setStatus(400).setBody("Invalid amount");
        }

        // Update user balance
        user.setBalance(user.getBalance().add(amount));
        userDao.update(user);

        // Create transaction
        String transactionId = UUID.randomUUID().toString();
        TransactionDto transactionDto = new TransactionDto(transactionId, userId, null, amount, TransactionType.Deposit);
        TransactionDao.getInstance().put(transactionDto);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(
            true,
            "Deposit successful",
            transactionDto
        );
        return new ResponseBuilder().setStatus(201).setBody(gson.toJson(response));
    }
}

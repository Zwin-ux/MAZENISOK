package handler;

import com.google.gson.Gson;
import dao.UserDao;
import dao.TransactionDao;
import dto.DepositRequestDto;
import dto.UserDto;
import dto.TransactionDto;
import dto.TransactionType;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        DepositRequestDto depositRequest;
        try {
            depositRequest = gson.fromJson(request.getBody(), DepositRequestDto.class);
        } catch (Exception e) {
            return new ResponseBuilder().setStatus(400).setBody("Invalid deposit request format");
        }

        UserDao userDao = UserDao.getInstance();
        TransactionDao transactionDao = TransactionDao.getInstance();

        UserDto user = userDao.get(depositRequest.userId).orElse(null);
        if (user == null) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Invalid user.", null)));
        }

        BigDecimal amount = depositRequest.amount;
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Invalid deposit amount", null)));
        }

        // Perform the deposit
        user.setBalance(user.getBalance().add(amount));
        userDao.update(user);

        // Create and save the transaction
        TransactionDto transaction = new TransactionDto(
            UUID.randomUUID().toString(),
            depositRequest.userId,
            null,
            amount,
            TransactionType.Deposit
        );
        transactionDao.put(transaction);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(
            true,
            "Deposit successful",
            transaction
        );
        return new ResponseBuilder().setStatus(200).setBody(gson.toJson(response));
    }
}
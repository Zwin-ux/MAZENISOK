package handler;

import com.google.gson.Gson;
import dao.UserDao;
import dao.TransactionDao;
import dto.TransferRequestDto;
import dto.UserDto;
import dto.TransactionDto;
import dto.TransactionType;
import request.ParsedRequest;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferHandler implements BaseHandler {
    private static final Gson gson = new Gson();

    @Override
    public ResponseBuilder handleRequest(ParsedRequest request) {
        TransferRequestDto transferRequest;
        try {
            transferRequest = gson.fromJson(request.getBody(), TransferRequestDto.class);
        } catch (Exception e) {
            return new ResponseBuilder().setStatus(400).setBody("Invalid transfer request format");
        }

        UserDao userDao = UserDao.getInstance();
        TransactionDao transactionDao = TransactionDao.getInstance();

        UserDto fromUser = userDao.get(transferRequest.fromId).orElse(null);
        UserDto toUser = userDao.get(transferRequest.toId).orElse(null);

        if (fromUser == null) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Invalid from user.", null)));
        }
        if (toUser == null) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Invalid user to transfer.", null)));
        }

        BigDecimal amount = transferRequest.amount;
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Invalid transfer amount", null)));
        }

        if (fromUser.getBalance().compareTo(amount) < 0) {
            return new ResponseBuilder().setStatus(400).setBody(gson.toJson(new RestApiAppResponse<>(false, "Not enough funds.", null)));
        }

        // Perform the transfer
        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        toUser.setBalance(toUser.getBalance().add(amount));

        userDao.update(fromUser);
        userDao.update(toUser);

        // Create and save the transaction
        TransactionDto transaction = new TransactionDto(
            UUID.randomUUID().toString(),
            transferRequest.fromId,
            transferRequest.toId,
            amount,
            TransactionType.Transfer
        );
        transactionDao.put(transaction);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(
            true,
            "Transfer successful",
            transaction
        );
        return new ResponseBuilder().setStatus(200).setBody(gson.toJson(response));
    }
}

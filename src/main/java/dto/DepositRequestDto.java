package dto;

import java.math.BigDecimal;

public class DepositRequestDto {
    public String userId;
    public BigDecimal amount;

    public DepositRequestDto(String userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }
}

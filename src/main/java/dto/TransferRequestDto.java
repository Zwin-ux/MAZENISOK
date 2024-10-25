package dto;

import java.math.BigDecimal;

public class TransferRequestDto {

    public final BigDecimal amount;
    public final String fromId;
    public final String toId;

    public TransferRequestDto(BigDecimal amount, String fromId, String toId) {
        this.amount = amount;
        this.fromId = fromId;
        this.toId = toId;
    }
}

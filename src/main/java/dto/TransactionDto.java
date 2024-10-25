package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto extends BaseDto {
    private String userId;
    private String toUserId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType type;

    public TransactionDto(String uniqueId, String userId, String toUserId, BigDecimal amount, TransactionType type) {
        super(uniqueId);
        this.userId = userId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "uniqueId='" + getUniqueId() + '\'' +
                ", userId='" + userId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }
}

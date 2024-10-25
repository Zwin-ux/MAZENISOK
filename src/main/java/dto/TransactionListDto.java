package dto;

import java.util.List;

public class TransactionListDto extends BaseDto {
    private List<TransactionDto> transactions;

    public TransactionListDto(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
}

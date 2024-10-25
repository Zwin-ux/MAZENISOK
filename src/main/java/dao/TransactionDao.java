package dao;

import dto.TransactionDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionDao implements BaseDao<TransactionDto> {

    private static TransactionDao instance = new TransactionDao();
    private final Map<String, TransactionDto> transactions = new ConcurrentHashMap<>();

    public static TransactionDao getInstance() {
        return instance;
    }

    @Override
    public void put(TransactionDto transactionDto) {
        transactions.put(transactionDto.getUniqueId(), transactionDto);
    }

    @Override
    public Optional<TransactionDto> get(String uniqueId) {
        return Optional.ofNullable(transactions.get(uniqueId));
    }

    @Override
    public List<TransactionDto> getAll() {
        return List.copyOf(transactions.values());
    }

    @Override
    public boolean exists(String uniqueId) {
        return transactions.containsKey(uniqueId);
    }

    @Override
    public void delete(String uniqueId) {
        transactions.remove(uniqueId);
    }

    @Override
    public void update(TransactionDto transactionDto) {
        transactions.replace(transactionDto.getUniqueId(), transactionDto);
    }

    public List<TransactionDto> getByUserId(String userId) {
        return getAll().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // only for testing, do not call this method
    public static void reset() {
        instance = new TransactionDao();
    }
}

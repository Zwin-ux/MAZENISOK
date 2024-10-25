package dao;

import dto.TransactionDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// TODO fill this out
public class TransactionDao implements BaseDao<TransactionDto> {

    private static TransactionDao instance = new TransactionDao();
    private final Map<String, TransactionDto> transactions = new ConcurrentHashMap<>();

    public static TransactionDao getInstance() {
        return instance;
    }

    // TODO fill this out
    @Override
    public void put(TransactionDto transactionDto) {
        transactions.put(transactionDto.getUniqueId(), transactionDto);
    }

    // TODO fill this out
    @Override
    public Optional<TransactionDto> get(String uniqueId) {
        return Optional.ofNullable(transactions.get(uniqueId));
    }

    // TODO fill this out
    @Override
    public List<TransactionDto> getAll() {
        return List.copyOf(transactions.values());
    }

    // TODO fill this out
    @Override
    public boolean exists(String uniqueId) {
        return transactions.containsKey(uniqueId);
    }

    // TODO fill this out
    @Override
    public void delete(String uniqueId) {
        transactions.remove(uniqueId);
    }

    // TODO fill this out
    @Override
    public void update(TransactionDto transactionDto) {
        transactions.replace(transactionDto.getUniqueId(), transactionDto);
    }

    // only for testing, do not call this method
    public static void reset() {
        instance = new TransactionDao();
    }
}

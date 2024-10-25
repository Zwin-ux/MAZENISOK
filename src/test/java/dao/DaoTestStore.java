package dao;

import dto.TransactionDto;
import dto.TransactionType;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;

public class DaoTestStore {

    @Test(singleThreaded = true)
    public void testStore(){
        TransactionDao.reset();
        TransactionDao transactionDao = TransactionDao.getInstance();
        String id = String.valueOf(Math.random());
        String userId = "testUser";
        BigDecimal amount = new BigDecimal("100.00");
        TransactionDto transactionDto = new TransactionDto(id, userId, null, amount, TransactionType.Deposit);
        transactionDao.put(transactionDto);
        
        Assert.assertTrue(transactionDao.get(String.valueOf(Math.random())).isEmpty());
        Assert.assertEquals(transactionDao.get(id).orElse(null), transactionDto);
        Assert.assertTrue(transactionDao.get(String.valueOf(Math.random())).isEmpty());
    }
}

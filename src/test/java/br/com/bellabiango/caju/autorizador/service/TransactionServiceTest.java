package br.com.bellabiango.caju.autorizador.service;

import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import br.com.bellabiango.caju.autorizador.entity.Merchant;
import br.com.bellabiango.caju.autorizador.entity.Transaction;
import br.com.bellabiango.caju.autorizador.repository.AccountBalanceRepository;
import br.com.bellabiango.caju.autorizador.repository.MerchantRepository;
import br.com.bellabiango.caju.autorizador.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.HashMap;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountBalanceRepository accountBalanceRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private MerchantRepository merchantRepository;

    @Test
    public void testSimpleAuthorizer_Approved() {
        Transaction transaction = new Transaction();
        transaction.setAccount("123");
        transaction.setTotalAmount(50.0);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE");

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("123");
        accountBalance.setBalances(new HashMap<>());
        accountBalance.getBalances().put("MEAL", 100.0);

        when(accountBalanceRepository.findById("123")).thenReturn(Optional.of(accountBalance));

        String code = transactionService.processTransaction(transaction);

        assertEquals("00", code);
        verify(accountBalanceRepository, times(1)).save(accountBalance);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testSimpleAuthorizer_InsufficientFunds() {
        Transaction transaction = new Transaction();
        transaction.setAccount("123");
        transaction.setTotalAmount(50.0);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE");

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("123");
        accountBalance.setBalances(new HashMap<>());
        accountBalance.getBalances().put("MEAL", 10.0);

        when(accountBalanceRepository.findById("123")).thenReturn(Optional.of(accountBalance));

        String code = transactionService.processTransaction(transaction);

        assertEquals("51", code);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testSimpleAuthorizer_Error() {
        Transaction transaction = new Transaction();
        transaction.setAccount("123");
        transaction.setTotalAmount(50.0);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE");

        when(accountBalanceRepository.findById("123")).thenThrow(new RuntimeException("Erro ao acessar o saldo da conta"));

        String code = transactionService.processTransaction(transaction);

        assertEquals("07", code);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testAuthorizerWithFallback_UsesCashBalance() {
        Transaction transaction = new Transaction();
        transaction.setAccount("123");
        transaction.setTotalAmount(50.0);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE");

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("123");
        accountBalance.setBalances(new HashMap<>());
        accountBalance.getBalances().put("MEAL", 0.0);
        accountBalance.getBalances().put("CASH", 100.0);

        when(accountBalanceRepository.findById("123")).thenReturn(Optional.of(accountBalance));

        String code = transactionService.processTransaction(transaction);

        assertEquals("00", code);
        verify(accountBalanceRepository, times(1)).save(accountBalance);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testMerchantDependent_MerchantOverride() {
        Transaction transaction = new Transaction();
        transaction.setAccount("123");
        transaction.setTotalAmount(50.0);
        transaction.setMcc("1111");
        transaction.setMerchant("Restaurante da Isabella");

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("123");
        accountBalance.setBalances(new HashMap<>());
        accountBalance.getBalances().put("MEAL", 70.0);

        Merchant merchant = new Merchant();
        merchant.setMcc("5812");
        merchant.setName("Restaurante da Isabella");

        when(merchantRepository.findByName(transaction.getMerchant())).thenReturn(Optional.of(merchant));
        when(accountBalanceRepository.findById("123")).thenReturn(Optional.of(accountBalance));

        String code = transactionService.processTransaction(transaction);

        assertEquals("00", code);
        verify(accountBalanceRepository, times(1)).save(accountBalance);
        verify(transactionRepository, times(1)).save(transaction);
    }
}


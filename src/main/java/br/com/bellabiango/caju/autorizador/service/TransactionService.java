package br.com.bellabiango.caju.autorizador.service;

import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import br.com.bellabiango.caju.autorizador.entity.Merchant;
import br.com.bellabiango.caju.autorizador.entity.Transaction;
import br.com.bellabiango.caju.autorizador.repository.AccountBalanceRepository;
import br.com.bellabiango.caju.autorizador.repository.MerchantRepository;
import br.com.bellabiango.caju.autorizador.repository.TransactionRepository;
import br.com.bellabiango.caju.autorizador.utils.MccCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MerchantRepository merchantRepository;


    public List<Transaction> getTransactionsByAccount(String accountId) {
        return transactionRepository.findByAccount(accountId);
    }

    @Transactional
    public String processTransaction(Transaction transaction) {
        long startTime = System.currentTimeMillis();
        try {
            String category = determineCategory(transaction);

            String resultCode = processAccountBalance(transaction, category);

            transaction.setCode(resultCode);
            transaction.setDateTime(LocalDateTime.now());
            transactionRepository.save(transaction);

            // Verificar o tempo de processamento
            long processingTime = System.currentTimeMillis() - startTime;
            if (processingTime > 100) {
                System.err.println("Transaction processing exceeded 100ms");
            }

            return resultCode;

        } catch (Exception e) {
            // Outros erros
            transaction.setCode("07");
            transaction.setDateTime(LocalDateTime.now());
            transactionRepository.save(transaction);

            e.printStackTrace();
            return "07";
        }
    }

    private String processAccountBalance(Transaction transaction, String category) {
        AccountBalance accountBalance = accountBalanceRepository.findById(transaction.getAccount())
                .orElseGet(() -> {
                    AccountBalance newBalance = new AccountBalance();
                    newBalance.setAccountId(transaction.getAccount());
                    newBalance.setBalances(new ConcurrentHashMap<>());
                    return newBalance;
                });

        // Sincronizar para garantir a atomicidade das operações
        synchronized (accountBalance) {
            Double availableBalance = accountBalance.getBalances().getOrDefault(category, 0.0);

            if (availableBalance >= transaction.getTotalAmount()) {
                // Deduzir do saldo da categoria
                accountBalance.getBalances().put(category, availableBalance - transaction.getTotalAmount());
            } else if (!"CASH".equals(category)) {
                // Tentar o saldo de CASH
                Double cashBalance = accountBalance.getBalances().getOrDefault("CASH", 0.0);
                if (cashBalance >= transaction.getTotalAmount()) {
                    accountBalance.getBalances().put("CASH", cashBalance - transaction.getTotalAmount());
                } else {
                    return "51";
                }
            } else {
                return "51"; // Rejeitada (Saldo insuficiente)
            }

            accountBalanceRepository.save(accountBalance);
        }
        return "00"; // Aprovada
    }

    private String determineCategory(Transaction transaction) {
        // Determinar categoria com base no nome do comerciante (O nome do comerciante tem maior precedência sobre as MCCs.)
        Optional<Merchant> merchantOpt = merchantRepository.findByName(transaction.getMerchant().trim());

        String mcc = null;

        if (merchantOpt.isPresent()) {
            mcc = merchantOpt.get().getMcc();
        }

        String category = MccCategory.getCategoryByMcc(mcc == null ? transaction.getMcc() : mcc);

        if (category == null) {
            category = "CASH";
        }
        return category;
    }
}

package br.com.bellabiango.caju.autorizador.service;

import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import br.com.bellabiango.caju.autorizador.repository.AccountBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountBalanceService {

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    public AccountBalance createAccountBalance(AccountBalance accountBalance) {
        return accountBalanceRepository.save(accountBalance);
    }

    public List<AccountBalance> getAllAccountBalances() {
        return accountBalanceRepository.findAll();
    }
}

package br.com.bellabiango.caju.autorizador.controller;

import br.com.bellabiango.caju.autorizador.documentation.AccountBalanceDocumentation;
import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import br.com.bellabiango.caju.autorizador.service.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountBalanceController implements AccountBalanceDocumentation {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @PostMapping
    public ResponseEntity<AccountBalance> createAccountBalance(@RequestBody AccountBalance accountBalance) {
        AccountBalance savedAccountBalance = accountBalanceService.createAccountBalance(accountBalance);
        return ResponseEntity.ok(savedAccountBalance);
    }

    @GetMapping
    public ResponseEntity<List<AccountBalance>> getAllAccountBalances() {
        List<AccountBalance> accountBalances = accountBalanceService.getAllAccountBalances();
        return ResponseEntity.ok(accountBalances);
    }
}

package br.com.bellabiango.caju.autorizador.controller;

import br.com.bellabiango.caju.autorizador.documentation.TransactionDocumentation;
import br.com.bellabiango.caju.autorizador.entity.Transaction;
import br.com.bellabiango.caju.autorizador.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController implements TransactionDocumentation {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Map<String, String>> processTransaction(@RequestBody Transaction transaction) {
        String code = transactionService.processTransaction(transaction);
        Map<String, String> response = new HashMap<>();
        response.put("code", code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable String accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountId);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
}


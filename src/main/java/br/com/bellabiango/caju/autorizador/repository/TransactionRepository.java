package br.com.bellabiango.caju.autorizador.repository;

import br.com.bellabiango.caju.autorizador.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByAccount(String account);
}


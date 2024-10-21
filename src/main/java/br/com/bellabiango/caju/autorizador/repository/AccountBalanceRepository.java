package br.com.bellabiango.caju.autorizador.repository;

import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountBalanceRepository extends MongoRepository<AccountBalance, String> {
}

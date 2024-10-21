package br.com.bellabiango.caju.autorizador.repository;

import br.com.bellabiango.caju.autorizador.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MerchantRepository extends MongoRepository<Merchant, String> {
    Optional<Merchant> findByName(String name);
}



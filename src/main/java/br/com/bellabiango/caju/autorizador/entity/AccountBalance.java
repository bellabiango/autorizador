package br.com.bellabiango.caju.autorizador.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Map;

@Data
@Document(collection = "account_balances")
public class AccountBalance {
    @Id
    @NotNull(message = "O accountId não pode ser nulo")
    @Schema(description = "Número da conta", example = "123")
    private String accountId;

    @NotEmpty(message = "O balances não pode ser vazio")
    @Schema(
            description = "Mapa de saldos por categoria",
            example = "{\"FOODS\": 100.0, \"CASH\": 50.0}"
    )
    private Map<String, Double> balances; // Key: Category, Value: Balance
}


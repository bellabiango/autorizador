package br.com.bellabiango.caju.autorizador.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Schema(description = "Número da conta", example = "123")
    private String account;

    @Schema(description = "Valor total da transação", example = "10.00")
    private double totalAmount;

    @Schema(description = "Código MCC", example = "5811")
    private String mcc;

    @Schema(description = "Nome do comerciante", example = "PADARIA DO ZE SAO PAULO BR")
    private String merchant;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Resposta da transação", example = "00", accessMode = Schema.AccessMode.READ_ONLY)
    private String code;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Data e hora da transação", example = "2024-10-21T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateTime;

}

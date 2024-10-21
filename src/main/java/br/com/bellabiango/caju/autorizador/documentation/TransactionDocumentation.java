package br.com.bellabiango.caju.autorizador.documentation;

import br.com.bellabiango.caju.autorizador.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
@Schema(description = "Representa a transação")
public interface TransactionDocumentation {
    @Operation(summary = "Processar uma transação", description = "Processa uma transação e retorna um código de resposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação processada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Map<String, String>> processTransaction(Transaction transaction);

    @Operation(summary = "Buscar transações por conta", description = "Retorna uma lista de transações para a conta especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<Transaction>> getTransactionsByAccount(String accountId);
}

package br.com.bellabiango.caju.autorizador.documentation;

import br.com.bellabiango.caju.autorizador.entity.AccountBalance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;
@Schema(description = "Representa o saldo de uma conta e suas categorias")
public interface AccountBalanceDocumentation {


    @Operation(summary = "Criar um novo saldo de conta", description = "Insere um novo AccountBalance no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo de conta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<AccountBalance> createAccountBalance(AccountBalance accountBalance);


    @Operation(summary = "Listar todos os saldos de contas", description = "Recupera todos os AccountBalance do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de saldos de contas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<AccountBalance>> getAllAccountBalances();
}

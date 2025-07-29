package br.edu.ifba.segurancaApp.controles;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.segurancaApp.dtos.OperacaoForm;
import br.edu.ifba.segurancaApp.servicos.OperacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/operacao")
public class OperacaoController {

    private OperacaoService operacaoService;

    public OperacaoController(OperacaoService operacaoService) {
        this.operacaoService = operacaoService;
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> realizarDeposito(@RequestBody @Valid OperacaoForm depositoForm) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Gets the login/email from JWT subject

        try {
            operacaoService.realizarDeposito(depositoForm, username);
        } catch (Exception e) { //TODO handle specific exceptions
            return ResponseEntity.badRequest().body("Erro ao realizar depósito: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/saque")
    public ResponseEntity<?> realizarSaque(@RequestBody @Valid OperacaoForm saqueForm) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            operacaoService.realizarSaque(saqueForm, username);
        } catch (Exception e) { //TODO handle specific exceptions
            return ResponseEntity.badRequest().body("Erro ao realizar saque: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pagamento")
    public ResponseEntity<?> realizarPagamento(@RequestBody @Valid OperacaoForm pagamentoForm) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            operacaoService.realizarPagamento(pagamentoForm, username);
        } catch (Exception e) {//TODO handle specific exceptions
            return ResponseEntity.badRequest().body("Erro ao realizar pagamento: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/extrato/{numeroConta}")
    public ResponseEntity<?> consultarExtrato(@PathVariable Long numeroConta) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            var extrato = operacaoService.consultarExtrato(numeroConta, username);
            return ResponseEntity.ok(extrato);
        } catch (Exception e) { //TODO handle specific exceptions
            return ResponseEntity.badRequest().body("Erro ao consultar extrato: " + e.getMessage());
        }
    }
}

package br.edu.ifba.segurancaApp.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record OperacaoForm(Long numeroConta,
                             @NotNull
                             @DecimalMin(value = "0.0", inclusive = false)
                             BigDecimal valor,
                             String descricao) {
}

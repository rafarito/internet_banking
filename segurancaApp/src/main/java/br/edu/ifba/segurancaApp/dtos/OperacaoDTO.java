package br.edu.ifba.segurancaApp.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.edu.ifba.segurancaApp.entidades.Operacao;
import br.edu.ifba.segurancaApp.entidades.TipoOperacao;

public record OperacaoDTO(Long id, TipoOperacao tipo, BigDecimal valor, LocalDateTime dataHora, String descricao) {
    public OperacaoDTO(Operacao operacao) {
        this(operacao.getId(), operacao.getTipo(), operacao.getValor(), operacao.getDataHora(), operacao.getDescricao());
    }
}

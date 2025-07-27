package br.edu.ifba.segurancaApp.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "operacoes")
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String descricao;
    @ManyToOne
    private Conta conta;


}

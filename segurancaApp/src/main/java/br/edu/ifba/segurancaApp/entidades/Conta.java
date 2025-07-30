package br.edu.ifba.segurancaApp.entidades;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long numero;
    private String agencia;
    private BigDecimal saldo;
    @OneToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "conta")
    private List<Operacao> operacoes;

    public Conta() {
    }
    
    public Conta(long numero, String agencia, BigDecimal saldo, Usuario usuario) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }

}

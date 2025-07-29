package br.edu.ifba.segurancaApp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.segurancaApp.entidades.Operacao;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {


}

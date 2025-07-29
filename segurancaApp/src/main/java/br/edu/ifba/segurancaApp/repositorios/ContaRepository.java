package br.edu.ifba.segurancaApp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.segurancaApp.entidades.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Conta findByNumero(Long numero);

}

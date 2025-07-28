package br.edu.ifba.segurancaApp.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.segurancaApp.entidades.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role);

}

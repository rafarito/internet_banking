package br.edu.ifba.segurancaApp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.segurancaApp.entidades.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByLogin(String username);

}

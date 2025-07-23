package br.edu.ifba.segurancaApp.dtos;

import br.edu.ifba.segurancaApp.entidades.Usuario;

public record UsuarioDTO(Long id, String nome, String login) {

	public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin());
	}
}

package br.edu.ifba.segurancaApp.dtos;

import br.edu.ifba.segurancaApp.entidades.Usuario;

public record UsuarioForm(Long id, String nome, String login, String senha, String cpf) {
	public UsuarioForm(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getCpf());
}}

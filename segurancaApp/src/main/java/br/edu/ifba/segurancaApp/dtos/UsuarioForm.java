package br.edu.ifba.segurancaApp.dtos;

import org.hibernate.validator.constraints.br.CPF;

import br.edu.ifba.segurancaApp.entidades.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioForm(
	Long id, 
	@NotBlank(message = "Nome é obrigatório") 
	String nome, 
	@NotBlank(message = "Email é obrigatório") 
	@Email(message = "Email deve ter um formato válido") 
	String login, 
	@NotBlank(message = "Senha é obrigatória") 
	@Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") 
	String senha, 
	@CPF(message = "CPF deve ter um formato válido") 
	@NotBlank(message = "CPF é obrigatório") 
	String cpf
) {
	public UsuarioForm(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getCpf());
}}

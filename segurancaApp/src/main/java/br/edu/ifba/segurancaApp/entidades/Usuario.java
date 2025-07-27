package br.edu.ifba.segurancaApp.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifba.segurancaApp.dtos.UsuarioForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity(name = "usuarios")
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String cpf;
	@Column(unique = true, name = "email")
	private String login;
	private String senha;
	@OneToOne
	private Conta conta;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuarios_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
            )
	private List<Role> roles= new ArrayList<Role>();
	private LocalDate dataCadastro;

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Usuario(String nome, String cpf, String login, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.login = login;
		this.senha = senha;
		this.dataCadastro = LocalDate.now();
	}

	public Usuario() {
		
	}
	
	public Usuario(Long id, String nome, String login, String senha) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public Usuario(UsuarioForm usuarioForm) {
		this.id = usuarioForm.id();
		this.nome = usuarioForm.nome();
		this.login = usuarioForm.login();
		this.senha = usuarioForm.senha();
		this.cpf = usuarioForm.cpf();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	

}

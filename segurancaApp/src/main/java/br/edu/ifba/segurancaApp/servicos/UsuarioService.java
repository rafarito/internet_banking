package br.edu.ifba.segurancaApp.servicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.clients.EmailClient;
import br.edu.ifba.segurancaApp.clients.EmailDTO;
import br.edu.ifba.segurancaApp.dtos.UsuarioDTO;
import br.edu.ifba.segurancaApp.dtos.UsuarioForm;
import br.edu.ifba.segurancaApp.entidades.Conta;
import br.edu.ifba.segurancaApp.entidades.Role;
import br.edu.ifba.segurancaApp.entidades.Usuario;
import br.edu.ifba.segurancaApp.repositorios.ContaRepository;
import br.edu.ifba.segurancaApp.repositorios.RoleRepository;
import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private ContaRepository contaRepository;
	private RoleRepository roleRepository;
	private EmailClient emailClient;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ContaRepository contaRepository, 
			RoleRepository roleRepository, EmailClient emailClient) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.contaRepository = contaRepository;
		this.roleRepository = roleRepository;
		this.emailClient = emailClient;
	}
	
	@Transactional
	public UsuarioDTO salvar(UsuarioForm usurioForm) {

		Usuario usuario = new Usuario(usurioForm);
		List<Role> roles = new ArrayList<>();
		Optional<Role> role = roleRepository.findByRole("ROLE_USER");
		if (role.isPresent()) {
			roles.add(role.get());
		} else {
			Role newRole = new Role();
			newRole.setRole("ROLE_USER");
			roleRepository.save(newRole);
			roles.add(newRole);
		}
		usuario.setRoles(roles);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario = usuarioRepository.save(usuario);
		Conta conta = new Conta(usuario.getId(), "0001", new BigDecimal("0.00"), usuario);
		contaRepository.save(conta);
		usuario.setConta(conta);
		usuarioRepository.save(usuario);

		EmailDTO emailDTO = new EmailDTO("rafaritogames@gmail.com",
							 					usurioForm.login(),
												"Boas vindas", 
				"Olá, " + usurioForm.nome() + "! Seu usuário foi criado com sucesso. Automaticamente criamos uma conta para você. " +
				"Você pode acessar o sistema com seu email e a senha que você escolheu.");

		emailClient.sendEmail(emailDTO);

		return new UsuarioDTO(usuario);
	}
	
	public List<UsuarioDTO> listar() {
		return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
	}
}

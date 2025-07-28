package br.edu.ifba.segurancaApp.servicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.dtos.UsuarioDTO;
import br.edu.ifba.segurancaApp.dtos.UsuarioForm;
import br.edu.ifba.segurancaApp.entidades.Conta;
import br.edu.ifba.segurancaApp.entidades.Role;
import br.edu.ifba.segurancaApp.entidades.Usuario;
import br.edu.ifba.segurancaApp.repositorios.ContaRepository;
import br.edu.ifba.segurancaApp.repositorios.RoleRepository;
import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private ContaRepository contaRepository;
	private RoleRepository roleRepository;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ContaRepository contaRepository, 
			RoleRepository roleRepository) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.contaRepository = contaRepository;
		this.roleRepository = roleRepository;
	}
	
	public UsuarioDTO salvar(UsuarioForm usurioForm) {
		// TODO enviar email de criação de conta
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
		return new UsuarioDTO(usuario);
	}
	
	public List<UsuarioDTO> listar() {
		return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
	}
}

package br.edu.ifba.segurancaApp.servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.dtos.UsuarioDTO;
import br.edu.ifba.segurancaApp.dtos.UsuarioForm;
import br.edu.ifba.segurancaApp.entidades.Role;
import br.edu.ifba.segurancaApp.entidades.Usuario;
import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UsuarioDTO salvar(UsuarioForm usurioForm) {
		Usuario usuario = new Usuario(usurioForm);
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setRole("ROLE_USER");
		roles.add(role);
		usuario.setRoles(roles);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario = usuarioRepository.save(usuario);
		return new UsuarioDTO(usuario);
	}
	
	public List<UsuarioDTO> listar() {
		return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
	}
}

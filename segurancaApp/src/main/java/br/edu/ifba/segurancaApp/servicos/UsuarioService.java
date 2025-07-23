package br.edu.ifba.segurancaApp.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.dtos.UsuarioDTO;
import br.edu.ifba.segurancaApp.dtos.UsuarioForm;
import br.edu.ifba.segurancaApp.entidades.Usuario;
import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public UsuarioDTO salvar(UsuarioForm usurioForm) {
		Usuario usuario = new Usuario(usurioForm);
		usuario = usuarioRepository.save(usuario);
		return new UsuarioDTO(usuario);
	}
	
	public List<UsuarioDTO> listar() {
		return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
	}
}

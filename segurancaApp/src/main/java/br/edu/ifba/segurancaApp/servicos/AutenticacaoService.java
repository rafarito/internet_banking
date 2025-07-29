package br.edu.ifba.segurancaApp.servicos;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return (UserDetails)usuarioRepository.findByLogin(username);
	}

}

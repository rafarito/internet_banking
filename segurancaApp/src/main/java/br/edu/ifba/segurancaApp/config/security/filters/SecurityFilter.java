package br.edu.ifba.segurancaApp.config.security.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;
import br.edu.ifba.segurancaApp.servicos.JWTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	private JWTokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	public SecurityFilter(JWTokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = recuperarToken(request);
		if(token!=null) {
			var login = tokenService.getSubject(token);
			var usuario = (UserDetails)usuarioRepository.findByLogin(login);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			System.out.println("Login: " + login);
		}
		filterChain.doFilter(request, response);

	}
	
	public String recuperarToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }


}

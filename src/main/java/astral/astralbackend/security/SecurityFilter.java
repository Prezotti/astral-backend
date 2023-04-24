package astral.astralbackend.security;

import astral.astralbackend.repository.ProdutorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProdutorRepository produtorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var role = tokenService.getRole(tokenJWT);

            if (role.equals("produtor")) {
                var produtor = produtorRepository.findByEmail(subject);
                var authentication = new UsernamePasswordAuthenticationToken(produtor, null, produtor.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } /*else if (role.equals("admin")) {
                var administrador = administradorRepository.findByEmail(subject);
                var authentication = new UsernamePasswordAuthenticationToken(administrador, null, administrador.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }*/
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}

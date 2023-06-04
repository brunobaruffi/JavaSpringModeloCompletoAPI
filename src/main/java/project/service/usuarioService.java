package project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import project.domain.entity.Usuario;

public interface usuarioService extends UserDetailsService {

    Usuario salvar(Usuario usuario);
    UserDetails autenticar(Usuario usuario);

    void teste();

    void testeMongo();
}

package project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Usuario;
import project.domain.repository.Usuarios;
import project.service.*;

@Service
public class usuarioServiceImpl implements usuarioService{

    @Autowired
    private Usuarios usuariorepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Usuario salvar(Usuario usuario){ //exexuta o salvamento no banco. Seria ideal se fose um DTO.
        return usuariorepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user =  loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getSenha(),user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erro de acesso.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuariorepository.findByLogin(username). //busca o usuairo no banco.
                orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado"));
        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN","USER"} : new String[] {"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}

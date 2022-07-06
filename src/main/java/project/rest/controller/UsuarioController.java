package project.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Usuario;
import project.rest.dto.CredenciaisDTO;
import project.rest.dto.TokenDTO;
import project.service.*;
import project.domain.security.jwt.jwtService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final usuarioService usuarioService;
    private final PasswordEncoder passowordEnconder;
    private final jwtService jwtService;

    @Operation(
        method = "POST",
        description = "Cadastramento de usuario",
        summary = "Cadastramento de usuario",
        tags = "/api/usuario")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhacriptografada = passowordEnconder.encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);//criptografa e seta a senha no lugar dela devolta
        return usuarioService.salvar(usuario); // faz o desvio para o service salvar
    }
    @Operation(
        method = "POST",
        description = "Autenticação do usuario",
        summary = "Autenticação do usuario e geração do Bearer",
        tags = "/api/usuario")
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha())
                    .build();

            UserDetails usuarioAutenticado =  usuarioService.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(),token);
        }catch (UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Senha Invalida");
        }
    }
}

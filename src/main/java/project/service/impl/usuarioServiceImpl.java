package project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Usuario;
import project.domain.repository.Usuarios;
import project.domain.repository.MongoRepo;
import project.service.*;

import project.domain.entity.MongoTest;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class usuarioServiceImpl implements usuarioService{

    @Autowired
    private Usuarios usuariorepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    private MongoRepo mongorepo;

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

    @Override
    public void teste(){

        Set<String> nomes = new HashSet<>();

        for(int c=0;c<100000;c++){
            nomes.add(gerarString(10));
        }
        long x = System.currentTimeMillis();

        nomes.parallelStream().forEach(nome -> kafkaTemplate.send("test","{messsage:"+nome+"}"));

        System.out.println(System.currentTimeMillis()-x);
    }

    private String gerarString(Integer tamanho){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = tamanho;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void testeMongo(){
        var arq = MongoTest.builder().nome(gerarString(10)).qtd(1).build();
        try {
            mongorepo.insert(arq);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.toString());
        }

    }
}

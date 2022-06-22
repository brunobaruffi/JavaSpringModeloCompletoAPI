package project.domain.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.domain.entity.Usuario;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;


@Service
public class jwtService {
    @Value("${security.jwt.expiracao}")
    private String expiracao; // tempo de duração do token

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura; // assinatura geral


    public String gerarToken(Usuario usuario){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExoiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExoiracao.atZone(ZoneId.systemDefault()).toInstant()); //define e convefte a data de expiração

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles","admin"); //outras informações
        claims.put("emailUsuario","email@email.com");

        return Jwts.builder() // gera o tokem de saida
                .setSubject(usuario.getLogin()) //define o usuario
                .setExpiration(data)
                // .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String  obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

}

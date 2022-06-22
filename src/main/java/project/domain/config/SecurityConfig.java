package project.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import project.domain.security.jwt.JwtAuthFilter;
import project.domain.security.jwt.jwtService;
import project.service.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Lazy // resolve o problema da dependencia circular com usuarioServiceImpl
    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private jwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){ //encriptação de password
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // onde é configurardo a autenticação
        auth.userDetailsService(usuarioService) //as informações são passadas via usuarioService
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //onde é criada a parte de autorização. oq fazer depois de autenticar
        http.csrf().disable() // disabilita csrf = sistemade segurança web. Como estamos usando API rest n funciona
                .authorizeRequests()
                .antMatchers("/api/aluno/**")
                .hasAnyRole("ADMIN")//com a roles que deseja
                .antMatchers("/api/turma/**")
                .hasRole("ADMIN")
                .antMatchers("/api/pedido/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/usuario/**")
                .permitAll()
                .anyRequest().authenticated()//caso n tenha definido pelo menos vai estar autenticado
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

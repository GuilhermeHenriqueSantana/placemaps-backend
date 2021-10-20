package com.esoft.placemaps.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserLoginDetailService customUserLoginDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        http.authorizeRequests().antMatchers("/api/categoria").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/controleponto/solicitados").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/controleponto/aceitar-solicitacao-pontos/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/controleponto/negar-solicitacao-pontos/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/pedidocadastro").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/plano").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/mensagem").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers("/api/avaliacao/responder-avaliacao").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/controleponto/solicitar-pontos/**").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/controleponto").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/dadosemanal").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/item").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/localizacao").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/localizacao/{id}").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/ponto/categoria/**").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/ponto/ativar/**").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/ponto/desativar/**").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/ponto/obter-pelo-proprietario").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/evento").hasAnyAuthority("PROPRIETARIO");
        http.authorizeRequests().antMatchers("/api/usuario/atualizar-documento").hasAnyAuthority("PROPRIETARIO");

        http.authorizeRequests().antMatchers("/api/avaliacao").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/comentario").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/resposta").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/usuario/lembrar-evento/**").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/usuario/esquecer-evento/**").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/evento/lembretes").hasAnyAuthority("PROPRIETARIO", "USUARIO");
        http.authorizeRequests().antMatchers("/api/opiniao").hasAnyAuthority("PROPRIETARIO", "USUARIO");

        http.authorizeRequests()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers( "/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and();

        http.headers().frameOptions().disable();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserLoginDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

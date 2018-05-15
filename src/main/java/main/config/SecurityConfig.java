package main.config;
import main.util.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, role from users where username = ?")
                .rolePrefix("")
                .passwordEncoder(new ShaPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                authorizeRequests().
                    antMatchers("/login/**").permitAll().
                    antMatchers("/favicon.ico").permitAll().
                    antMatchers("/admin/**").access("hasAuthority('ADMIN')").
                    antMatchers("/secretary/**").access("hasAuthority('SECRETARY')").
                    antMatchers("/secretaryConsultations/**").access("hasAuthority('SECRETARY')").
                    antMatchers("/secretaryPatients/**").access("hasAuthority('SECRETARY')").
                    antMatchers("/doctor/**").access("hasAuthority('DOCTOR')").
                and().formLogin().
                    loginPage("/login").
                    successHandler(authAccessHandler()).
                    failureUrl("/login?error")
                ;

    }

    @Bean
    AuthSuccessHandler authAccessHandler(){
        return new AuthSuccessHandler();
    }
}

package de.judgeman.SpringBootWithSpringSecurityExample.Config;

import de.judgeman.SpringBootWithSpringSecurityExample.Enums.Permission;
import de.judgeman.SpringBootWithSpringSecurityExample.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserService.class)
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(userService.getPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/forAll").permitAll()
                .antMatchers("/admin/**").hasAuthority(Permission.ADMIN_AREA.toString())
                .antMatchers("/user/**").hasAnyAuthority(Permission.ADMIN_AREA.toString(), Permission.USER_AREA.toString())
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).permitAll()
        ;

        return http.build();
    }
}
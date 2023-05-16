package com.forms.lightweight.lightweight.config;

import com.forms.lightweight.lightweight.authentication.AuthEntry;
import com.forms.lightweight.lightweight.authentication.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("AuthEntryPoint")
    private AuthEntry authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/services/**")
                .authenticated().anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

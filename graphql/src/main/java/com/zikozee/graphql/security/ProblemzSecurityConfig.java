package com.zikozee.graphql.security;


import com.zikozee.graphql.datasource.problemz.repository.UserzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class ProblemzSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserzRepository userzRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var authProvider = new ProblemzAuthenticationProvider(userzRepository);
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new ProblemzSecurityFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated().and().csrf().disable();
    }


}

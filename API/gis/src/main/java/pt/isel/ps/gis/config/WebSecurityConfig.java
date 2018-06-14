package pt.isel.ps.gis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BasicAuthenticationPoint basicAuthenticationPoint;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder, BasicAuthenticationPoint basicAuthenticationPoint, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.basicAuthenticationPoint = basicAuthenticationPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
                .antMatchers("/v1").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicAuthenticationPoint);
    }

    public void configureGlobal() throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}

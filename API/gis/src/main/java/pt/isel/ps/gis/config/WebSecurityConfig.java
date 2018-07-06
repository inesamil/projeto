package pt.isel.ps.gis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.components.BasicAuthenticationPoint;
import pt.isel.ps.gis.filters.AuthorizeFilter;
import pt.isel.ps.gis.filters.DebugFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BasicAuthenticationPoint basicAuthenticationPoint;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final MessageSource messageSource;

    public WebSecurityConfig(BasicAuthenticationPoint basicAuthenticationPoint, @Qualifier("userServiceImpl") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationFacade authenticationFacade, MessageSource messageSource) {
        this.basicAuthenticationPoint = basicAuthenticationPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
        this.messageSource = messageSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(getCorsConfigurationSource())
                .and()
                .addFilterBefore(new DebugFilter(), ChannelProcessingFilter.class)
                .addFilterAfter(new AuthorizeFilter(authenticationFacade, messageSource), FilterSecurityInterceptor.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
                .antMatchers("/v1/houses/{house-id}/movements").permitAll()
                .antMatchers("/v1").permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicAuthenticationPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    private static CorsConfigurationSource getCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

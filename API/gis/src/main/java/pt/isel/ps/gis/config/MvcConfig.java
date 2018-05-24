package pt.isel.ps.gis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pt.isel.ps.gis.interceptors.AuthenticationInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    private final AuthenticationInterceptor authenticationInterceptor;

    public MvcConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor);
    }
}

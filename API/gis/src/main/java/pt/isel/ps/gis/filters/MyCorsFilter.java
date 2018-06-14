package pt.isel.ps.gis.filters;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
public class MyCorsFilter extends CorsFilter {

    public MyCorsFilter() {
        super(getCorsConfigurationSource());
    }

    private static CorsConfigurationSource getCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

package pt.isel.ps.gis.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pt.isel.ps.gis.hypermedia.problemDetails.ProblemDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@Component
public class BasicAuthenticationPoint extends BasicAuthenticationEntryPoint {

    private final static String REALM_NAME = "GIS API";

    private final MessageSource messageSource;

    public BasicAuthenticationPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(REALM_NAME);
        super.afterPropertiesSet();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        Locale locale = LocaleContextHolder.getLocale();
        ProblemDetails problemDetails = new ProblemDetails(
                "Requires authentication.",
                httpStatus.value(),
                messageSource.getMessage("need_Authenticate_First", null, locale),
                messageSource.getMessage("need_Authenticate_First", null, locale));
        String body = new ObjectMapper().writeValueAsString(problemDetails);
        PrintWriter writer = response.getWriter();
        writer.print(body);
        writer.flush();
    }
}

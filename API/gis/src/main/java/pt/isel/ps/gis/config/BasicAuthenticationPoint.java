package pt.isel.ps.gis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component
public class BasicAuthenticationPoint extends BasicAuthenticationEntryPoint {

    private final static String REALM_NAME = "GIS API";

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
        ProblemDetails problemDetails = new ProblemDetails(
                "Requires authentication.",
                httpStatus.value(),
                "You need to authenticate first.");
        String body = new ObjectMapper().writeValueAsString(problemDetails);
        PrintWriter writer = response.getWriter();
        writer.println(body);
        writer.flush();
    }
}

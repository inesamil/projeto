package pt.isel.ps.gis.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.hypermedia.problemDetails.ProblemDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class AuthorizeFilter implements Filter {

    private final AuthenticationFacade authenticationFacade;
    private final MessageSource messageSource;

    public AuthorizeFilter(AuthenticationFacade authenticationFacade, MessageSource messageSource) {
        this.authenticationFacade = authenticationFacade;
        this.messageSource = messageSource;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        String uri = hreq.getRequestURI();
        if (uri.contains("/users")) {
            String username = uri.split("/users")[1].split("/")[1];
            String authenticatedUsername = authenticationFacade.getAuthentication().getName();
            if (!authenticatedUsername.equals(username)) {
                sendForbiddenError(hres);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void sendForbiddenError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Locale locale = LocaleContextHolder.getLocale();
        ProblemDetails problemDetails = new ProblemDetails(
                "You don't have permissions to access this resource.", // TODO meter nas strings
                httpStatus.value(),
                messageSource.getMessage("need_Authenticate_First", null, locale), // TODO meter nas strings e mudar a mensagem
                messageSource.getMessage("need_Authenticate_First", null, locale)); // TODO igual ao de cima
        String body = new ObjectMapper().writeValueAsString(problemDetails);
        PrintWriter writer = response.getWriter();
        writer.print(body);
        writer.flush();
    }
}

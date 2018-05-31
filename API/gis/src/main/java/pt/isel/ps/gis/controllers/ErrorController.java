package pt.isel.ps.gis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.hypermedia.problemPlusJson.ProblemPlusJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static pt.isel.ps.gis.utils.HeadersUtils.setProblemDetailContentType;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<ProblemPlusJson> error(HttpServletRequest request) {
        String servletName = (String) request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String queryString = (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (queryString == null)
            log.warn("Error in {} @ {}", servletName, requestUri);
        else
            log.warn("Error in {} @ {}?{}", servletName, requestUri, queryString);
        HttpStatus httpStatus;
        if (statusCode != null)
            httpStatus = HttpStatus.valueOf(statusCode);
        else
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                "Your request couldn't be processed."
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(httpHeaders), httpStatus);
    }
}
package pt.isel.ps.gis.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.isel.ps.gis.hypermedia.problemDetails.ProblemDetails;

import java.util.Set;

import static pt.isel.ps.gis.utils.HeadersUtils.setProblemDetailContentType;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler({
            BadRequestException.class,
            NotFoundException.class,
            ConflictException.class,
            ForbiddenException.class
    })
    public final ResponseEntity<ProblemDetails> handleProblemDetailsException(ProblemDetailsException ex) {
        log.warn(ex.getTitle());
        log.warn(ex.getDetail());
        HttpStatus httpStatus = ex.getStatus();
        ProblemDetails problemDetails = new ProblemDetails(
                ex.getType(),
                ex.getTitle(),
                httpStatus.value(),
                ex.getDetail(),
                ex.getInstance()
        );
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), httpStatus);
    }

    // This exception is thrown when you send a requested with an unsupported HTTP method.
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String error = "The method " + ex.getMethod() + " is not supported for this request. Please check the documentation.";
        ProblemDetails problemDetails = new ProblemDetails(
                "Method not supported.",
                status.value(),
                error
        );
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods))
            headers.setAllow(supportedMethods);
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String error = "The " + ex.getContentType() + " media type is not supported.";
        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        ProblemDetails problemDetails = new ProblemDetails(
                "Media type not supported.",
                httpStatus.value(),
                error
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(responseHeaders), httpStatus);
    }

    // This exception is thrown when request missing parameter.
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetails problemDetails = new ProblemDetails(
                "Request missing parameter.",
                httpStatus.value(),
                ex.getMessage()
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(responseHeaders), httpStatus);
    }

    // This exception is thrown when method argument is not the expected type.
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String error = ex.getValue() + " should be of type " + ex.getRequiredType().getName();
        ProblemDetails problemDetails = new ProblemDetails(
                "Method argument is not the expected type.",
                status.value(),
                error
        );
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), status);
    }

    // This exception is thrown when no handler found for the request.
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ProblemDetails problemDetails = new ProblemDetails(
                "No handler found.",
                status.value(),
                error
        );
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        ProblemDetails problemDetails = new ProblemDetails(
                "Something went wrong.",
                status.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), status);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ProblemDetails> handleException(Throwable ex) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetails problemDetails = new ProblemDetails(
                "Server error.",
                httpStatus.value(),
                "The server can not process your request."
        );
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemDetails, setProblemDetailContentType(headers), httpStatus);
    }
}

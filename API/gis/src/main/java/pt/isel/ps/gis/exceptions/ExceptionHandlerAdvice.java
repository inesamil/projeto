package pt.isel.ps.gis.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.isel.ps.gis.hypermedia.problemPlusJson.ProblemPlusJson;

import static pt.isel.ps.gis.utils.HeadersUtils.setProblemDetailContentType;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler({
            BadRequestException.class,
            NotFoundException.class
    })
    public final ResponseEntity<ProblemPlusJson> handleForbidden(ProblemDetailsException ex) {
        log.warn(ex.getTitle());
        log.warn(ex.getDetail());
        HttpStatus httpStatus = ex.getStatus();
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                ex.getType(),
                ex.getTitle(),
                httpStatus.value(),
                ex.getDetail(),
                ex.getInstance()
        );
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(headers), httpStatus);
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
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                "Request missing parameter.",
                httpStatus.value(),
                ex.getMessage()
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(responseHeaders), httpStatus);
    }

    // This exception is thrown when method argument is not the expected type.
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                "Method argument is not the expected type.",
                status.value(),
                error
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(responseHeaders), status);
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
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                "No handler found.",
                httpStatus.value(),
                error
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(responseHeaders), httpStatus);
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
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                "Method not supported.",
                httpStatus.value(),
                error
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(responseHeaders), httpStatus);
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
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(
                "Media type not supported.",
                httpStatus.value(),
                error
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(responseHeaders), httpStatus);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ProblemPlusJson> handleException(Throwable ex) {
        log.warn(ex.getMessage());
        log.warn(ex.getLocalizedMessage());
        String title = "Server error.";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        int statusCode = httpStatus.value();
        String detail = "The server can not process your request.";
        ProblemPlusJson problemPlusJson = new ProblemPlusJson(title, statusCode, detail);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemPlusJson, setProblemDetailContentType(headers), httpStatus);
    }
}

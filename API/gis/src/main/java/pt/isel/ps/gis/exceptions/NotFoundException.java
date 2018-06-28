package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ProblemDetailsException {

    private static final String TITLE = "Resource not found.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "This resource can not be found.";

    public NotFoundException() {
        super(TITLE, HTTP_STATUS, MESSAGE, MESSAGE);
    }

    public NotFoundException(String message, String userFriendlyMessage) {
        super(TITLE, HTTP_STATUS, message, userFriendlyMessage);
    }
}

package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ProblemDetailsException {

    public BadRequestException(String detail, String userFriendlyMessage) {
        super("Bad request.", HttpStatus.BAD_REQUEST, detail, userFriendlyMessage);
    }
}

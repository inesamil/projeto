package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ProblemDetailsException {

    public BadRequestException(String detail) {
        super("Bad request.", HttpStatus.BAD_REQUEST, detail);
    }
}

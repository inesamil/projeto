package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ProblemDetailsException {

    public ForbiddenException(String detail) {
        super("Forbidden.", HttpStatus.FORBIDDEN, detail);
    }
}

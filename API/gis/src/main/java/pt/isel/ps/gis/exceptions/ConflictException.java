package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends ProblemDetailsException {

    public ConflictException(String detail) {
        super("Conflict.", HttpStatus.CONFLICT, detail);
    }
}

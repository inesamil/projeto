package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends ProblemDetailsException {

    public ConflictException(String detail, String userFriendlyMessage) {
        super("Conflict.", HttpStatus.CONFLICT, detail, userFriendlyMessage);
    }
}

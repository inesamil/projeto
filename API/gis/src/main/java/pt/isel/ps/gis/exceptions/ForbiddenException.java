package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ProblemDetailsException {

    public ForbiddenException(String detail, String userFriendlyMessage) {
        super("Forbidden.", HttpStatus.FORBIDDEN, detail, userFriendlyMessage);
    }
}

package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ProblemDetailsException {

    public NotFoundException() {
        super("Resource not found.", HttpStatus.NOT_FOUND, "This resource can not be found.");
    }
}

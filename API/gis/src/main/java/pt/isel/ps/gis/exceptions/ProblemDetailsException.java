package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ProblemDetailsException extends Exception {

    private String type;
    private String title;
    private HttpStatus status;
    private String detail;
    private String instance;

    public ProblemDetailsException(String type, String title, HttpStatus status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public ProblemDetailsException(String title, HttpStatus status, String detail, String instance) {
        this("about:blank", title, status, detail, instance);
    }

    public ProblemDetailsException(String title, HttpStatus status, String detail) {
        this(title, status, detail, null);
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }
}

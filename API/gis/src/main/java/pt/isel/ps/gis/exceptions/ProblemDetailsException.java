package pt.isel.ps.gis.exceptions;

import org.springframework.http.HttpStatus;

public class ProblemDetailsException extends Exception {

    private String type;
    private String title;
    private HttpStatus status;
    private String detail;
    private String instance;
    private String userFriendlyMessage;

    public ProblemDetailsException(String type, String title, HttpStatus status, String detail, String instance, String userFriendlyMessage) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public ProblemDetailsException(String title, HttpStatus status, String detail, String instance, String userFriendlyMessage) {
        this("about:blank", title, status, detail, instance, userFriendlyMessage);
    }

    public ProblemDetailsException(String title, HttpStatus status, String detail, String userFriendlyMessage) {
        this(title, status, detail, null, userFriendlyMessage);
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

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }
}

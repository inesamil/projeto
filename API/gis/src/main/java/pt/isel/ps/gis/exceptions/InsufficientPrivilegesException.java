package pt.isel.ps.gis.exceptions;

public class InsufficientPrivilegesException extends Exception {

    private String userFriendlyMessage;

    public InsufficientPrivilegesException(String message, String userFriendlyMessage) {
        super(message);
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }
}

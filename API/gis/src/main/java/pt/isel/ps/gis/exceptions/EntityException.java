package pt.isel.ps.gis.exceptions;

public class EntityException extends Exception {

    private String userFriendlyMessage;

    public EntityException(String message, String userFriendlyMessage) {
        super(message);
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }
}

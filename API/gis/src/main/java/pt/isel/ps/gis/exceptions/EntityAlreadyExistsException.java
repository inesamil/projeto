package pt.isel.ps.gis.exceptions;

public class EntityAlreadyExistsException extends Exception {

    private String userFriendlyMessage;

    public EntityAlreadyExistsException(String message, String userFriendlyMessage) {
        super(message);
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }
}

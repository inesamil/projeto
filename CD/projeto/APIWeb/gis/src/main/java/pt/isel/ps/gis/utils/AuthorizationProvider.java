package pt.isel.ps.gis.utils;

import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;

public interface AuthorizationProvider {

    void checkUserAuthorizationToAccessHouse(String username, long houseId) throws InsufficientPrivilegesException;

    void checkUserAuthorizationToAdminHouse(String username, long houseId) throws InsufficientPrivilegesException;

    void checkUserAuthorizationToAccessResource(String authenticatedUsername, String usernameInPath) throws InsufficientPrivilegesException;
}

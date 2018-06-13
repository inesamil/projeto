package pt.isel.ps.gis.config;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticationFacade {

    Authentication getAuthentication();

    boolean isAuthenticated();

    Optional<String> getCurrentUsernameIfAuthenticated();
}

package pt.isel.ps.gis.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;

import java.util.Locale;

public class AuthorizationProviderImpl implements AuthorizationProvider {

    private final UserHouseRepository userHouseRepository;
    private final MessageSource messageSource;

    public AuthorizationProviderImpl(UserHouseRepository userHouseRepository, MessageSource messageSource) {
        this.userHouseRepository = userHouseRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void checkUserAuthorizationToAccessHouse(String username, long houseId) throws InsufficientPrivilegesException {
        Locale locale = LocaleContextHolder.getLocale();
        if (!userHouseRepository.existsById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username))
            throw new InsufficientPrivilegesException("No authorization.", messageSource.getMessage("no_authorization", null, locale));
    }
}

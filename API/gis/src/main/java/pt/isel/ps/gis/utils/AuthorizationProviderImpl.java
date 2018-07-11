package pt.isel.ps.gis.utils;

import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;

import java.util.Locale;

public class AuthorizationProviderImpl implements AuthorizationProvider {

    private final UserHouseRepository userHouseRepository;

    private final Locale locale;

    public AuthorizationProviderImpl(UserHouseRepository userHouseRepository, Locale locale) {
        this.userHouseRepository = userHouseRepository;
        this.locale = locale;
    }

    @Override
    public void checkUserAuthorizationToAccessHouse(String username, long houseId) throws InsufficientPrivilegesException {
        if (!userHouseRepository.existsById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username))
            throw new InsufficientPrivilegesException("", "");  //TODO: mensagem
    }
}

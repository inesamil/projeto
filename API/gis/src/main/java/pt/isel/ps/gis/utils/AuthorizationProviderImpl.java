package pt.isel.ps.gis.utils;

import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;

public class AuthorizationProviderImpl implements AuthorizationProvider {

    private final UserHouseRepository userHouseRepository;

    public AuthorizationProviderImpl(UserHouseRepository userHouseRepository) {
        this.userHouseRepository = userHouseRepository;
    }

    @Override
    public void checkUserAuthorizationToAccessHouse(String username, long houseId) throws InsufficientPrivilegesException {
        if (!userHouseRepository.existsById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username))
            throw new InsufficientPrivilegesException("", "");  //TODO: mensagem
    }
}

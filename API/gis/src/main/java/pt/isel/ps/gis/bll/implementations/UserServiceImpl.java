package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean existsUserByUserId(String username) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return usersRepository.existsById(username);
    }

    @Override
    public Optional<Users> getUserByUserId(String username) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return usersRepository.findById(username);
    }

    @Override
    public Users addUser(Users user) throws EntityException {
        if (existsUserByUserId(user.getUsersUsername())) {
            throw new EntityException(String.format("Username %s is already in use.", user.getUsersUsername()));
        }
        return usersRepository.save(user);
    }

    @Override
    public Users updateUser(Users user) throws EntityNotFoundException, EntityException {
        String username = user.getUsersUsername();
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(String.format("User with username %s does not exist.", username));
        return usersRepository.save(user);
    }

    @Override
    public void deleteUserByUserId(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(String.format("User with username %s does not exist.", username));
        // Remover o utilizador bem como todas as relações das quais o utilizador seja parte integrante
        usersRepository.deleteCascadeUserById(username);
    }
}

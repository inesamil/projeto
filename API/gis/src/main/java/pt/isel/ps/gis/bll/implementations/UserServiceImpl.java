package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean existsUserByUserId(String username) {
        return usersRepository.existsById(username);
    }

    @Override
    public Optional<Users> getUserByUserId(String username) {
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
    public Users updateUser(Users user) throws EntityNotFoundException {
        if (!existsUserByUserId(user.getUsersUsername()))
            throw new EntityNotFoundException(String.format("User with username %s does not exist.", user.getUsersUsername()));
        return usersRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserByUserId(String username) throws EntityNotFoundException {
        if (!existsUserByUserId(username))
            throw new EntityNotFoundException(String.format("User with username %s does not exist.", username));
        //TODO: deleteUserCascade
        usersRepository.deleteById(username);   // Remover o utilizador
    }
}

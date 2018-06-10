package pt.isel.ps.gis.bll.implementations;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsUserByUserId(String username) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return usersRepository.existsById(username);
    }

    @Override
    public Users getUserByUserId(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        return usersRepository
                .findById(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with username %s does not exist.", username)));
    }

    @Override
    public Users addUser(String username, String email, Short age, String name, String password) throws EntityException {
        if (existsUserByUserId(username))
            throw new EntityException(String.format("Username %s is already in use.", username));
        return usersRepository.save(new Users(username, email, age, name, passwordEncoder.encode(password)));
    }

    @Transactional
    @Override
    public Users updateUser(String username, String email, Short age, String name, String password) throws EntityException, EntityNotFoundException {
        Users user = getUserByUserId(username);
        user.setUsersEmail(email);
        user.setUsersAge(age);
        user.setUsersName(name);
        user.setUsersPassword(passwordEncoder.encode(password));
        return user;
    }

    @Override
    public void deleteUserByUserId(String username) throws EntityException, EntityNotFoundException {
        checkUserUsername(username);
        // Remover o utilizador bem como todas as relações das quais o utilizador seja parte integrante
        usersRepository.deleteCascadeUserById(username);
    }

    private void checkUserUsername(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(String.format("User with username %s does not exist.", username));

    }
}

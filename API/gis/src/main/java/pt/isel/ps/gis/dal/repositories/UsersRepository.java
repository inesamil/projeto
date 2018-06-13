package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Users;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Long>, UsersRepositoryCustom {

    /**
     * Verify if exists user with param username
     *
     * @param username The username of the user
     * @return true if already exists a user with username, otherwise false
     */
    boolean existsByUsersUsername(String username);

    /**
     * Verify if exists user with param email
     *
     * @param email The email of the user
     * @return true if already exists a user with email, otherwise false
     */
    boolean existsByUsersEmail(String email);

    /**
     * Find users by username
     *
     * @param username The username of the user
     * @return Optional with user if find a user with username, otherwise returns Option.Empty
     */
    Optional<Users> findByUsersUsername(String username);
}

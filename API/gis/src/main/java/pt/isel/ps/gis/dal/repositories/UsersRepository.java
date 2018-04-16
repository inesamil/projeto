package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Users;

public interface UsersRepository extends CrudRepository<Users, String> {

    //TODO: deleteUserByUsersUsername(String username) // Apaga o utilizador com username = @username e todo o conteúdo associado ao utilizador
}

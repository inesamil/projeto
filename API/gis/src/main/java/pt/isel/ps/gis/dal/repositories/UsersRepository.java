package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Users;

public interface UsersRepository extends CrudRepository<Users, String>, UsersRepositoryCustom {
}

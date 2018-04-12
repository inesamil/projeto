package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.Users;

public interface UsersRepository extends CrudRepository<Users, String> {
}

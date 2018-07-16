package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserRole;
import pt.isel.ps.gis.model.UserRoleId;

public interface UserRoleRepository extends CrudRepository<UserRole, UserRoleId> {
}

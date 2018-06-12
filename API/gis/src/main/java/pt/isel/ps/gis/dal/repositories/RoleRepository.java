package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Short> {

    Optional<Role> findByRoleName(String roleName);
}

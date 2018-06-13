package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Short> {

    /**
     * Find role by its name
     *
     * @param roleName The role name
     * @return Optional with Role if role name exists, otherwise Optional.Empty
     */
    Optional<Role> findByRoleName(String roleName);
}

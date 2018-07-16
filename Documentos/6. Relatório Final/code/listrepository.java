import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<List, ListId> {
    /**
     * Finds all lists by house ID
     *
     * @param houseId The id of the house
     * @return java.util.List of list of the house with ID {houseId}
     */
    java.util.List<List> findAllById_HouseId(long houseId);
}
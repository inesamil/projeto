package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;

import java.util.List;
import java.util.Optional;

public interface HouseMemberService {

    /**
     * Verifica se um dado membro existe na casa através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param username identificador do utilizador
     * @return true se o membro existir na casa, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsMemberByMemberId(long houseId, String username) throws EntityException;

    /**
     * Obter um membro da casa através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param username identificador do utilizador
     * @return Optional<UserHouse>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Optional<UserHouse> getMemberByMemberId(long houseId, String username) throws EntityException;

    /**
     * Listar os membros de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<UserHouse>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<UserHouse> getMembersByHouseId(long houseId) throws EntityException;

    /**
     * Adicionar um membro à casa
     *
     * @param member membro a adicionar
     * @return UserHouse
     */
    UserHouse addMember(UserHouse member);

    /**
     * Atualizar os atributos de um membro
     *
     * @param member membro a atualizar
     * @return UserHouse
     * @throws EntityNotFoundException se o membro especificado não existir na casa particularizada
     */
    UserHouse updateMember(UserHouse member) throws EntityNotFoundException;

    /**
     * Remover um membro da casa
     *
     * @param houseId identificador da casa
     * @param username identificador do utilizador
     * @throws EntityException se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o membro especificado não existir na casa particularizada
     */
    void deleteMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException;
}

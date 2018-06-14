package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;

import java.util.List;

public interface HouseMemberService {

    /**
     * Verifica se um dado membro existe na casa através dos seus IDs
     *
     * @param houseId  identificador da casa
     * @param username identificador do utilizador
     * @return true se o membro existir na casa, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsMemberByMemberId(long houseId, String username) throws EntityException;

    /**
     * Obter um membro da casa através dos seus IDs
     *
     * @param houseId  identificador da casa
     * @param username identificador do utilizador
     * @return Optional<UserHouse>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    UserHouse getMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException;

    /**
     * Listar os membros de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<UserHouse>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<UserHouse> getMembersByHouseId(long houseId) throws EntityException, EntityNotFoundException;

    /**
     * Associar ou atualizar um membro de uma casa
     *
     * @param houseId       identificador da casa
     * @param username      identificador do membro
     * @param administrator se for administrador da casa passar true, senão false
     * @return UserHouse
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o membro especificado não existir ou já estiver presente na casa particularizada
     */
    UserHouse associateMember(long houseId, String username, Boolean administrator) throws EntityException, EntityNotFoundException;

    /**
     * Remover um membro da casa
     *
     * @param houseId  identificador da casa
     * @param username identificador do utilizador
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o membro especificado não existir na casa particularizada
     */
    void deleteMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException;
}

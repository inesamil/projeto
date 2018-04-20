package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;

import java.util.List;

public interface HouseMemberService {

    /**
     * Verifica se um dado membro existe na casa através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param username identificador do utilizador
     * @return true se o membro existir na casa, false caso contrário
     */
    boolean existsMemberByMemberId(long houseId, String username) throws EntityException;

    /**
     * Listar os membros de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Member>
     */
    List<UserHouse> getMembersByHouseId(long houseId);

    /**
     * Adicionar um membro à casa
     *
     * @param member membro a adicionar
     * @return Member
     */
    UserHouse addMember(UserHouse member);

    /**
     * Atualizar os atributos de um membro
     *
     * @param member membro a atualizar
     * @return Member
     */
    UserHouse updateMember(UserHouse member) throws EntityException, EntityNotFoundException;

    /**
     * Remover um membro da casa
     *
     * @param houseId identificador da casa
     * @param username identificador do utilizador
     */
    void deleteMember(long houseId, String username) throws EntityException, EntityNotFoundException;
}

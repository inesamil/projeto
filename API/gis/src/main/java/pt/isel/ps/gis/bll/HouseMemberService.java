package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;

public interface HouseMemberService {

    /**
     * Listar os membros de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Member>
     */
    Iterable<UserHouse> getMembersByHouseId(Long houseId);

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
    UserHouse updateMember(UserHouse member);

    /**
     * Remover um membro da casa
     *
     * @param memberId identificador do membro a remover
     */
    void deleteMember(UserHouseId memberId);
}

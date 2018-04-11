package pt.isel.ps.gis.bll;

import java.util.List;

public interface HouseMemberService {

    /**
     * Listar os membros de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Member>
     */
    List<Member> getMembersByHouseId(Long houseId);

    /**
     * Adicionar um membro à casa
     *
     * @param member membro a adicionar
     * @return Member
     */
    Member addMember(Member member);

    /**
     * Atualizar os atributos de um membro
     *
     * @param member membro a atualizar
     * @return Member
     */
    Member updateMember(Member member);

    /**
     * Remover um membro da casa
     *
     * @param member membro a remover
     */
    void deleteMember(Member member);
}

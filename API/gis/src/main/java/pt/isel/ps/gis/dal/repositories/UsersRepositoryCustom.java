package pt.isel.ps.gis.dal.repositories;

public interface UsersRepositoryCustom {

    void deleteUserById(String username);   //TODO: rename para deleteCascadeById ou algo que eviencie que se estão apagar todas as coisas associadas ao User
}

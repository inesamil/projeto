package pt.isel.ps.gis.dal.repositories;

public interface HouseRepositoryCustom {

    void deleteHouseById(Long houseId); //TODO: rename para deleteCascadeById ou algo que eviencie que se estão apagar todas as coisas associadas à Casa
}

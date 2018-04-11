package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.House;

import java.util.List;

public interface HouseService {

    /**
     * Obter uma casa através do seu ID
     *
     * @param houseId identificador da casa
     * @return House
     */
    House getHouseByHouseId(Long houseId);

    /**
     * Listar as casas de um utilizador através do seu nome do utilizador
     *
     * @param username identificador do utilizador
     * @return List<House>
     */
    List<House> getHousesByUserId(String username);

    /**
     * Adicionar uma casa
     *
     * @param house casa a adicionar
     * @return House
     */
    House addHouse(House house);

    /**
     * Atualizar uma casa
     *
     * @param house casa atualizada
     * @return House
     */
    House updateHouse(House house);

    /**
     * Remover uma casa
     *
     * @param houseId identificador da casa
     */
    void deleteHouse(Long houseId);
}

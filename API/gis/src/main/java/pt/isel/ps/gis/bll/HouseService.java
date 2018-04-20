package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.House;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    /**
     * Verifica se uma dada casa existe através do seu ID
     *
     * @param houseId identificador da casa
     * @return true se a casa existir, false caso contrário
     */
    boolean existsHouseByHouseId(long houseId);

    /**
     * Obter uma casa através do seu ID
     *
     * @param houseId identificador da casa
     * @return Optional<House>
     */
    Optional<House> getHouseByHouseId(long houseId);

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
     * @throws EntityException se os atributos especificados no parâmetro house forem inválidos
     */
    House addHouse(House house) throws EntityException;

    /**
     * Atualizar uma casa
     *
     * @param house casa atualizada
     * @return House
     * @throws EntityNotFoundException se a casa especificada não existir
     */
    House updateHouse(House house) throws EntityNotFoundException;

    /**
     * Remover uma casa
     *
     * @param houseId identificador da casa a remover
     * @throws EntityNotFoundException  se a casa especificada não existir
     */
    void deleteHouseByHouseId(long houseId) throws EntityNotFoundException;
}
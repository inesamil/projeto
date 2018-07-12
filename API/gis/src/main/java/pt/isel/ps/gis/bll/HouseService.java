package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.House;

import java.util.List;
import java.util.Locale;

public interface HouseService {

    /**
     * Verifica se uma dada casa existe através do seu ID
     *
     * @param houseId identificador da casa
     * @return true se a casa existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsHouseByHouseId(long houseId) throws EntityException;

    /**
     * Obter uma casa através do seu ID
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return Optional<House>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    House getHouseByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Listar as casas de um utilizador através do seu nome do utilizador
     *
     * @param username identificador do utilizador
     * @return List<House>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<House> getHousesByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar uma casa
     *
     * @param username       nome do utilizador
     * @param name           nome da casa
     * @param babiesNumber   número de bebes na casa
     * @param childrenNumber número de crianças na casa
     * @param adultsNumber   número de adultos na casa
     * @param seniorsNumber  número de séniores na casa
     * @return House
     * @throws EntityException se os atributos especificados no parâmetro house forem inválidos
     */
    House addHouse(String username, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Atualizar uma casa
     *
     *
     * @param username
     * @param houseId        identificador da casa
     * @param name           nome da casa
     * @param babiesNumber   número de bebes na casa
     * @param childrenNumber número de crianças na casa
     * @param adultsNumber   número de adultos na casa
     * @param seniorsNumber  número de séniores na casa
     * @return House
     * @throws EntityNotFoundException se a casa especificada não existir
     * @throws EntityException         se os atributos especificados no parâmetro house forem inválidos
     */
    House updateHouse(String username, long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber, Locale locale) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException;

    /**
     * Remover uma casa
     *
     *
     * @param username
     * @param houseId identificador da casa a remover
     * @throws EntityNotFoundException se a casa especificada não existir
     */
    void deleteHouseByHouseId(String username, long houseId, Locale locale) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException;
}
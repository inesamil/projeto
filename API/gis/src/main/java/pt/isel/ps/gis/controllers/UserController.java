package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.model.inputModel.ListInputModel;
import pt.isel.ps.gis.model.inputModel.UserInputModel;
import pt.isel.ps.gis.model.outputModel.*;
import pt.isel.ps.gis.model.requestParams.ListRequestParam;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/users/{username}")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;
    private final ListService listService;

    public UserController(UserService userService, HouseService houseService, ListService listService) {
        this.userService = userService;
        this.houseService = houseService;
        this.listService = listService;
    }

    @GetMapping("")
    public ResponseEntity<UserOutputModel> getUser(
            @PathVariable("username") String username
    ) throws NotFoundException, BadRequestException {
        Users user;
        try {
            user = userService.getUserByUserId(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(
            @PathVariable("username") String username
    ) throws BadRequestException, NotFoundException {
        List<House> userHouses;
        try {
            userHouses = houseService.getHousesByUserId(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/lists")
    public ResponseEntity<UserListsOutputModel> getUserLists(
            @PathVariable("username") String username,
            ListRequestParam params
    ) throws BadRequestException, NotFoundException {
        List<pt.isel.ps.gis.model.List> lists;
        try {
            ListService.AvailableListFilters filters;
            if (params.isNull()) {
                Long[] housesIds = houseService.getHousesByUserId(username)
                        .stream()
                        .map(House::getHouseId)
                        .toArray(Long[]::new);
                filters = new ListService.AvailableListFilters(housesIds);
            } else
                filters = new ListService.AvailableListFilters(
                        params.getHousesIds(),
                        params.getSystem(),
                        params.getUser(),
                        params.getShareable()
                );
            lists = listService.getAvailableListsByUserUsername(username, filters);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(httpHeaders),
                HttpStatus.OK);
    }


    @PostMapping("/lists")
    public ResponseEntity<ListOutputModel> postUserList(
            @PathVariable("username") String username,
            @RequestBody ListInputModel body
    ) throws BadRequestException, NotFoundException {
        pt.isel.ps.gis.model.List list;
        try {
            list = listService.addUserList(
                    body.getHouseId(),
                    body.getName(),
                    username,
                    body.getShareable()
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(list), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<UserOutputModel> putUser(
            @PathVariable("username") String username,
            @RequestBody UserInputModel body
    ) throws BadRequestException, NotFoundException {
        Users user;
        HttpStatus status;
        try {
            //TODO: como sei se é insert ou update?
            if (userService.existsUserByUserId(username)) {
                user = userService.updateUser(username, body.getEmail(), body.getAge(), body.getName(), body.getPassword());
                status = HttpStatus.OK;
            } else {
                user = userService.addUser(username, body.getEmail(), body.getAge(), body.getName(), body.getPassword());
                status = HttpStatus.CREATED;
            }
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), status);
    }

    @DeleteMapping("")
    public ResponseEntity<IndexOutputModel> deleteUser(
            @PathVariable("username") String username
    ) throws BadRequestException, NotFoundException {
        try {
            userService.deleteUserByUserId(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }
}

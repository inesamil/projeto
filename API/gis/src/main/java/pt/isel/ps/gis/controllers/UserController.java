package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.model.inputModel.ListInputModel;
import pt.isel.ps.gis.model.inputModel.RegisterUserInputModel;
import pt.isel.ps.gis.model.inputModel.UserInputModel;
import pt.isel.ps.gis.model.outputModel.*;
import pt.isel.ps.gis.model.requestParams.ListRequestParam;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;
    private final ListService listService;

    public UserController(UserService userService, HouseService houseService, ListService listService) {
        this.userService = userService;
        this.houseService = houseService;
        this.listService = listService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputModel> getUser(
            @PathVariable("username") String username,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        Users user;
        try {
            user = userService.getUserByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{username}/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(
            @PathVariable("username") String username,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<House> userHouses;
        try {
            userHouses = houseService.getHousesByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{username}/lists")
    public ResponseEntity<UserListsOutputModel> getUserLists(
            @PathVariable("username") String username,
            ListRequestParam params,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<pt.isel.ps.gis.model.List> lists;
        try {
            ListService.AvailableListFilters filters;
            if (params.isNull()) {
                Long[] housesIds = houseService.getHousesByUserUsername(username, locale)
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
            lists = listService.getAvailableListsByUserUsername(username, filters, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(httpHeaders),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserOutputModel> registerUser(
            @RequestBody RegisterUserInputModel body,
            Locale locale
    ) throws BadRequestException, ConflictException {
        Users user;
        try {
            user = userService.addUser(body.getUsername(), body.getEmail(), body.getAge(), body.getName(), body.getPassword(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PostMapping("/{username}/lists")
    public ResponseEntity<UserListsOutputModel> postUserList(
            @PathVariable("username") String username,
            @RequestBody ListInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        pt.isel.ps.gis.model.List list;
        List<pt.isel.ps.gis.model.List> lists;
        try {
            list = listService.addUserList(
                    body.getHouseId(),
                    body.getName(),
                    username,
                    body.getShareable(),
                    locale
            );
            Long[] housesIds = houseService.getHousesByUserUsername(username, locale)
                    .stream()
                    .map(House::getHouseId)
                    .toArray(Long[]::new);
            lists = listService.getAvailableListsByUserUsername(username, new ListService.AvailableListFilters(housesIds), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserOutputModel> putUser(
            @PathVariable("username") String username,
            @RequestBody UserInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ConflictException {
        Users user;
        try {
            user = userService.updateUser(
                    username,
                    body.getEmail(),
                    body.getAge(),
                    body.getName(),
                    body.getPassword(),
                    locale
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<IndexOutputModel> deleteUser(
            @PathVariable("username") String username,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        try {
            userService.deleteUserByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }
}

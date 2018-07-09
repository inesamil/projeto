package pt.isel.ps.gis.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.model.inputModel.ListInputModel;
import pt.isel.ps.gis.model.inputModel.RegisterUserInputModel;
import pt.isel.ps.gis.model.inputModel.UserInputModel;
import pt.isel.ps.gis.model.outputModel.*;
import pt.isel.ps.gis.model.requestParams.ListRequestParam;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.net.URI;
import java.net.URISyntaxException;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("")
    public ResponseEntity<UsersOutputModel> getStartsWithUsername(
            @RequestParam String search
    ) throws BadRequestException {
        List<Users> users;
        try {
            users = userService.getUsersStartsWithUsername(search);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UsersOutputModel(users), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserOutputModel> getUser(
            @PathVariable("username") String username,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        Users user;
        try {
            user = userService.getUserByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{username}/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(
            @PathVariable("username") String username,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<House> userHouses;
        try {
            userHouses = houseService.getHousesByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(httpHeaders),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity<UserOutputModel> registerUser(
            @RequestBody RegisterUserInputModel body,
            Locale locale
    ) throws BadRequestException, ConflictException, URISyntaxException {
        Users user;
        try {
            user = userService.addUser(body.getUsername(), body.getEmail(), body.getAge(), body.getName(), body.getPassword(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(UriBuilderUtils.buildUserUri(user.getUsersUsername())));
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/{username}/lists")
    public ResponseEntity<ListOutputModel> postUserList(
            @PathVariable("username") String username,
            @RequestBody ListInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, URISyntaxException {
        pt.isel.ps.gis.model.List list;
        try {
            list = listService.addUserList(
                    body.getHouseId(),
                    body.getName(),
                    username,
                    body.getShareable(),
                    locale
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        ListId listId = list.getId();
        headers.setLocation(new URI(UriBuilderUtils.buildListUri(listId.getHouseId(), listId.getListId())));
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<IndexOutputModel> deleteUser(
            @PathVariable("username") String username,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        try {
            userService.deleteUserByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }
}

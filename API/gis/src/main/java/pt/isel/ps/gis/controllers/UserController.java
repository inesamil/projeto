package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.model.inputModel.UserInputModel;
import pt.isel.ps.gis.model.outputModel.IndexOutputModel;
import pt.isel.ps.gis.model.outputModel.UserHousesOutputModel;
import pt.isel.ps.gis.model.outputModel.UserOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/users/{username}")
public class UserController {

    private static final String USER_NOT_EXIST = "The user does not exist.";

    private final UserService userService;
    private final HouseService houseService;

    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<UserOutputModel> getUser(
            @PathVariable("username") String username
    ) throws NotFoundException, BadRequestException {
        Optional<Users> userOptional;
        try {
            userOptional = userService.getUserByUserId(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        Users user = userOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(
            @PathVariable("username") String username
    ) throws BadRequestException {
        checkUser(username);
        List<House> userHouses;
        try {
            userHouses = houseService.getHousesByUserId(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<UserOutputModel> putUser(
            @PathVariable("username") String username,
            @RequestBody UserInputModel body
    ) throws BadRequestException, NotFoundException {
        Users user;
        HttpStatus status;
        try {
            user = new Users(body.getUsername(), body.getEmail(), body.getAge(), body.getName(), body.getPassword());
            if (userService.existsUserByUserId(username)) {
                userService.updateUser(user);
                status = HttpStatus.OK;
            } else {
                userService.addUser(user);
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
        checkUser(username);
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

    private void checkUser(String username) throws BadRequestException {
        try {
            if (!userService.existsUserByUserId(username))
                throw new BadRequestException(USER_NOT_EXIST);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}

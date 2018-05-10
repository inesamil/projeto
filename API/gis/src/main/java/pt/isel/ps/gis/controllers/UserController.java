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

import static pt.isel.ps.gis.utils.HeadersUtils.*;

@RestController
@RequestMapping("/v1/users/{username}")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;

    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<UserOutputModel> getUser(
            @PathVariable("username") String username
    ) throws EntityException, NotFoundException {
        Optional<Users> userOptional = userService.getUserByUserId(username);
        Users user = userOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(
            @PathVariable("username") String username
    ) throws EntityException, BadRequestException {
        checkUser(username);
        List<House> userHouses = houseService.getHousesByUserId(username);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<UserOutputModel> putUser(
            @PathVariable("username") String username,
            @RequestBody UserInputModel body
    ) throws EntityException, EntityNotFoundException {
        Users user = new Users(body.getUsername(), body.getEmail(), body.getAge(), body.getName(), body.getPassword());
        HttpStatus status;
        if (userService.existsUserByUserId(username)) {
            userService.updateUser(user);
            status = HttpStatus.OK;
        } else {
            userService.addUser(user);
            status = HttpStatus.CREATED;
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), status);
    }

    @DeleteMapping("")
    public ResponseEntity<IndexOutputModel> deleteUser(
            @PathVariable("username") String username
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkUser(username);
        userService.deleteUserByUserId(username);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }

    private void checkUser(String username) throws EntityException, BadRequestException {
        if (!userService.existsUserByUserId(username))
            throw new BadRequestException("The user does not exist.");
    }
}

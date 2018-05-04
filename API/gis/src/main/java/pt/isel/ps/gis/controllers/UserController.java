package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.model.outputModel.UserHousesOutputModel;
import pt.isel.ps.gis.model.outputModel.UserOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("v1/users/{username}")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;

    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<UserOutputModel> getUser(@PathVariable("username") String username) throws EntityException, NotFoundException {
        Optional<Users> userOptional = userService.getUserByUserId(username);
        Users user = userOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserOutputModel(user), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/houses")
    public ResponseEntity<UserHousesOutputModel> getUserHouses(@PathVariable("username") String username) throws EntityException {
        List<House> userHouses = houseService.getHousesByUserId(username);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, userHouses), setCollectionContentType(headers),
                HttpStatus.OK);
    }
}

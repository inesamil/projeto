package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.inputModel.HouseInputModel;
import pt.isel.ps.gis.model.inputModel.HouseholdInputModel;
import pt.isel.ps.gis.model.outputModel.HouseMembersOutputModel;
import pt.isel.ps.gis.model.outputModel.HouseOutputModel;
import pt.isel.ps.gis.model.outputModel.IndexOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses")
public class HouseController {

    private final HouseService houseService;
    private final HouseMemberService houseMemberService;

    public HouseController(HouseService houseService, HouseMemberService houseMemberService) {
        this.houseService = houseService;
        this.houseMemberService = houseMemberService;
    }

    @GetMapping("/{house-id}")
    public ResponseEntity<HouseOutputModel> getHouses(@PathVariable("house-id") long houseId)
            throws EntityException, NotFoundException {
        Optional<House> optionalHouse = houseService.getHouseByHouseId(houseId);
        House house = optionalHouse.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{house-id}/users")
    public ResponseEntity<HouseMembersOutputModel> getHousehold(
            @PathVariable("house-id") long houseId
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        List<UserHouse> household = houseMemberService.getMembersByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HouseOutputModel> postHouse(
            @RequestBody HouseInputModel body
    ) throws EntityException {
        Characteristics characteristics = new Characteristics(
                body.getBabiesNumber(),
                body.getChildrenNumber(),
                body.getAdultsNumber(),
                body.getSeniorsNumber()
        );
        House house = houseService.addHouse(new House(body.getName(), characteristics));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PutMapping("/{house-id}")
    public ResponseEntity<HouseOutputModel> putHouse(
            @PathVariable("house-id") long houseId,
            @RequestBody HouseInputModel body
    ) throws EntityException, BadRequestException, EntityNotFoundException {
        checkHouse(houseId);
        Characteristics characteristics = new Characteristics(
                body.getBabiesNumber(),
                body.getChildrenNumber(),
                body.getAdultsNumber(),
                body.getSeniorsNumber()
        );
        House house = houseService.updateHouse(new House(houseId, body.getName(), characteristics));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> putMember(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username,
            @RequestBody HouseholdInputModel body
    ) throws EntityException, EntityNotFoundException {
        UserHouse member = new UserHouse(houseId, username, body.getAdministrator());
        if (houseMemberService.existsMemberByMemberId(houseId, username))
            houseMemberService.updateMember(member);
        else
            houseMemberService.addMember(member);
        List<UserHouse> household = houseMemberService.getMembersByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}")
    public ResponseEntity<IndexOutputModel> deleteHouse(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        houseService.deleteHouseByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> deleteUser(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkMember(houseId, username);
        houseMemberService.deleteMemberByMemberId(houseId, username);
        List<UserHouse> household = houseMemberService.getMembersByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }

    private void checkMember(long houseId, String username) throws EntityException, BadRequestException {
        if (!houseMemberService.existsMemberByMemberId(houseId, username))
            throw new BadRequestException("Member does not exist.");
    }
}

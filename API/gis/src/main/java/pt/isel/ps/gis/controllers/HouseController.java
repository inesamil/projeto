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
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.inputModel.HouseInputModel;
import pt.isel.ps.gis.model.inputModel.HouseholdInputModel;
import pt.isel.ps.gis.model.outputModel.HouseMembersOutputModel;
import pt.isel.ps.gis.model.outputModel.HouseOutputModel;
import pt.isel.ps.gis.model.outputModel.IndexOutputModel;

import java.util.List;

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
            throws NotFoundException, BadRequestException {
        House house;
        try {
            house = houseService.getHouseByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{house-id}/users")
    public ResponseEntity<HouseMembersOutputModel> getHousehold(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            household = houseMemberService.getMembersByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HouseOutputModel> postHouse(
            @RequestBody HouseInputModel body
    ) throws BadRequestException {
        House house;
        try {
            house = houseService.addHouse(
                    "pedro", // TODO ir buscar o username autenticado
                    body.getName(),
                    body.getBabiesNumber(),
                    body.getChildrenNumber(),
                    body.getAdultsNumber(),
                    body.getSeniorsNumber()
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PutMapping("/{house-id}")
    public ResponseEntity<HouseOutputModel> putHouse(
            @PathVariable("house-id") long houseId,
            @RequestBody HouseInputModel body
    ) throws BadRequestException, NotFoundException {
        House house;
        try {
            house = houseService.updateHouse(
                    houseId,
                    body.getName(),
                    body.getBabiesNumber(),
                    body.getChildrenNumber(),
                    body.getAdultsNumber(),
                    body.getSeniorsNumber()
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> putMember(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username,
            @RequestBody HouseholdInputModel body
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            houseMemberService.associateMember(houseId, username, body.getAdministrator());
            household = houseMemberService.getMembersByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}")
    public ResponseEntity<IndexOutputModel> deleteHouse(
            @PathVariable("house-id") long houseId
    ) throws NotFoundException, BadRequestException {
        try {
            houseService.deleteHouseByHouseId(houseId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> deleteUser(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            houseMemberService.deleteMemberByMemberId(houseId, username);
            household = houseMemberService.getMembersByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }
}

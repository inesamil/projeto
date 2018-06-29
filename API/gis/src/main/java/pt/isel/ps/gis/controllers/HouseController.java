package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.components.AuthenticationFacade;
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
import pt.isel.ps.gis.model.outputModel.UserHousesOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses")
public class HouseController {

    private final HouseService houseService;
    private final HouseMemberService houseMemberService;
    private final AuthenticationFacade authenticationFacade;
    private final MessageSource messageSource;

    public HouseController(HouseService houseService, HouseMemberService houseMemberService, ListService listService, AuthenticationFacade authenticationFacade, MessageSource messageSource, MessageSource messageSource1) {
        this.houseService = houseService;
        this.houseMemberService = houseMemberService;
        this.authenticationFacade = authenticationFacade;
        this.messageSource = messageSource1;
    }

    @GetMapping("/{house-id}")
    public ResponseEntity<HouseOutputModel> getHouse(@PathVariable("house-id") long houseId, Locale locale)
            throws NotFoundException, BadRequestException {
        House house;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            house = houseService.getHouseByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(username, house), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{house-id}/users")
    public ResponseEntity<HouseMembersOutputModel> getHousehold(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            household = houseMemberService.getMembersByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserHousesOutputModel> postHouse(
            @RequestBody HouseInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        House house;
        List<House> houses;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            house = houseService.addHouse(
                    username,
                    body.getName(),
                    body.getBabiesNumber(),
                    body.getChildrenNumber(),
                    body.getAdultsNumber(),
                    body.getSeniorsNumber(),
                    locale
            );
            houses = houseService.getHousesByUserUsername(username, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserHousesOutputModel(username, houses), setSirenContentType(headers), HttpStatus.CREATED);
    }

    @PutMapping("/{house-id}")
    public ResponseEntity<HouseOutputModel> putHouse(
            @PathVariable("house-id") long houseId,
            @RequestBody HouseInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        House house;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            house = houseService.updateHouse(
                    houseId,
                    body.getName(),
                    body.getBabiesNumber(),
                    body.getChildrenNumber(),
                    body.getAdultsNumber(),
                    body.getSeniorsNumber(),
                    locale
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(username, house), setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> putMember(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username,
            @RequestBody HouseholdInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            houseMemberService.associateMember(houseId, username, body.getAdministrator(), locale);
            household = houseMemberService.getMembersByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}")
    public ResponseEntity<IndexOutputModel> deleteHouse(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        try {
            houseService.deleteHouseByHouseId(houseId, locale);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{house-id}/users/{username}")
    public ResponseEntity<HouseMembersOutputModel> deleteUser(
            @PathVariable("house-id") long houseId,
            @PathVariable("username") String username,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<UserHouse> household;
        try {
            houseMemberService.deleteMemberByMemberId(houseId, username, locale);
            household = houseMemberService.getMembersByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseMembersOutputModel(houseId, household), setSirenContentType(headers),
                HttpStatus.OK);
    }
}

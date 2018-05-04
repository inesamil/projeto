package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.outputModel.HouseOutputModel;
import pt.isel.ps.gis.model.outputModel.HouseholdOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("v1/houses")
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
    public ResponseEntity<HouseholdOutputModel> getHousehold(@PathVariable("house-id") long houseId) throws EntityException {
        List<UserHouse> household = houseMemberService.getMembersByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseholdOutputModel(houseId, household), setCollectionContentType(headers),
                HttpStatus.OK);
    }
}

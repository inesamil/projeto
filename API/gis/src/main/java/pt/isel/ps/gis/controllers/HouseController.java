package pt.isel.ps.gis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.outputModel.HouseOutputModel;
import pt.isel.ps.gis.utils.HeadersUtils;

import java.util.Optional;

@RestController
@RequestMapping("v1/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    public ResponseEntity<HouseOutputModel> getHouses(@PathVariable("id") long houseId) throws EntityException, NotFoundException {
        Optional<House> optionalHouse = houseService.getHouseByHouseId(houseId);
        House house = optionalHouse.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseOutputModel(house), HeadersUtils.setSirenContentType(headers), HttpStatus.OK);
    }
}

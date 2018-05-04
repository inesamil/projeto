package pt.isel.ps.gis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.model.outputModel.ListsOutputModel;

@RestController
@RequestMapping("v1/houses/{house-id}/lists")
public class ListController {

    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("")
    public ResponseEntity<ListsOutputModel> getLists(@PathVariable("house-id") long houseId) {
        listService.getListsByHouseId()
    }
}

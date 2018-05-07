package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.outputModel.ListOutputModel;
import pt.isel.ps.gis.model.outputModel.ListsOutputModel;
import pt.isel.ps.gis.model.outputModel.ProductsListOutputModel;
import pt.isel.ps.gis.model.requestParams.ListRequestParam;

import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/lists")
public class ListController {

    private final ListService listService;
    private final ListProductService listProductService;

    public ListController(ListService listService, ListProductService listProductService) {
        this.listService = listService;
        this.listProductService = listProductService;
    }

    @GetMapping("")
    public ResponseEntity<ListsOutputModel> getLists(
            @PathVariable("house-id") long houseId,
            // TODO change request header
            @RequestHeader("authorization") String username,
            ListRequestParam param
    ) throws EntityException {
        java.util.List<List> lists;
        if (param.isNull()) {
            ListService.ListFilters filters = new ListService.ListFilters(
                    true,
                    username,
                    true
            );
            lists = listService.getListsByHouseIdFiltered(houseId, filters);
        } else {
            ListService.ListFilters filters = new ListService.ListFilters(
                    param.getSystem(),
                    param.getUser(),
                    param.getShareable()
            );
            lists = listService.getListsByHouseIdFiltered(houseId, filters);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> getList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws EntityException, NotFoundException {
        Optional<List> listOptional = listService.getListByListId(houseId, listId);
        List list = listOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(list), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}/products")
    public ResponseEntity<ProductsListOutputModel> getProductsInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws EntityException {
        java.util.List<ListProduct> listProducts = listProductService.getListProductsByListId(houseId, listId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductsListOutputModel(houseId, listId, listProducts),
                setCollectionContentType(headers), HttpStatus.OK);
    }
}

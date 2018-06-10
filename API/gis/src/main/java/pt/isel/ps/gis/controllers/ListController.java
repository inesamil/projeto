package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.inputModel.ListInputModel;
import pt.isel.ps.gis.model.inputModel.ListProductInputModel;
import pt.isel.ps.gis.model.outputModel.ListOutputModel;
import pt.isel.ps.gis.model.outputModel.ListProductsOutputModel;
import pt.isel.ps.gis.model.outputModel.ListsOutputModel;
import pt.isel.ps.gis.model.outputModel.UserListsOutputModel;

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
    public ResponseEntity<ListsOutputModel> geHouseLists(
            @PathVariable("house-id") long houseId
    ) throws NotFoundException, BadRequestException {
        java.util.List<List> lists;
        try {
            lists = listService.getListsByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> getList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws NotFoundException, BadRequestException {
        List list;
        try {
            list = listService.getListByListId(houseId, listId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(list), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}/products")
    public ResponseEntity<ListProductsOutputModel> getProductsInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws BadRequestException, NotFoundException {
        java.util.List<ListProduct> listProducts;
        try {
            listProducts = listProductService.getListProductsByListId(houseId, listId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListProductsOutputModel(houseId, listId, listProducts),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> putList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @RequestBody ListInputModel body
    ) throws BadRequestException, NotFoundException {
        List list;
        try {
            list = listService.updateList(houseId, listId, body.getName(), body.getShareable());
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(list), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListProductsOutputModel> putProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId,
            @RequestBody ListProductInputModel body
    ) throws BadRequestException, NotFoundException {
        java.util.List<ListProduct> listProducts;
        try {
            listProductService.associateListProduct(houseId, listId, productId, body.getBrand(), body.getQuantity());
            listProducts = listProductService.getListProductsByListId(houseId, listId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListProductsOutputModel(houseId, listId, listProducts),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{list-id}")
    public ResponseEntity<UserListsOutputModel> deleteList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws BadRequestException, NotFoundException {
        java.util.List<List> lists = null;
        try {
            listService.deleteUserListByListId(houseId, listId);
            /* TODO verificar se o user qe ta a apagar é quem criou a lista para poder apagar.
               Retornar a lista das listas do user que tá a fazer esta operação e passar a variavel username ao output model
              */
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel("", lists), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListProductsOutputModel> deleteProductFromList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId
    ) throws BadRequestException, NotFoundException {
        java.util.List<ListProduct> listProducts;
        try {
            listProductService.deleteListProductByListProductId(houseId, listId, productId);
            listProducts = listProductService.getListProductsByListId(houseId, listId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListProductsOutputModel(houseId, listId, listProducts),
                setSirenContentType(headers), HttpStatus.OK);
    }
}

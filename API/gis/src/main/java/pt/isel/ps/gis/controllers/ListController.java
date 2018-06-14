package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.config.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.House;
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
    private final HouseService houseService;
    private final AuthenticationFacade authenticationFacade;

    public ListController(ListService listService, ListProductService listProductService, HouseService houseService, AuthenticationFacade authenticationFacade) {
        this.listService = listService;
        this.listProductService = listProductService;
        this.houseService = houseService;
        this.authenticationFacade = authenticationFacade;
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
        String username = authenticationFacade.getAuthentication().getName();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers), HttpStatus.OK);
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
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List list;
        try {
            list = listService.updateList(houseId, listId, body.getName(), body.getShareable());
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        String username = authenticationFacade.getAuthentication().getName();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("/{list-id}/products")
    public ResponseEntity<ListProductsOutputModel> putProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @RequestBody ListProductInputModel body
    ) throws BadRequestException, NotFoundException {
        java.util.List<ListProduct> listProducts;
        try {
            listProductService.associateListProduct(houseId, listId, body.getProductId(), body.getBrand(), body.getQuantity());
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
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        java.util.List<List> lists;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            listService.deleteUserListByListId(username, houseId, listId);
            Long[] housesIds = houseService.getHousesByUserUsername(username)
                    .stream()
                    .map(House::getHouseId)
                    .toArray(Long[]::new);
            lists = listService.getAvailableListsByUserUsername(
                    username,
                    new ListService.AvailableListFilters(
                            housesIds,
                            false,
                            true,
                            false
                    ));
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(headers),
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

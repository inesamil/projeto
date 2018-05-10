package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.inputModel.ListInputModel;
import pt.isel.ps.gis.model.inputModel.ListProductInputModel;
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
    private final HouseService houseService;
    private final ListProductService listProductService;

    public ListController(ListService listService, HouseService houseService, ListProductService listProductService) {
        this.listService = listService;
        this.houseService = houseService;
        this.listProductService = listProductService;
    }

    @GetMapping("")
    public ResponseEntity<ListsOutputModel> getLists(
            @PathVariable("house-id") long houseId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username,
            ListRequestParam param
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        ListService.ListFilters filters;
        if (param.isNull()) {
            filters = new ListService.ListFilters(
                    true,
                    username,
                    true
            );
        } else {
            filters = new ListService.ListFilters(
                    param.getSystem(),
                    param.getUser(),
                    param.getShareable()
            );
        }
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> getList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws EntityException, NotFoundException, BadRequestException {
        Optional<List> listOptional = listService.getListByListId(houseId, listId);
        List list = listOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(list), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}/products")
    public ResponseEntity<ProductsListOutputModel> getProductsInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId
    ) throws EntityException, BadRequestException {
        checkList(houseId, listId);
        java.util.List<ListProduct> listProducts = listProductService.getListProductsByListId(houseId, listId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductsListOutputModel(houseId, listId, listProducts),
                setCollectionContentType(headers), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ListsOutputModel> postList(
            @PathVariable("house-id") long houseId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username,
            @RequestBody ListInputModel body
    ) throws BadRequestException, EntityException {
        checkHouse(houseId);
        listService.addUserList(new UserList(
                houseId,
                body.getName(),
                body.getUsername(),
                body.getShareable()
        ));
        ListService.ListFilters filters = new ListService.ListFilters(
                true,
                username,
                true
        );
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.CREATED);
    }

    @PutMapping("/{list-id}")
    public ResponseEntity<ListsOutputModel> putList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username,
            @RequestBody ListInputModel body
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        List list = listService.getListByListId(houseId, listId)
                .orElseThrow(() -> new BadRequestException("List does not exist."));
        // TODO meter este if no serviço
        if (list.getListType().equals("system"))
            throw new BadRequestException("Invalid list.");
        boolean toUpdate = false;
        if (body.getName() != null && !list.getListName().equals(body.getName())) {
            list.setListName(body.getName());
            toUpdate = true;
        }
        if (body.getShareable() != null && !list.getUserlist().getListShareable().equals(body.getShareable())) {
            list.getUserlist().setListShareable(body.getShareable());
            toUpdate = true;
        }
        if (toUpdate)
            listService.updateList(list);
        ListService.ListFilters filters = new ListService.ListFilters(
                true,
                username,
                true
        );
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListsOutputModel> putProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") String categoryProductId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username,
            @RequestBody ListProductInputModel body
    ) throws EntityException, BadRequestException, EntityNotFoundException {
        checkHouse(houseId);
        checkList(houseId, listId);
        String[] ids = extractCategoryIdAndProductId(categoryProductId);
        int categoryId, productId;
        try {
            categoryId = Integer.parseInt(ids[0]);
            productId = Integer.parseInt(ids[1]);
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Invalid product id.");
        }
        if (body.getBrand() == null || body.getQuantity() == null)
            throw new BadRequestException("You must specify the body correctly.");
        ListProduct listProduct = new ListProduct(houseId, listId, categoryId, productId, body.getBrand(), body.getQuantity());
        if (isToUpdateList(houseId, listId, categoryId, productId))
            listProductService.updateListProduct(listProduct);
        else
            listProductService.addListProduct(listProduct);
        ListService.ListFilters filters = new ListService.ListFilters(
                true,
                username,
                true
        );
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }


    @DeleteMapping("/{list-id}")
    public ResponseEntity<ListsOutputModel> deleteList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        checkList(houseId, listId);
        listService.deleteUserListByListId(houseId, listId);
        ListService.ListFilters filters = new ListService.ListFilters(
                true,
                username,
                true
        );
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListsOutputModel> deleteProductFromList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") String categoryProductId,
            // TODO change request header
            @RequestHeader(value = "authorization", required = false) String username
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        checkList(houseId, listId);
        String[] ids = extractCategoryIdAndProductId(categoryProductId);
        int categoryId, productId;
        try {
            categoryId = Integer.parseInt(ids[0]);
            productId = Integer.parseInt(ids[1]);
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Invalid product id.");
        }
        checkProductInList(houseId, listId, categoryId, productId);
        listProductService.deleteListProductByListProductId(houseId, listId, categoryId, productId);
        ListService.ListFilters filters = new ListService.ListFilters(
                true,
                username,
                true
        );
        java.util.List<List> lists = listService.getListsByHouseIdFiltered(houseId, filters);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }

    private void checkList(long houseId, short listId) throws EntityException, BadRequestException {
        if (!listService.existsListByListId(houseId, listId))
            throw new BadRequestException("List does not exist.");
    }

    private void checkProductInList(long houseId, short listId, int categoryId, int productId) throws BadRequestException, EntityException {
        if (listProductService.existsListProductByListProductId(houseId, listId, categoryId, productId))
            throw new BadRequestException("Product in that list does not exist.");
    }

    private String[] extractCategoryIdAndProductId(String s) throws BadRequestException {
        String[] ids = s.split("-");
        if (ids.length != 2)
            throw new BadRequestException("Invalid category - product id.");
        return ids;
    }

    private boolean isToUpdateList(long houseId, short listId, int categoryId, int productId) throws EntityException {
        return listProductService.existsListProductByListProductId(houseId, listId, categoryId, productId);
    }
}

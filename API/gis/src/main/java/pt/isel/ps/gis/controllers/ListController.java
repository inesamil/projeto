package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.components.AuthenticationFacade;
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

import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/lists")
public class ListController {

    private final ListService listService;
    private final ListProductService listProductService;
    private final HouseService houseService;
    private final AuthenticationFacade authenticationFacade;
    private final MessageSource messageSource;

    public ListController(ListService listService, ListProductService listProductService, HouseService houseService, AuthenticationFacade authenticationFacade, MessageSource messageSource) {
        this.listService = listService;
        this.listProductService = listProductService;
        this.houseService = houseService;
        this.authenticationFacade = authenticationFacade;
        this.messageSource = messageSource;
    }

    @GetMapping("")
    public ResponseEntity<ListsOutputModel> geHouseLists(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        java.util.List<List> lists;
        try {
            lists = listService.getListsByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> getList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        List list;
        try {
            list = listService.getListByListId(houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        String username = authenticationFacade.getAuthentication().getName();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{list-id}/products")
    public ResponseEntity<ListProductsOutputModel> getProductsInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        java.util.List<ListProduct> listProducts;
        try {
            listProducts = listProductService.getListProductsByListId(houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListProductsOutputModel(houseId, listId, listProducts),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PostMapping("/{list-id}/products")
    public ResponseEntity<ListOutputModel> postProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @RequestBody ListProductInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ConflictException {
        List list;
        String username;
        try {
            listProductService.addListProduct(houseId, listId, body.getProductId(), body.getBrand(), body.getQuantity(), locale);
            list = listService.getListByListId(houseId, listId, locale);
            username = authenticationFacade.getAuthentication().getName();
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers),
                HttpStatus.CREATED);
    }

    @PutMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> putList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @RequestBody ListInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List list;
        try {
            list = listService.updateList(houseId, listId, body.getName(), body.getShareable(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        String username = authenticationFacade.getAuthentication().getName();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListOutputModel> putProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId,
            @RequestBody ListProductInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List list;
        String username;
        try {
            listProductService.associateListProduct(houseId, listId, productId, body.getBrand(), body.getQuantity(), locale);
            list = listService.getListByListId(houseId, listId, locale);
            username = authenticationFacade.getAuthentication().getName();
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @DeleteMapping("/{list-id}")
    public ResponseEntity<UserListsOutputModel> deleteList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        java.util.List<List> lists;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            listService.deleteUserListByListId(username, houseId, listId, locale);
            Long[] housesIds = houseService.getHousesByUserUsername(username, locale)
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
                    ),
                    locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListOutputModel> deleteProductFromList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List list;
        String username;
        try {
            listProductService.deleteListProductByListProductId(houseId, listId, productId, locale);
            list = listService.getListByListId(houseId, listId, locale);
            username = authenticationFacade.getAuthentication().getName();
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list),
                setSirenContentType(headers), HttpStatus.OK);
    }
}

package pt.isel.ps.gis.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("")
    public ResponseEntity<ListsOutputModel> geHouseLists(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws NotFoundException, BadRequestException, ForbiddenException {
        java.util.List<List> lists;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            lists = listService.getListsByHouseId(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListsOutputModel(houseId, lists), setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{list-id}")
    public ResponseEntity<ListOutputModel> getList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            Locale locale
    ) throws NotFoundException, BadRequestException, ForbiddenException {
        List list;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            list = listService.getListByListId(username, houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{list-id}/products")
    public ResponseEntity<ListProductsOutputModel> getProductsInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        java.util.List<ListProduct> listProducts;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            listProducts = listProductService.getListProductsByListId(username, houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListProductsOutputModel(houseId, listId, listProducts),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/{list-id}/products")
    public ResponseEntity<ListOutputModel> postProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @RequestBody ListProductInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ConflictException, ForbiddenException {
        List list;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            // TODO autorizacao no add?
            listProductService.addListProduct(houseId, listId, body.getProductId(), body.getBrand(), body.getQuantity(), locale);
            list = listService.getListByListId(username, houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers),
                HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        String username = authenticationFacade.getAuthentication().getName();
        return new ResponseEntity<>(new ListOutputModel(username, list), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListOutputModel> putProductInList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId,
            @RequestBody ListProductInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List list;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            // TODO autorizacao no associate?
            listProductService.associateListProduct(houseId, listId, productId, body.getBrand(), body.getQuantity(), locale);
            list = listService.getListByListId(username, houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new UserListsOutputModel(username, lists), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{list-id}/products/{product-id}")
    public ResponseEntity<ListOutputModel> deleteProductFromList(
            @PathVariable("house-id") long houseId,
            @PathVariable("list-id") short listId,
            @PathVariable("product-id") int productId,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List list;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            // TODO autorizacao no delete?
            listProductService.deleteListProductByListProductId(houseId, listId, productId, locale);
            list = listService.getListByListId(username, houseId, listId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ListOutputModel(username, list),
                setSirenContentType(headers), HttpStatus.OK);
    }
}

package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.model.inputModel.StorageInputModel;
import pt.isel.ps.gis.model.outputModel.StorageOutputModel;
import pt.isel.ps.gis.model.outputModel.StoragesOutputModel;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/storages")
public class StorageController {

    private final StorageService storageService;
    private final MessageSource messageSource;

    public StorageController(StorageService storageService, MessageSource messageSource) {
        this.storageService = storageService;
        this.messageSource = messageSource;
    }

    @GetMapping("")
    public ResponseEntity<StoragesOutputModel> getStorages(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<Storage> storages;
        try {
            storages = storageService.getStorageByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{storage-id}")
    public ResponseEntity<StorageOutputModel> getStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        Storage storage;
        try {
            storage = storageService.getStorageByStorageId(houseId, storageId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StorageOutputModel(storage), setSirenContentType(headers), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<StorageOutputModel> postStorage(
            @PathVariable("house-id") long houseId,
            @RequestBody StorageInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, URISyntaxException {
        Storage storage;
        try {
            storage = storageService.addStorage(
                    houseId,
                    body.getName(),
                    body.getMinimumTemperature(),
                    body.getMaximumTemperature(),
                    locale
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        StorageId storageId = storage.getId();
        headers.setLocation(new URI(UriBuilderUtils.buildStorageUri(storageId.getHouseId(), storageId.getStorageId())));
        return new ResponseEntity<>(new StorageOutputModel(storage), setSirenContentType(headers),
                HttpStatus.CREATED);
    }

    @PutMapping("/{storage-id}")
    public ResponseEntity<StorageOutputModel> putStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId,
            @RequestBody StorageInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        Storage storage;
        try {
            storage = storageService.updateStorage(
                    houseId,
                    storageId,
                    body.getName(),
                    body.getMinimumTemperature(),
                    body.getMaximumTemperature(),
                    locale
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StorageOutputModel(storage), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{storage-id}")
    public ResponseEntity<StoragesOutputModel> deleteStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<Storage> storages;
        try {
            storageService.deleteStorageByStorageId(houseId, storageId, locale);
            storages = storageService.getStorageByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.OK);
    }
}

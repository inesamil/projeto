package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.outputModel.StorageOutputModel;
import pt.isel.ps.gis.model.outputModel.StoragesOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/storages")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("")
    public ResponseEntity<StoragesOutputModel> getStorages(@PathVariable("house-id") long houseId) throws EntityException {
        List<Storage> storages = storageService.getStorageByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{storage-id}")
    public ResponseEntity<StorageOutputModel> getStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId) throws EntityException, NotFoundException {
        Optional<Storage> storageOptional = storageService.getStorageByStorageId(houseId, storageId);
        Storage storage = storageOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StorageOutputModel(storage), setSirenContentType(headers), HttpStatus.OK);
    }
}

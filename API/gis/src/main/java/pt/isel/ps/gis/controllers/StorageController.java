package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Numrange;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.inputModel.StorageInputModel;
import pt.isel.ps.gis.model.outputModel.StorageOutputModel;
import pt.isel.ps.gis.model.outputModel.StoragesOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/storages")
public class StorageController {

    private final StorageService storageService;
    private final HouseService houseService;

    public StorageController(StorageService storageService, HouseService houseService) {
        this.storageService = storageService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<StoragesOutputModel> getStorages(
            @PathVariable("house-id") long houseId
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        List<Storage> storages = storageService.getStorageByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{storage-id}")
    public ResponseEntity<StorageOutputModel> getStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId
    ) throws EntityException, NotFoundException, BadRequestException {
        Optional<Storage> storageOptional = storageService.getStorageByStorageId(houseId, storageId);
        Storage storage = storageOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StorageOutputModel(storage), setSirenContentType(headers), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<StoragesOutputModel> postStorage(
            @PathVariable("house-id") long houseId,
            @RequestBody StorageInputModel body
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        storageService.addStorage(new Storage(
                houseId,
                body.getName(),
                new Numrange(body.getMinimumTemperature(), body.getMaximumTemperature())
        ));
        List<Storage> storages = storageService.getStorageByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.CREATED);
    }

    @PutMapping("/{storage-id}")
    public ResponseEntity<StoragesOutputModel> putStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId,
            @RequestBody StorageInputModel body
    ) throws EntityException, BadRequestException, EntityNotFoundException {
        checkHouse(houseId);
        Storage storage = storageService.getStorageByStorageId(houseId, storageId)
                .orElseThrow(() -> new BadRequestException("Storage does not exist."));
        boolean toUpdate = false;
        if (body.getName() != null && !storage.getStorageName().equals(body.getName())) {
            storage.setStorageName(body.getName());
            toUpdate = true;
        }
        if (body.getMinimumTemperature() != null && body.getMaximumTemperature() != null) {
            Numrange storageTemperature = storage.getStorageTemperature();
            if (!storageTemperature.getMinimum().equals(body.getMinimumTemperature())
                    || storage.getStorageTemperature().getMaximum().equals(body.getMaximumTemperature())) {
                Numrange numrange = new Numrange(body.getMinimumTemperature(), body.getMaximumTemperature());
                storage.setStorageTemperature(numrange);
                toUpdate = true;
            }
        }
        if (toUpdate)
            storageService.updateStorage(storage);
        List<Storage> storages = storageService.getStorageByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{storage-id}")
    public ResponseEntity<StoragesOutputModel> deleteStorage(
            @PathVariable("house-id") long houseId,
            @PathVariable("storage-id") short storageId
    ) throws EntityException, BadRequestException, EntityNotFoundException {
        checkHouse(houseId);
        checkStorage(houseId, storageId);
        storageService.deleteStorageByStorageId(houseId, storageId);
        List<Storage> storages = storageService.getStorageByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StoragesOutputModel(houseId, storages), setSirenContentType(headers),
                HttpStatus.OK);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }

    private void checkStorage(long houseId, short storageId) throws EntityException, BadRequestException {
        if (!storageService.existsStorageByStorageId(houseId, storageId))
            throw new BadRequestException("Storage does not exist.");
    }
}

package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.model.outputModel.IndexOutputModel;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;

@RestController
@RequestMapping("v1")
public class IndexController {

    @GetMapping("")
    public ResponseEntity<IndexOutputModel> getIndex() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }
}

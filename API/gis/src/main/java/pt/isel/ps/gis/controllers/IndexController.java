package pt.isel.ps.gis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.model.outputModel.IndexOutputModel;

import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setJsonHomeContentType;

@RestController
@RequestMapping("/v1")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final MessageSource messageSource;

    public IndexController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("")
    public ResponseEntity<IndexOutputModel> getIndex(Locale locale) {
        log.info(messageSource.getMessage("test", null, locale));
        log.info(messageSource.getMessage("test.abc", new Object[]{"xpto"}, locale));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new IndexOutputModel(), setJsonHomeContentType(headers), HttpStatus.OK);
    }
}

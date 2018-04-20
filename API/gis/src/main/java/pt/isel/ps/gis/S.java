package pt.isel.ps.gis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.List;

@RestController
public class S {

    @Autowired
    private ListRepository repository;

    @GetMapping("")
    public void g() throws EntityException {
        java.util.List<List> listsFiltered = repository.findListsFiltered(1L, false, "nuno", false);
        for (List l : listsFiltered) {
            System.out.println(l);
        }
    }
}

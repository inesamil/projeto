package pt.isel.ps.gis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.UserService;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.Users;

@RestController
public class S {

    @Autowired
    private ListRepository repository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public void g() throws EntityException {
        java.util.List<List> listsFiltered = repository.findListsFiltered(1L, false, "nuno", false);
        for (List l : listsFiltered) {
            System.out.println(l);
        }
    }

    @GetMapping("/house")
    public void g1() throws EntityException {
        try {
            House house = userService.updateUser(new Users("ze", "ze.@gmail.com", 30, null, null));
            System.out.println(house.toString());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }
}

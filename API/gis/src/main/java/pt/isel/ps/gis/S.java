package pt.isel.ps.gis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.StockItem;

@RestController
public class S {

    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    public void g() throws EntityException {
        Product product = new Product(7, "Produto A", true, (short) 3, "week");
        repository.insertProduct(product);
    }
}

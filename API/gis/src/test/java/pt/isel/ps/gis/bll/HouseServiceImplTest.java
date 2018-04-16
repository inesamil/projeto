package pt.isel.ps.gis.bll;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pt.isel.ps.gis.AppConfig;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.Repository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class HouseServiceImplTest {

    @Autowired
    private HouseService houseService;

    @Before
    public void initialize() {
        System.out.println(houseService.toString());
    }

    @Test
    public void existsHouseByHouseIdTest() {
        Long houseId = 1L;
        assertFalse(houseService.existsHouseByHouseId(houseId));
    }

    @Test
    public void getHouseByHouseIdTest() {
    }

    @Test
    public void getHousesByUserIdTest() {
    }

    @Test
    public void addHouseTest() {
    }

    @Test
    public void updateHouseTest() {
    }

    @Test
    public void deleteHouseTest() {
    }
}
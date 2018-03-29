package pt.isel.ps.gis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pt.isel.ps.gis.DAL.Repositories.IHouseRepository;
import pt.isel.ps.gis.DAL.bdModel.House;

import java.util.List;

@SpringBootApplication
public class GisApplication {

	private static final Logger log = LoggerFactory.getLogger(GisApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GisApplication.class, args);
	}

	@Bean
    public CommandLineRunner demo(IHouseRepository repo) {
	    return args -> {
            House casa1 = repo.save(new House("Minha casa", (short)1, (short)1, (short)1, (short)1));
            House casa2 = repo.save(new House("Minha casa", (short)1, (short)1, (short)1, (short)1));

	        log.info(casa1.toString());
	        log.info(casa2.toString());

	        log.info("");
	        log.info("Fetch house by name");
            List<House> houses = repo.findByName("Minha casa");
            houses.forEach(House::toString);
        };
    }
}

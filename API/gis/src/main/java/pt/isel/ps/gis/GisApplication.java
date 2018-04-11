package pt.isel.ps.gis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GisApplication {

    private static final Logger log = LoggerFactory.getLogger(GisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class, args);
    }
}

package pt.isel.ps.gis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.isel.ps.gis.stockAlgorithm.BasicStockManagementAlgorithm;
import pt.isel.ps.gis.stockAlgorithm.StockManagementAlgorithm;

@SpringBootApplication
public class GisApplication {

    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public StockManagementAlgorithm stockManagementAlgorithm() {
        return new BasicStockManagementAlgorithm();
    }
}

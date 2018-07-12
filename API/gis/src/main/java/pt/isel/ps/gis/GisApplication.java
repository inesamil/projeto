package pt.isel.ps.gis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.stockAlgorithm.BasicStockManagementAlgorithm;
import pt.isel.ps.gis.stockAlgorithm.StockManagementAlgorithm;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.AuthorizationProviderImpl;

import java.util.Locale;

@SpringBootApplication
public class GisApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
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

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("locales/messages");
        return messageSource;
    }

    @Bean
    public AuthorizationProvider authorizationProvider(UserHouseRepository userHouseRepository, MessageSource messageSource) {
        return new AuthorizationProviderImpl(userHouseRepository, messageSource);
    }
}

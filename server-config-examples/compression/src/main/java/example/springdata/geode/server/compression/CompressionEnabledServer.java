package example.springdata.geode.server.compression;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import example.springdata.geode.server.compression.config.CompressionEnabledServerConfig;
import example.springdata.geode.server.compression.domain.Customer;
import example.springdata.geode.server.compression.domain.EmailAddress;
import example.springdata.geode.server.compression.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackageClasses = CompressionEnabledServerConfig.class)
public class CompressionEnabledServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new SpringApplicationBuilder(CompressionEnabledServer.class).web(WebApplicationType.NONE).build().run(args);
    }

    @Bean
    ApplicationRunner runner(CustomerRepository customerRepo) {
        logger.info("Inserting 4000 Customers into compressed region");
        return args -> {
            Faker faker = new Faker();
            Name fakerName = faker.name();
            Internet fakerInternet = faker.internet();
            for(long i = 0; i < 4000; i ++) {
                customerRepo.save(new Customer(i, new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()));
            }
        };
    }
}

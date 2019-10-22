package example.springdata.geode.server.wan.client.config;

import example.springdata.geode.client.common.client.config.ClientApplicationConfig;
import example.springdata.geode.server.wan.client.repo.CustomerRepository;
import example.springdata.geode.server.wan.client.repo.CustomerRepository;
import example.springdata.geode.server.wan.client.services.CustomerService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components.
 *
 * @author Udo Kohlmeyer
* @author Patrick Johnson
 */
@Configuration
@Import(ClientApplicationConfig.class)
@ComponentScan(basePackageClasses = CustomerService.class)
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class WanClientConfig {

}

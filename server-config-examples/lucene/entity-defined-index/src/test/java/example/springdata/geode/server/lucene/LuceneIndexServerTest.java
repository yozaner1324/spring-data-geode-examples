package example.springdata.geode.server.lucene;

import example.springdata.geode.server.lucene.domain.Customer;
import example.springdata.geode.server.lucene.domain.DataCreators;
import example.springdata.geode.server.lucene.repo.CustomerRepository;
import org.apache.geode.cache.lucene.LuceneResultStruct;
import org.apache.geode.cache.lucene.LuceneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.search.lucene.LuceneTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = LuceneIndexServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LuceneIndexServerTest {

    @Autowired
    LuceneTemplate luceneTemplate;

    @Autowired
    LuceneService luceneService;

    @Autowired
    private CustomerRepository customerRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void luceneIsConfiguredCorrectly() {
        assertThat(luceneService.getAllIndexes().size()).isEqualTo(1);
        assertThat(luceneService.getAllIndexes().iterator().next().getRegionPath()).isEqualTo("/Customers");
        assertThat(luceneService.getAllIndexes().iterator().next().getFieldNames().length).isEqualTo(1);
        assertThat(luceneService.getAllIndexes().iterator().next().getFieldNames()[0]).isEqualTo("lastName");

        logger.info("Inserting 300 customers");
        DataCreators.createLuceneCustomers(300, customerRepository);

        assertThat(customerRepository.count()).isEqualTo(300);

        logger.info("Completed creating customers ");

        final List<LuceneResultStruct<Object, Customer>> lastName = luceneTemplate.query("D*", "lastName", 300);

        logger.info("Customers with last names beginning with 'D':");
        lastName.forEach(result -> logger.info(result.getValue().toString()));
    }
}
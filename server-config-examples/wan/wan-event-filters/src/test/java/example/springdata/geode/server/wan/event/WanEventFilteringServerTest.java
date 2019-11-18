package example.springdata.geode.server.wan.event;

import example.springdata.geode.server.wan.client.config.WanClientConfig;
import example.springdata.geode.server.wan.domain.Customer;
import org.apache.geode.cache.Region;
import org.awaitility.Awaitility;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = WanClientConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class WanEventFilteringServerTest extends ForkingClientServerIntegrationTestsSupport {

    @Resource(name = "Customers")
    private Region<Long, Customer> customers;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setup() throws IOException {
        startGemFireServer(WanEventFilteringServer.class, "-Dspring.profiles.active=SiteB");
        startGemFireServer(WanEventFilteringServer.class, "-Dspring.profiles.active=SiteA");
        System.getProperties().remove("spring.data.gemfire.pool.servers");
    }

    @Ignore
    @Test
    public void  wanReplicationOccursCorrectly() {
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(()-> customers.keySetOnServer().size() == 150);
        assertThat(customers.keySetOnServer().size()).isEqualTo(150);
        logger.info(customers.keySetOnServer().size() + " entries replicated to siteA");
    }
}

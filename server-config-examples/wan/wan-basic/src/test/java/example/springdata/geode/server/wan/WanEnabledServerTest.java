/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.server.wan;

import example.springdata.geode.server.wan.client.WanClientConfig;
import example.springdata.geode.server.wan.server.siteA.WanEnabledServerSiteA;
import example.springdata.geode.server.wan.server.siteB.WanEnabledServerSiteB;
import org.apache.geode.cache.Region;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = WanClientConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class WanEnabledServerTest extends ForkingClientServerIntegrationTestsSupport {

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(WanEnabledServerSiteB.class);
		startGemFireServer(WanEnabledServerSiteA.class);
		System.getProperties().remove("spring.data.gemfire.pool.servers");
	}

	@Test
	@Ignore
	public void wanReplicationOccurs() {
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> customers.keySetOnServer().size() == 301);
		Assertions.assertThat(customers.keySetOnServer().size()).isEqualTo(301);
		logger.info(customers.keySetOnServer().size() + " entries replicated to siteB");
	}
}
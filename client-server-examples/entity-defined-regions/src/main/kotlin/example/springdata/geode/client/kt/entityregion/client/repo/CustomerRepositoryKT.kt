package example.springdata.geode.client.kt.entityregion.client.repo

import example.springdata.geode.client.common.kt.client.repo.BaseCustomerRepositoryKT
import org.springframework.data.gemfire.mapping.annotation.ClientRegion

@ClientRegion("Customers")
interface CustomerRepositoryKT : BaseCustomerRepositoryKT
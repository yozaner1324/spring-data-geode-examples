package example.springdata.geode.functions.cascading.kt.client.repo

import example.springdata.geode.domain.Customer
import org.springframework.data.gemfire.mapping.annotation.ClientRegion
import org.springframework.data.repository.CrudRepository

interface CustomerRepositoryKT : CrudRepository<Customer,Long>
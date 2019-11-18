package example.springdata.geode.functions.cascading.kt.client.repo

import example.springdata.geode.functions.cascading.kt.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepositoryKT : CrudRepository<Order, Long>
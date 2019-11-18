package example.springdata.geode.client.function.kt.client.repo

import example.springdata.geode.client.function.kt.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepositoryKT : CrudRepository<Order, Long>
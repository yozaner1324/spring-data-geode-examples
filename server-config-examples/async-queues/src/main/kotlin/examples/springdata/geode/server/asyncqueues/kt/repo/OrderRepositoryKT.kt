package examples.springdata.geode.server.asyncqueues.kt.repo

import examples.springdata.geode.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepositoryKT : CrudRepository<Order, Long>

package example.springdata.geode.server.eventhandlers.kt.utils

import example.springdata.geode.server.eventhandlers.kt.domain.Customer
import org.apache.geode.cache.util.CacheWriterAdapter

class CustomerCacheWriterKT : CacheWriterAdapter<Long, Customer>()

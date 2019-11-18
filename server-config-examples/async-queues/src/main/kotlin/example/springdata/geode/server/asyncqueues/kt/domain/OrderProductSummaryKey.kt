package example.springdata.geode.server.asyncqueues.kt.domain

import java.io.Serializable

data class OrderProductSummaryKey(val productId: Long?, val timebucketStart: Long) : Serializable
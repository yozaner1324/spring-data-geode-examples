/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package examples.springdata.geode.domain

import examples.springdata.geode.util.sumBy
import org.springframework.data.annotation.Id
import org.springframework.data.gemfire.mapping.annotation.Region
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Entity

/**
 * Orders object used in the examples
 * @author Oliver Gierke
 * @author David Turanski
 * @author Udo Kohlmeyer
 */
@Region("Orders")
@Entity
data class Order @JvmOverloads constructor(@Id @javax.persistence.Id val id: Long?, val customerId: Long,
                                           private val billingAddress: Address, private val shippingAddress: Address = billingAddress) : Serializable {
    private val lineItems: MutableList<LineItem> = mutableListOf()
    /**
     * Returns the total of the [Order].
     *
     * @return
     */
    val total: BigDecimal
        get() =
            if (lineItems.size == 0) {
                BigDecimal.ZERO
            } else {
                lineItems.asSequence().sumBy { it.total }
            }

    /**
     * Adds the given [LineItem] to the [Order].
     *
     * @param lineItem
     */
    fun add(lineItem: LineItem) = lineItems.add(lineItem)

    /**
     * Returns all [LineItem]s currently belonging to the [Order].
     *
     * @return
     */
    fun getLineItems(): List<LineItem> = lineItems.toList()

    override fun toString(): String =
        "Order(id=$id, customerId=$customerId, billingAddress=$billingAddress, shippingAddress=$shippingAddress) \n\t" +
            "LineItems:$lineItems"


    companion object {
        private const val serialVersionUID = -3779061453639083037L
    }
}

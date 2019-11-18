package example.springdata.geode.server.expiration.entity.kt.domain

import org.springframework.data.annotation.Id
import org.springframework.data.gemfire.expiration.IdleTimeoutExpiration
import org.springframework.data.gemfire.expiration.TimeToLiveExpiration
import org.springframework.data.gemfire.mapping.annotation.Region
import java.io.Serializable

@Region(name = "Customers")
@IdleTimeoutExpiration(action = "DESTROY", timeout = "2")
@TimeToLiveExpiration(action = "DESTROY", timeout = "4")
data class CustomerKT(@field:Id
                      private val id: Long?, private val emailAddress: EmailAddress,
                      private val firstName: String, private val lastName: String) : Serializable {

    constructor(id: Long?, emailAddress: EmailAddress,
                firstName: String, lastName: String,
                vararg address: Address) : this(id, emailAddress, firstName, lastName) {
        addresses.addAll(address.toList())
    }

    private val addresses = mutableListOf<Address>()

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
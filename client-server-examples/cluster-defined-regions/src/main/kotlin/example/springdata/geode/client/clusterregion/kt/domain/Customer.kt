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

package example.springdata.geode.client.clusterregion.kt.domain

import org.springframework.data.annotation.Id
import org.springframework.data.gemfire.mapping.annotation.Region
import org.springframework.util.Assert
import java.io.Serializable

/**
 * A customer used for Lucene examples.

 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */

@Region(name = "Customers")
data class Customer(@Id val id: Long?, val emailAddress: EmailAddress,
                    val firstName: String, val lastName: String) : Serializable {

    init {
        Assert.hasText(firstName, "First Name cannot be empty")
        Assert.hasText(lastName, "Last Name cannot be empty")
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}

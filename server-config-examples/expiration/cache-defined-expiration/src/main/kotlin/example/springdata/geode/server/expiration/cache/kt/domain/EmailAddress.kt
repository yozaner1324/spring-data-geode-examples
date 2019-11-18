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

package example.springdata.geode.server.expiration.cache.kt.domain

import org.springframework.util.Assert
import java.io.Serializable
import java.util.regex.Pattern

/**
 * Value object to represent email addresses.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
data class EmailAddress (val value: String) : Serializable {
    init {
        Assert.isTrue(isValid(value), "Invalid email address!")
    }

    companion object {
        private const val serialVersionUID = -2990839949384592331L
        private const val EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        private val PATTERN = Pattern.compile(EMAIL_REGEX)

        /**
         * Returns whether the given value is a valid [EmailAddress].
         *
         * @param source must not be null or empty.
         * @return
         */
        fun isValid(emailAddress: String): Boolean {
            Assert.hasText(emailAddress, "email address cannot be empty")
            return PATTERN.matcher(emailAddress).matches()
        }
    }
}

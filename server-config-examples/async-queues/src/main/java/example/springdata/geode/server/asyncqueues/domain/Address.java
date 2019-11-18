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

package example.springdata.geode.server.asyncqueues.domain;

import java.io.Serializable;

/**
 * An address used in the examples.
 *
 * @author Oliver Gierke
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
public class Address implements Serializable {
    private String street;
    private String city;
    private String country;

    public Address(String street, String city, String country) {
    	this.street = street;
    	this.city = city;
    	this.country = country;
	}
}

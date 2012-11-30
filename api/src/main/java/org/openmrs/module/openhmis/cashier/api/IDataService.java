/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.openhmis.cashier.api;

import org.openmrs.OpenmrsData;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.cashier.api.util.PagingInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Represents classes that provide data access services to model types that implement {@link OpenmrsData}.
 * @param <E> The entity model class.
 */
@Transactional
public interface IDataService<E extends OpenmrsData> extends IEntityService<E> {
	/**
	 * Voiding an entity essentially removes it from circulation.
	 *
	 * @param entity The entity object to void.
	 * @param reason The reason for voiding.
	 * @should void the entity
	 * @should throw IllegalArgumentException with null reason parameter
	 * @should throw NullPointerException with null entity
	 */
	public E voidEntity(E entity, String reason);

	/**
	 * Unvoid the entity record.
	 *
	 * @param entity The entity to be revived.
	 * @should unvoid the entity
	 * @should throw NullPointerException with null entity
	 */
	public E unvoidEntity(E entity) throws APIException;

	/**
	 * Returns all entity records that have the specified voided status.
	 * @param includeVoided {@code true} to include voided entities.
	 * @return All the entity records that have the specified voided status.
	 * @throws APIException
	 * @should return all voided entities when voided is set to true
	 * @should return all unvoided entities when voided is set to false
	 */
	List<E> getAll(boolean includeVoided) throws APIException;

	/**
	 * Returns all entity records that have the specified voided status and paging.
	 * @param includeVoided {@code true} to include voided entities.
	 * @param paging The paging information.
	 * @return All the entity records that have the specified voided status.
	 * @throws APIException
	 * @should return all voided entities when voided is set to true
	 * @should return all unvoided entities when voided is set to false
	 */
	List<E> getAll(boolean includeVoided, PagingInfo paging) throws APIException;

	/**
	 * Finds all the entities that start with the specified name.
	 * @param nameFragment The name fragment.
	 * @param includeVoided Whether voided items should be included in the results.
	 * @return All items that start with the specified name.
	 * @throws APIException
	 * @should throw IllegalArgumentException if the name is null
	 * @should throw IllegalArgumentException if the name is empty
	 * @should throw IllegalArgumentException if the name is longer than 255 characters
	 * @should return an empty list if no entities are found
	 * @should not return voided entities unless specified
	 * @should return entities that start with the specified name
	 */
	List<E> findByName(String nameFragment, boolean includeVoided) throws APIException;

	/**
	 * Finds all the entities that start with the specified name and paging.
	 * @param nameFragment The name fragment.
	 * @param includeRetired Whether voided items should be included in the results.
	 * @param paging The paging information.
	 * @return All items that start with the specified name.
	 * @throws APIException
	 * @should throw IllegalArgumentException if the name is null
	 * @should throw IllegalArgumentException if the name is empty
	 * @should throw IllegalArgumentException if the name is longer than 255 characters
	 * @should return an empty list if no entities are found
	 * @should not return voided entities unless specified
	 * @should return entities that start with the specified name
	 */
	List<E> findByName(String nameFragment, boolean includeRetired, PagingInfo paging) throws APIException;
}

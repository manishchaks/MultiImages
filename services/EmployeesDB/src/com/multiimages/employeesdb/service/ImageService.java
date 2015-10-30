/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.multiimages.employeesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;

import com.multiimages.employeesdb.*;

/**
 * Service object for domain model class Image.
 * @see com.multiimages.employeesdb.Image
 */

public interface ImageService {
   /**
	 * Creates a new image.
	 * 
	 * @param created
	 *            The information of the created image.
	 * @return The created image.
	 */
	public Image create(Image created);

	/**
	 * Deletes a image.
	 * 
	 * @param imageId
	 *            The id of the deleted image.
	 * @return The deleted image.
	 * @throws EntityNotFoundException
	 *             if no image is found with the given id.
	 */
	public Image delete(Integer imageId) throws EntityNotFoundException;

	/**
	 * Finds all images.
	 * 
	 * @return A list of images.
	 */
	public Page<Image> findAll(QueryFilter[] queryFilters, Pageable pageable);
	
	public Page<Image> findAll(Pageable pageable);
	
	/**
	 * Finds image by id.
	 * 
	 * @param id
	 *            The id of the wanted image.
	 * @return The found image. If no image is found, this method returns
	 *         null.
	 */
	public Image findById(Integer id) throws EntityNotFoundException;
	/**
	 * Updates the information of a image.
	 * 
	 * @param updated
	 *            The information of the updated image.
	 * @return The updated image.
	 * @throws EntityNotFoundException
	 *             if no image is found with given id.
	 */
	public Image update(Image updated) throws EntityNotFoundException;

	/**
	 * Retrieve the total count of the images in the repository.
	 * 
	 * @param None
	 *            .
	 * @return The count of the image.
	 */

	public long countAll();


    public Page<Image> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable);


}

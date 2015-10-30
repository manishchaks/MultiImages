/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.multiimages.employeesdb.controller; 

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import com.multiimages.employeesdb.service.ImageService;


import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.util.WMMultipartUtils;
import com.wavemaker.runtime.util.WMRuntimeUtils;
import com.wordnik.swagger.annotations.*;

import com.multiimages.employeesdb.*;
import com.multiimages.employeesdb.service.*;


/**
 * Controller object for domain model class Image.
 * @see com.multiimages.employeesdb.Image
 */

@RestController(value = "EmployeesDB.ImageController")
@Api(value = "/EmployeesDB/Image", description = "Exposes APIs to work with Image resource.")
@RequestMapping("/EmployeesDB/Image")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	@Qualifier("EmployeesDB.ImageService")
	private ImageService imageService;


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Returns the list of Image instances matching the search criteria.")
	public Page<Image> findImages( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
		LOGGER.debug("Rendering Images list");
		return imageService.findAll(queryFilters, pageable);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the list of Image instances.")
	public Page<Image> getImages(Pageable pageable) {
		LOGGER.debug("Rendering Images list");
		return imageService.findAll(pageable);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the total count of Image instances.")
	public Long countAllImages() {
		LOGGER.debug("counting Images");
		Long count = imageService.countAll();
		return count;
	}


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Image instance associated with the given id.")
    public Image getImage(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Image with id: {}" , id);
        Image instance = imageService.findById(id);
        LOGGER.debug("Image details with id: {}" , instance);
        return instance;
    }
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Image instance associated with the given id.")
    public boolean deleteImage(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Image with id: {}" , id);
        Image deleted = imageService.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Image instance associated with the given id.")
    public Image editImage(@PathVariable("id") Integer id, @RequestBody Image instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Image with id: {}" , instance.getId());
        instance.setId(id);
        instance = imageService.update(instance);
        LOGGER.debug("Image details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the Image instance associated with the given id.This API should be used when Image instance fields that require multipart data.")
    public Image editImage(@PathVariable("id") Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws EntityNotFoundException {
        Image newimage = WMMultipartUtils.toObject(multipartHttpServletRequest,Image.class,"EmployeesDB");
        newimage.setId(id);
        Image oldimage = imageService.findById(id);
        WMMultipartUtils.updateLobsContent(oldimage,newimage);
        LOGGER.debug("Updating image with information: {}" , newimage);
        return imageService.update(newimage);
    }



	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Image instance.")
	public Image createImage(@RequestBody Image instance) {
		LOGGER.debug("Create Image with information: {}" , instance);
		instance = imageService.create(instance);
		LOGGER.debug("Created Image with information: {}" , instance);
	    return instance;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@ApiOperation(value = "Creates a new Image instance.This API should be used when the Image instance has fields that requires multipart data.")
    public Image createImage(MultipartHttpServletRequest multipartHttpServletRequest) {
    	Image image = WMMultipartUtils.toObject(multipartHttpServletRequest,Image.class,"EmployeesDB");
        LOGGER.debug("Creating a new image with information: {}" , image);
        return imageService.create(image);
    }

    @RequestMapping(value = "/{id}/content/{fieldName}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves content for the given BLOB field in Image instance" )
    public void getImageBLOBContent(@PathVariable("id") Integer id,@PathVariable("fieldName") String fieldName,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
       LOGGER.debug("Retrieves content for the given BLOB field {} in Image instance" , fieldName);
       if(!WMRuntimeUtils.isLob(Image.class,fieldName))
       {
          throw new TypeMismatchException("Given field " + fieldName + " is not a valid BLOB type");
       }
       Image instance = imageService.findById(id);
       WMMultipartUtils.buildHttpResponseForBlob(instance,fieldName,httpServletRequest,httpServletResponse);
    }

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
	protected void setImageService(ImageService service) {
		this.imageService = service;
	}
}

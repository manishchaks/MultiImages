/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.multiimages.employeesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wavemaker.runtime.data.dao.*;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;

import com.multiimages.employeesdb.*;


/**
 * ServiceImpl object for domain model class Image.
 * @see com.multiimages.employeesdb.Image
 */
@Service("EmployeesDB.ImageService")
public class ImageServiceImpl implements ImageService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    @Qualifier("EmployeesDB.ImageDao")
    private WMGenericDao<Image, Integer> wmGenericDao;
    public void setWMGenericDao(WMGenericDao<Image, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }
     @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
     public Page<Image> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable){
          LOGGER.debug("Fetching all associated");
          return this.wmGenericDao.getAssociatedObjects(value, entityName, key, pageable);
     }

    @Transactional(value = "EmployeesDBTransactionManager")
    @Override
    public Image create(Image image) {
        LOGGER.debug("Creating a new image with information: {}" , image);
        return this.wmGenericDao.create(image);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "EmployeesDBTransactionManager")
    @Override
    public Image delete(Integer imageId) throws EntityNotFoundException {
        LOGGER.debug("Deleting image with id: {}" , imageId);
        Image deleted = this.wmGenericDao.findById(imageId);
        if (deleted == null) {
            LOGGER.debug("No image found with id: {}" , imageId);
            throw new EntityNotFoundException(String.valueOf(imageId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Page<Image> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all images");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Page<Image> findAll(Pageable pageable) {
        LOGGER.debug("Finding all images");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Image findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding image by id: {}" , id);
        Image image=this.wmGenericDao.findById(id);
        if(image==null){
            LOGGER.debug("No image found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return image;
    }
    @Transactional(rollbackFor = EntityNotFoundException.class, value = "EmployeesDBTransactionManager")
    @Override
    public Image update(Image updated) throws EntityNotFoundException {
        LOGGER.debug("Updating image with information: {}" , updated);
        this.wmGenericDao.update(updated);
        return this.wmGenericDao.findById((Integer)updated.getId());
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}


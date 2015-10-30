/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.multiimages.insertmultiimg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.multiimages.insertmultiimg.model.*;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.wavemaker.runtime.file.model.DownloadResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import com.multiimages.employeesdb.service.ImageService;

import org.springframework.transaction.annotation.Transactional;
import java.sql.Blob;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.commons.io.IOUtils;
import com.multiimages.employeesdb.*;
import com.multiimages.employeesdb.service.*;
import com.wavemaker.runtime.data.factory.TypeResolverSessionFactoryBean;
import java.io.*;
/**
 * This is a singleton class with all of its public methods exposed to the client via controller.
 * Their return values and parameters will be passed to the client or taken
 * from the client respectively.
 */
@ExposeToClient
public class InsertMultiImg {

    private static final Logger logger=LoggerFactory.getLogger(InsertMultiImg.class);
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TypeResolverSessionFactoryBean sessionFactory;
    
    @Transactional(value = "EmployeesDBTransactionManager")
    public void uploadFile(Integer relativePath, MultipartFile[] files, HttpServletRequest httpServletRequest) {
        /* Note: relativePath here maps to the id of the related Object to be saved in the transaction */
        File outputFile = null;
        Session session = sessionFactory.getObject().openSession();
        
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setEmployee(employeeService.findById(relativePath));
                
                byte[] byteArray = IOUtils.toByteArray(file.getInputStream());
                Blob blob = Hibernate.getLobCreator(session).createBlob(new ByteArrayInputStream(byteArray), new Long(byteArray.length));
               
                image.setImgdate(blob);
                
                imageService.create(image);
            } catch (Exception e) {
            }
        }
    }

}

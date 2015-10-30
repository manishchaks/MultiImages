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
 * ServiceImpl object for domain model class Employee.
 * @see com.multiimages.employeesdb.Employee
 */
@Service("EmployeesDB.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {


    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    @Qualifier("EmployeesDB.EmployeeDao")
    private WMGenericDao<Employee, Integer> wmGenericDao;
    public void setWMGenericDao(WMGenericDao<Employee, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }
     @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
     public Page<Employee> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable){
          LOGGER.debug("Fetching all associated");
          return this.wmGenericDao.getAssociatedObjects(value, entityName, key, pageable);
     }

    @Transactional(value = "EmployeesDBTransactionManager")
    @Override
    public Employee create(Employee employee) {
        LOGGER.debug("Creating a new employee with information: {}" , employee);
        return this.wmGenericDao.create(employee);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "EmployeesDBTransactionManager")
    @Override
    public Employee delete(Integer employeeId) throws EntityNotFoundException {
        LOGGER.debug("Deleting employee with id: {}" , employeeId);
        Employee deleted = this.wmGenericDao.findById(employeeId);
        if (deleted == null) {
            LOGGER.debug("No employee found with id: {}" , employeeId);
            throw new EntityNotFoundException(String.valueOf(employeeId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Page<Employee> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all employees");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Page<Employee> findAll(Pageable pageable) {
        LOGGER.debug("Finding all employees");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public Employee findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding employee by id: {}" , id);
        Employee employee=this.wmGenericDao.findById(id);
        if(employee==null){
            LOGGER.debug("No employee found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return employee;
    }
    @Transactional(rollbackFor = EntityNotFoundException.class, value = "EmployeesDBTransactionManager")
    @Override
    public Employee update(Employee updated) throws EntityNotFoundException {
        LOGGER.debug("Updating employee with information: {}" , updated);
        this.wmGenericDao.update(updated);
        return this.wmGenericDao.findById((Integer)updated.getId());
    }

    @Transactional(readOnly = true, value = "EmployeesDBTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}



package com.simplidata.multidbconfig.dbservice;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public class EmployeeDBService {
        
        protected Logger log = LoggerFactory.getLogger(this.getClass());
                
        @Autowired
        @Qualifier("employeeEntityManager")
        EntityManagerFactory employeeEntityManager;
        

        
        public void getData() {
                EntityManager entityManager = null;
                try {
                        entityManager = employeeEntityManager.createEntityManager();
                        //entityManager.getTransaction().begin();
                        Query selectQuery = entityManager.createNativeQuery("Select * from Persons");
               
                        List temp = selectQuery.getResultList();
                        
                        
                        log.info("Done Execution :::::"+ temp.size());


                        //entityManager.getTransaction().commit();
                        log.info("Done Execution :::::");
                }catch(Exception dbException) {
                        log.error("Exception " + dbException);
                        throw dbException;
                }
        }

}

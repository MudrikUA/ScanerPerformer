/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Mudrik
 */
public class ScanUtil {
      private static final SessionFactory sessionFactory = buildSessionFactory(); 
 
    private static SessionFactory buildSessionFactory() { 
        try { 
            // Create the SessionFactory from hibernate.cfg.xml 
            	//SessionFactory sessionFactory = new Configuration().configure(
		//			"./src/main/resources/hibernate.cfg.xml")
		//			.buildSessionFactory();
                //return sessionFactory;
            //return new AnnotationConfiguration().configure( new File("./src/main/resources/hibernate.cfg.xml")).buildSessionFactory(); 
            return new AnnotationConfiguration().configure(ScanUtil.class.getResource("/hibernate.cfg.xml")).buildSessionFactory(); 
 
        } catch (Throwable ex) { 
            // Make sure you log the exception, as it might be swallowed 
            System.err.println("Initial SessionFactory creation failed." + ex); 
            throw new ExceptionInInitializerError(ex); 
        } 
    } 
 
    public static SessionFactory getSessionFactory() { 
        return sessionFactory; 
    } 
 
    public static void shutdown() { 
        // Close caches and connection pools 
        getSessionFactory().close(); 
    } 
}

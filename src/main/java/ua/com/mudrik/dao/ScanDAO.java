/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.dao;

import org.hibernate.Session;
import ua.com.mudrik.dto.Scan;

/**
 *
 * @author Mudrik
 */
public class ScanDAO {

    private Session session;

    public void createNewScanRec(String scanerName, String scanerCode) {
        session = ScanUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Scan sc = new Scan();
        sc.setScanCode(scanerCode);
        sc.setScanerName(scanerName);
        session.save(sc);
        session.getTransaction().commit();
        session.close();
    }
}

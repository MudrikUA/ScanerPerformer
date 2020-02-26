/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import ua.com.mudrik.dto.Scan;
import ua.com.mudrik.dto.ScanDuplicate;

/**
 *
 * @author Mudrik
 */
public class ScanDuplicateDAO {

    private Session session;

    public void createNewScanDupRec(Scan dupScan, String scanerCode,
            String laminName, String scanerName, Integer position) {
        session = ScanUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ScanDuplicate dsc = new ScanDuplicate();
        dsc.setDuplicateScan(dupScan);
        dsc.setScanerName(scanerName);
        dsc.setLaminName(laminName);
        dsc.setScanCode(scanerCode);
        session.save(dsc);
        session.getTransaction().commit();
        session.close();
    }

    public Scan findScanByScanCode(String scanCode) {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from ScanDuplicate where scanCode = :scanCode");
        query.setParameter("scanCode", scanCode);
        List<Scan> scan = query.list();
        session.close();
        if (!scan.isEmpty()) {
            return scan.get(0);
        }
        return null;
    }

}

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

/**
 *
 * @author Mudrik
 */
public class ScanDAO {

    private Session session;

    public void createNewScanRec(String scanerName, String laminName, String scanerCode, Integer position) {
        session = ScanUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Scan sc = new Scan();
        sc.setScanCode(scanerCode);
        sc.setScanerName(scanerName);
        sc.setPanelPosition(position);
        sc.setLaminName(laminName);
        session.save(sc);
        session.getTransaction().commit();
        session.close();
    }

    public Scan findScanByScanCode(String scanCode) {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Scan where scanCode like :scanCode");
        query.setParameter("scanCode", "%" + scanCode + "%");
        List<Scan> scan = query.list();
        session.close();
        if (!scan.isEmpty()) {
            return scan.get(0);
        }
        return null;
    }

    public Scan findScanById(Integer id) {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Scan where id = :id");
        query.setParameter("id", id);
        List<Scan> scan = query.list();
        session.close();
        if (!scan.isEmpty()) {
            return scan.get(0);
        }
        return null;
    }

    public void updateScan(Scan scan) {
        session = ScanUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(scan);
        session.getTransaction().commit();
        session.close();
    }
}

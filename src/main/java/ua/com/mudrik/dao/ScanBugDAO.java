/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.dao;

import org.hibernate.Session;
import ua.com.mudrik.dto.Scan;
import ua.com.mudrik.dto.ScanBug;

/**
 *
 * @author Mudrik
 */
public class ScanBugDAO {

    private Session session;

    public void createNewScanBugRec(Scan currentPanel, Scan leftPanel, Scan rightPanel) {
        session = ScanUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ScanBug scb = new ScanBug();
        scb.setCurrentPanel(currentPanel);
        scb.setLeftPanel(leftPanel);
        scb.setRightPanel(rightPanel);
        session.save(scb);
        session.getTransaction().commit();
        session.close();
    }
}

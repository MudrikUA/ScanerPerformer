/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import ua.com.mudrik.dto.Settings;

/**
 *
 * @author Mudrik
 */
public class SettingsDAO {

    private Session session;

    public List<Settings> getComPortsNameSetting() {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Settings where settingName = :settingName");
        query.setParameter("settingName", "ComList");
        List<Settings> settings = query.list();
        session.close();
        return settings;
    }

    public List<Settings> getComPortsForBugNameSetting() {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Settings where settingName = :settingName");
        query.setParameter("settingName", "ComForBugsList");
        List<Settings> settings = query.list();
        session.close();
        return settings;
    }

    public Settings findSettingByName(String paramName) {
        session = ScanUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Settings where settingName = :settingName");
        query.setParameter("settingName", paramName);
        List<Settings> settings = query.list();
        session.close();
        if (!settings.isEmpty()) {
            return settings.get(0);
        }
        return null;
    }
}

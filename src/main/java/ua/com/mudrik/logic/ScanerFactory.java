/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import org.hibernate.Session;
import ua.com.mudrik.dto.Settings;
import ua.com.mudrik.dao.SettingsDAO;

/**
 *
 * @author Mudrik
 */
public class ScanerFactory {

    private List<ScanerPerformer> scanerPerformerList;

    public ScanerFactory() {
    }

    public void ScanerService() {
        scanerPerformerList = new ArrayList<>();
        SettingsDAO sDao = new SettingsDAO();
        String comPortsListStr = "COM1/COM3";
        Settings baudrateSetting = sDao.findSettingByName("BAUDRATE");
        Settings databitsSetting = sDao.findSettingByName("DATABITS");
        Settings stopbitsSetting = sDao.findSettingByName("STOPBITS");
        Settings paritySetting = sDao.findSettingByName("PARITY");
        int baudrate = baudrateSetting == null ? 115200 : Integer.parseInt(baudrateSetting.getSettingParam());
        int databits = databitsSetting == null ? 8 : Integer.parseInt(databitsSetting.getSettingParam());;
        int stopbits = stopbitsSetting == null ? 1 : Integer.parseInt(stopbitsSetting.getSettingParam());;
        int parity = paritySetting == null ? 0 : Integer.parseInt(paritySetting.getSettingParam());;
        List<Settings> comPortsNameSettings = sDao.getComPortsNameSetting();
        for (Settings comPortsNameSetting : comPortsNameSettings) {
            comPortsListStr = comPortsNameSetting.getSettingParam();
        }
        String[] comPortsList = comPortsListStr.split("/");
        for (String portName : comPortsList) {
            try {
                ScanerPerformer sp = new ScanerPerformer();
                sp.setupComConnection(portName, baudrate, databits, stopbits, parity);
                scanerPerformerList.add(sp);
            } catch (SerialPortException ex) {
                Logger.getLogger(ScanerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<ScanerPerformer> getScanerPerformerList() {
        return scanerPerformerList;
    }

    public void setScanerPerformerList(List<ScanerPerformer> scanerPerformerList) {
        this.scanerPerformerList = scanerPerformerList;
    }

}

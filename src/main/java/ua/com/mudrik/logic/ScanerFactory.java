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
import ua.com.mudrik.dto.Settings;
import ua.com.mudrik.dao.SettingsDAO;

/**
 *
 * @author Mudrik
 */
public class ScanerFactory {

    private List<ScanerPerformer> scanerPerformerList;
    private List<ScanerPerformer> scanerBugPerformerList;
    private int baudrate = 115200;
    private int databits = 8;
    private int stopbits = 1;
    private int parity = 0;

    public ScanerFactory() {
    }

    public void ScanerService() {
        scanerPerformerList = new ArrayList<>();
        scanerBugPerformerList = new ArrayList<>();
        SettingsDAO sDao = new SettingsDAO();
        setupComPortConnectionSettings(sDao);
        setupComConnections(sDao, true);//bug scaner 
        setupComConnections(sDao, false); //usual scaner
    }

    private void setupComPortConnectionSettings(SettingsDAO sDao) {
        Settings baudrateSetting = sDao.findSettingByName("BAUDRATE");
        Settings databitsSetting = sDao.findSettingByName("DATABITS");
        Settings stopbitsSetting = sDao.findSettingByName("STOPBITS");
        Settings paritySetting = sDao.findSettingByName("PARITY");
        baudrate = baudrateSetting == null ? 115200 : Integer.parseInt(baudrateSetting.getSettingParam());
        databits = databitsSetting == null ? 8 : Integer.parseInt(databitsSetting.getSettingParam());
        stopbits = stopbitsSetting == null ? 1 : Integer.parseInt(stopbitsSetting.getSettingParam());
        parity = paritySetting == null ? 0 : Integer.parseInt(paritySetting.getSettingParam());
    }

    private void setupComConnections(SettingsDAO sDao, boolean isScanerForBugs) {
        String comPortsListStr = "COM2";
        List<Settings> comPortsNameSettings = new ArrayList<>();
        if (isScanerForBugs) {
            comPortsNameSettings = sDao.getComPortsForBugNameSetting();
        } else {
            comPortsNameSettings = sDao.getComPortsNameSetting();
        }
        for (Settings comPortsNameSetting : comPortsNameSettings) {
            comPortsListStr = comPortsNameSetting.getSettingParam();
        }
        String[] comPortsList = comPortsListStr.split("/");
        for (String portName : comPortsList) {
            try {
                ScanerPerformer sp = new ScanerPerformer();
                if (isScanerForBugs) {
                    sp.setupComConnection(portName, baudrate, databits, stopbits, parity, true);
                    scanerBugPerformerList.add(sp);
                } else {
                    sp.setupComConnection(portName, baudrate, databits, stopbits, parity, false);
                    scanerPerformerList.add(sp);
                }

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

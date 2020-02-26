/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.logic;

import java.util.HashMap;
import java.util.Map;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ua.com.mudrik.dao.ScanBugDAO;
import ua.com.mudrik.dao.ScanDAO;
import ua.com.mudrik.dao.ScanDuplicateDAO;
import ua.com.mudrik.dao.SettingsDAO;
import ua.com.mudrik.dto.Scan;
import ua.com.mudrik.dto.Settings;

/**
 *
 * @author Mudrik
 */
public class ScanerPerformer {

    private SerialPort serialPort;
    private String serialData;
    private Map<String, Integer> panelPos = new HashMap<String, Integer>();

    public ScanerPerformer() {

    }

    public void setupComConnection(String portName, int baudrate, int databits, int stopbits, int parity, boolean isBugScaner) throws SerialPortException {
        System.out.println("ua.com.mudrik.logic.ScanerPerformer.setupComConnection()");
        serialPort = new SerialPort(portName);
        try {
            serialData = "";
            serialPort.openPort();
            serialPort.setParams(baudrate, databits, stopbits, parity);
            //serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            if (isBugScaner) {
                serialPort.addEventListener(new PortEventListenerForScanerBug());
            } else {
                serialPort.addEventListener(new PortEventListenerForScaner());
            }

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    private String getNameOfComPort(String comPort) {
        SettingsDAO sDao = new SettingsDAO();
        String settingName = comPort + "Name";
        Settings comPortNameSetting = sDao.findSettingByName(settingName);
        if (comPortNameSetting != null) {
            return comPortNameSetting.getSettingParam();
        }
        return comPort;
    }

    private Scan getScanByScanCode(String scanCode) {
        ScanDAO sDao = new ScanDAO();
        Scan scan = sDao.findScanByScanCode(scanCode);
        return scan;
    }

    private Scan getScanById(Integer id) {
        ScanDAO sDao = new ScanDAO();
        Scan scan = sDao.findScanById(id);
        return scan;
    }

    private class PortEventListenerForScaner implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String codeStr = new String(serialPort.readBytes());
                    String comNameStr = serialPort.getPortName();
                    serialData = serialData.concat(codeStr);
                    if (codeStr.contains("\r")) {
                        serialData = serialData.replaceAll("\r", "").replaceAll("\n", "");
                        String laminName = getNameOfComPort(comNameStr);
                        Scan scanDuplicate = getScanByScanCode(serialData);
                        Integer position = panelPos.get(comNameStr);
                        if (position == null) {
                            panelPos.put(comNameStr, 1);
                            position = 1;
                        }
                        if (scanDuplicate != null) {
                            ScanDuplicateDAO scanDuplicateDAO = new ScanDuplicateDAO();
                            scanDuplicateDAO.createNewScanDupRec(scanDuplicate, serialData, laminName, comNameStr, position);
                            serialData = "";
                        } else {
                            ScanDAO sDao = new ScanDAO();
                            sDao.createNewScanRec(comNameStr, laminName, serialData, position);
                            serialData = "";
                            if (position >= 3) {
                                panelPos.replace(comNameStr, 1);
                            } else {
                                position = position + 1;
                                panelPos.replace(comNameStr, position);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    private class PortEventListenerForScanerBug implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                //        && !"".equals(event.getEventValue())) {
                ScanBugDAO sDao = new ScanBugDAO();
                try {
                    String codeStr = new String(serialPort.readBytes());
                    serialData = serialData.concat(codeStr);
                    if (codeStr.contains("\r")) {
                        serialData = serialData.replaceAll("\r", "").replaceAll("\n", "");
                        Scan currentPanel = getScanByScanCode(serialData);
                        if (currentPanel != null) {
                            Scan leftPanel = currentPanel.getId() > 1 ? getScanById(currentPanel.getId() - 1) : null;
                            Scan rightPanel = getScanById(currentPanel.getId() + 1);
                            sDao.createNewScanBugRec(currentPanel, leftPanel, rightPanel);
                        }
                        serialData = "";
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                //ScanUtil.shutdown();
            }
        }
    }
}

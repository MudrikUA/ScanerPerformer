/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.mudrik.logic;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ua.com.mudrik.dao.ScanBugDAO;
import ua.com.mudrik.dao.ScanDAO;

/**
 *
 * @author Mudrik
 */
public class ScanerPerformer {

    private SerialPort serialPort;
    private String serialData;

    public ScanerPerformer() {

    }

    public void setupComConnection(String portName, int baudrate, int databits, int stopbits, int parity, boolean isBugScaner) throws SerialPortException {
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

    private class PortEventListenerForScaner implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            Integer position = 1;
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                //        && !"".equals(event.getEventValue())) {
                ScanDAO sDao = new ScanDAO();
                try {
                    //String codeStr = new String(serialPort.readBytes()).replaceAll("[^\\d.]", "");
                    String codeStr = new String(serialPort.readBytes());
                    String nameStr = serialPort.getPortName();
                    //Long codeInt = Long.parseLong(codeStr);
                    serialData = serialData.concat(codeStr);
                    if (codeStr.contains("\r")) {
                        //serialData = serialData.replaceAll("[^\\d.]", ""); //----------------------------------------------------
                        sDao.createNewScanRec(nameStr, serialData, position);
                        serialData = "";
                        position++;
                        if (position > 3) {
                            position = 1;
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                //ScanUtil.shutdown();
            }
        }
    }

    private class PortEventListenerForScanerBug implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            Integer position = 1;
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                //        && !"".equals(event.getEventValue())) {
                ScanBugDAO sDao = new ScanBugDAO();
                try {
                    //String codeStr = new String(serialPort.readBytes()).replaceAll("[^\\d.]", "");
                    String codeStr = new String(serialPort.readBytes());
                    String nameStr = serialPort.getPortName();
                    //Long codeInt = Long.parseLong(codeStr);
                    serialData = serialData.concat(codeStr);
                    if (codeStr.contains("\r")) {
                        //serialData = serialData.replaceAll("[^\\d.]", ""); //----------------------------------------------------
                        sDao.createNewScanBugRec(nameStr, serialData);
                        serialData = "";
                        position++;
                        if (position > 3) {
                            position = 1;
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                //ScanUtil.shutdown();
            }
        }
    }
}

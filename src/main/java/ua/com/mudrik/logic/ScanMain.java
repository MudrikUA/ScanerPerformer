package ua.com.mudrik.logic;

import java.util.Date;
import java.util.List;
import jssc.SerialPortException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mudrik
 */
public class ScanMain {

    public static void main(String[] args) throws InterruptedException, SerialPortException {
        System.out.println("Start -> " + new Date());
        ScanerFactory sf = new ScanerFactory();
        sf.ScanerService();
        System.out.println("stage2 -> " + new Date());
        List<ScanerPerformer> scanerPerformerList = sf.getScanerPerformerList();
    }

}

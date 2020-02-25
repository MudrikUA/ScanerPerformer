
import java.util.Date;
import java.util.List;
import jssc.SerialPortException;
import ua.com.mudrik.logic.ScanerFactory;
import ua.com.mudrik.logic.ScanerPerformer;

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
//         ScanerPerformer sp1 = new ScanerPerformer();
//         ScanerPerformer sp2 = new ScanerPerformer();
//         ScanerPerformer sp3 = new ScanerPerformer();
//         ScanerPerformer sp4 = new ScanerPerformer();
//         sp1.setupComConnection("COM1");
//         sp2.setupComConnection("COM2");
//         sp3.setupComConnection("COM3");
//         sp4.setupComConnection("COM4");
        
    }

}

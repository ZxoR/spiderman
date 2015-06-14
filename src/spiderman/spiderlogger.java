/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderman;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ZxoR (Yonatan)
 */
public class spiderlogger {

    public static void print(String level, String Text, String CallerInformation) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        System.out.print("["+dateFormat.format(date)+"] [" + level + "] [" + CallerInformation + "]: " + Text);
    }

    public static void println(String level, String Text, String CallerInformation) {
        print(level, Text + "\n", CallerInformation);
    }

}

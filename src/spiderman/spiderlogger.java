/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderman;

import static java.awt.SystemColor.INFO;

/**
 *
 * @author ZxoR (Yonatan)
 */
public class spiderlogger {

    public static void print(String level, String Text, String CallerInformation) {
        System.out.print("[" + level + "] [" + CallerInformation + "]: " + Text);
    }

    public static void println(String level, String Text, String CallerInformation) {
        print(level, Text + "\n", CallerInformation);
    }

}

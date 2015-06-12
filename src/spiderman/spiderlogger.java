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

    public static void print(spiderlogger.LOGLEVEL level, String Text, String CallerInformation) {
        System.out.print("[" + LOGLEVEL.logLevel(level) + "] [" + CallerInformation + "]: " + Text);
    }

    private static class LOGLEVEL {

        public LOGLEVEL() {
            final int LEVEL_INFO = 1;
            final int LEVEL_WARN = 2;
            final int LEVEL_ERROR = 3;
        }

        public static String logLevel(LOGLEVEL loglevel) {
            switch (Integer.parseInt("" + loglevel)) {
                case 1:
                    return "Information";
                case 2:
                    return "Warning";
                case 3:
                    return "ERROR";
                default:
                    return "Warning";
            }
        }
    }
}

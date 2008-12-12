/*
 * Main method. Calls starts the User Interface for the Simulator.
 */

package uk.ac.swan.eg253.rdt;

import java.util.Date;

/**
 *
 * @author Chris Jobling
 */
public class Main {
    private static String banner="Swansea University\n" +
            "School of Engineering\n" +
            "ICCT Programme\n\n" +
            "EG-253 Practical Internet Technology II\n";
    public static final String progname = "Reliable Data Transport Simulator";
    public static final String version = "Version 0.1";
    public static final String copyright =  "Copyright (c) 2008 Swansea University. All Rights Reserved.";
    public static final String license = "Apache 2 License.";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(banner);
        System.out.println();
        System.out.println(progname);
        System.out.println(version);
        System.out.println();
        System.out.println(copyright);
        System.out.println("Source Code Released under the " + license);

        Date now = new Date();
        System.out.println("Simulation stated at: " + now.toLocaleString());

    }

}

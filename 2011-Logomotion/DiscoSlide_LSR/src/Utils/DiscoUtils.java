/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import edu.wpi.first.wpilibj.*;

/**
 * DiscoUtils
 * Used in many places with in these Discobots Classes
 * @author Allen
 */
public class DiscoUtils {

    /**
     * Change this field to false to turn off the debug mode of the code
     * Will not allow debugPrintln to print anything to the terminal
     */
    public static final boolean DEBUG = true;
    
    /**
     * Change this field to false to turn off error messages from custom code
     * Will not allow errorPrintln to print anything to the terminal
     */
    public static final boolean ERROR = true;

    /**
     * Only prints lines when the BEBUG value is set to true
     * @param s - string that you want to print to the terminal
     */
    public static void debugPrintln(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    /**
     * Only prints lines when the BEBUG value is set to true
     * @param s - string that you want to print to the terminal
     */
    public static void errorPrintln(String s) {
        if (ERROR) {
            System.out.println(s);
        }
    }

    /**
     * printToLCD allows for easy printing to the driver station LCD
     * @param lcd - the instance of the DriverStationLCD that you would like to print to
     * @param line - the line number that you would like to use 0 is the Main6 line and 1-5 are user lines 2-6
     * @param s - the string that you wish to print to the lcd screen
     */
    public static void printToLCD(DriverStationLCD lcd, int line, String s) {
        switch (line) {
            case (0): {
                lcd.println(DriverStationLCD.Line.kMain6, 1, s);
            }
            case (1): {
                lcd.println(DriverStationLCD.Line.kUser2, 1, s);
            }
            case (2): {
                lcd.println(DriverStationLCD.Line.kUser3, 1, s);
            }
            case (3): {
                lcd.println(DriverStationLCD.Line.kUser4, 1, s);
            }
            case (4): {
                lcd.println(DriverStationLCD.Line.kUser5, 1, s);
            }
            case (5): {
                lcd.println(DriverStationLCD.Line.kUser6, 1, s);
            }
            defualt:
            {
                debugPrintln("Print to DriverStation LCD on a line that does not exist");
            }
        }
    }

    /**
     * limits the input from 1.0 to -1.0
     * @param num the number that you with to be limited
     * @return the limited the value
     */
    public static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }

    /**
     * Converts a Raw PWM value (0-255) to a  speed value (-1 to 1)
     * @param num the number that you wish to convert should be a raw pwm value 0- 255
     * @return the rescaled value
     */
    public static double rawToSpeed(double num) {
        return ((num / 255) - .5) * 2;
    }

    /**
     * Converts a raw PWM value (0-255) to a position value (0 - 1)
     * @param num - the number that is to be converted should be a raw pwm value 0 -255
     * @return the rescaled value
     */
    public static double rawToPosition(double num) {
        return (num / 255);
    }

    /**
     * Converts a raw PWM value (0-255) to an Angle value (-90 to 90)
     * @param num - the number that is to be converted should be a raw pwm value 0 -255
     * @return the rescaled value
     */
    public static double rawToAngle(double num) {
        return (num * (90 / 255));
    }

    public static double deadband(double input, double deadband){
        /*if (input > 0 && input < deadband){
            return (input - deadband) / (1-deadband);
        } else (input < 0 && input > -deadband){
            return (input + deadband) / (-1 + deadband);
        } else {
            return input
        }*/

        return input;
    }
}

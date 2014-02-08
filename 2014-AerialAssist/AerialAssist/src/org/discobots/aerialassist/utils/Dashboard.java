/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Not Nolan
 */
public class Dashboard {
    public static void init() {
        
    }
    public static void update() {
        // Robot
        
        // Subsystem: Drivetrain
                
        // Subsystem: Collector
        
        //Subsystem: Compressor
        SmartDashboard.putBoolean("Tanks full", CommandBase.compressorSub.getPressure());
        
        // Subsystem: Shared
    }
}

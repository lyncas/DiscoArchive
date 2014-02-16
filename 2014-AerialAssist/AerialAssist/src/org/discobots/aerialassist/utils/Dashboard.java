package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.*;

/**
 *
 * @author nolan
 */
public class Dashboard {
    public static void init() {
        driveSub = CommandBase.drivetrainSub;
        compSub = CommandBase.compressorSub;
        rollSub = CommandBase.rollerSub;
        cataSub = CommandBase.pneumatapultSub;
    }
    static Drivetrain driveSub;
    static CompressorSub compSub;
    static RollerSub rollSub;
    static Pneumatapult cataSub;
    
    public static void update() {
        SmartDashboard.putData(driveSub);
        SmartDashboard.putData(compSub);
        SmartDashboard.putData(rollSub);
        SmartDashboard.putData(cataSub);
        
        // Drive
        SmartDashboard.putNumber("Drive Angle", driveSub.getGyroAngle());
        SmartDashboard.putString("Drive Shift Mode", driveSub.getShiftPosition() ? "Traction" : "Mecanum");
        
        // Compressor
        SmartDashboard.putBoolean("Compressor Compressor", compSub.isEnabled());
        SmartDashboard.putBoolean("Compressor Max Presure", compSub.isFull());
        
        // Roller
        SmartDashboard.putBoolean("Intake Extended", rollSub.isExtended());
        SmartDashboard.putNumber("Intake Speed", rollSub.getIntakeSpeed());
        
        // Pneumatapult
        SmartDashboard.putBoolean("Catapult Down?", cataSub.isDown());
        SmartDashboard.putNumber("Catapult Mode", cataSub.getMode());
                
    }
}

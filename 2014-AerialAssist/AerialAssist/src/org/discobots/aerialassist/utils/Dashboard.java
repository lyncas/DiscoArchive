package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.commands.drive.ControlLEDState;
import org.discobots.aerialassist.subsystems.*;

public class Dashboard {

    public static void init() {
        driveSub = CommandBase.drivetrainSub;
        compSub = CommandBase.compressorSub;
        intaSub = CommandBase.rollerSub;
        launSub = CommandBase.pneumatapultSub;
    }
    static Drivetrain driveSub;
    static CompressorSub compSub;
    static Intake intaSub;
    static Launcher launSub;

    public static void update() {
        SmartDashboard.putData(driveSub);
        SmartDashboard.putData(compSub);
        SmartDashboard.putData(intaSub);
        SmartDashboard.putData(launSub);

        // Drive
        SmartDashboard.putNumber("Encoder Distance", driveSub.getEncoderForwardDistance());
        SmartDashboard.putNumber("Ultrasonic Shooter", driveSub.getUltrasonicShooterAverageValue());
        SmartDashboard.putNumber("Ultrasonic Intake", driveSub.getUltrasonicIntakeAverageValue());
        SmartDashboard.putBoolean("Use MiniCims", driveSub.getMiniCimUsage());

        // Compressor
        SmartDashboard.putBoolean("Compressor ON when PSI < 120", compSub.isEnabled());
        SmartDashboard.putBoolean("Tanks Full at 120PSI", compSub.isFull());
        SmartDashboard.putBoolean("Optimal Shooting PSI", compSub.getPressurePSIInt() > 70);
        SmartDashboard.putNumber("PSI", compSub.getPressurePSIDou());

        // Intake
        SmartDashboard.putBoolean("Intake Extended", intaSub.isExtended());
        SmartDashboard.putNumber("Intake Speed", intaSub.getIntakeSpeed());

        // Launcher
        SmartDashboard.putBoolean("Launcher Down?", launSub.isDown());
        
        // Misc
        SmartDashboard.putBoolean("Ready to Shoot?", ControlLEDState.readyToShoot);

    }
}

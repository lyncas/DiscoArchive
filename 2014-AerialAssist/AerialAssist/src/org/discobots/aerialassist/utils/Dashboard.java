package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.*;

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
        SmartDashboard.putString("Drive Shift Mode", driveSub.getShiftPosition() ? "Traction" : "Omniwheel");
        SmartDashboard.putNumber("PID Controller Output", driveSub.getAngleControllerOutput());
        SmartDashboard.putBoolean("Field Centric Enabled?", driveSub.isFieldCentricEnabled());
        SmartDashboard.putNumber("Encoder Forward Distance", driveSub.getEncoderForwardDistance());
        //SmartDashboard.putNumber("Encoder Sideway Distance", driveSub.getEncoderSidewayDistance());
        SmartDashboard.putBoolean("Use MiniCims?", driveSub.getMiniCimUsage());

        // Compressor
        SmartDashboard.putBoolean("Compressor On?", compSub.isEnabled());
        SmartDashboard.putBoolean("Compressor Max Pressure?", compSub.isFull());
        SmartDashboard.putBoolean("Can Run Pneumatics?", compSub.canRunPneumatics());
        SmartDashboard.putNumber("Pressure", compSub.getPressurePSI());
        SmartDashboard.putBoolean("Optimal Shooting PSI?", compSub.getPressurePSI() > 60);
        SmartDashboard.putNumber("Pressure Sensor Voltage", compSub.getPressureVoltage());

        // Roller
        SmartDashboard.putBoolean("Intake Extended", rollSub.isExtended());
        SmartDashboard.putNumber("Intake Speed", rollSub.getIntakeSpeed());

        // Pneumatapult
        SmartDashboard.putBoolean("Catapult Down?", cataSub.isDown());
        SmartDashboard.putNumber("Catapult Mode", cataSub.getMode());

    }
}

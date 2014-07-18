package org.discobots.aerialassist.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.OI;
import org.discobots.aerialassist.subsystems.CompressorSub;
import org.discobots.aerialassist.subsystems.Drivetrain;
import org.discobots.aerialassist.subsystems.Intake;
import org.discobots.aerialassist.subsystems.Launcher;

/** 
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system.
 * 
 */

public abstract class CommandBase extends Command {

    public static OI oi;

    public static CompressorSub compressorSub = new CompressorSub();
    public static Drivetrain drivetrainSub = new Drivetrain();
    public static Intake rollerSub = new Intake();
    public static Launcher pneumatapultSub = new Launcher();

    public static void init() {
        oi = new OI();

        SmartDashboard.putData(drivetrainSub);
        SmartDashboard.putData(compressorSub);
        SmartDashboard.putData(rollerSub);
        SmartDashboard.putData(pneumatapultSub);

    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}

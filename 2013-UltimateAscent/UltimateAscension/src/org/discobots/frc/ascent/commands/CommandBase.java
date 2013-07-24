package org.discobots.frc.ascent.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.discobots.frc.ascent.OI;
import org.discobots.frc.ascent.subsystems.Collector;
import org.discobots.frc.ascent.subsystems.Drivetrain;
import org.discobots.frc.ascent.subsystems.Compressor;
import org.discobots.frc.ascent.subsystems.Shooter;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Compressor compressorSubsystem;
    public static Drivetrain drivetrainSubsystem;
    public static Shooter shooterSubsystem;
    public static Collector collectorSubsystem;
    
    public static void init() {
        compressorSubsystem = new Compressor();
        drivetrainSubsystem = new Drivetrain();
        shooterSubsystem = new Shooter();
        collectorSubsystem=new Collector();
        oi = new OI();
        
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
    
}

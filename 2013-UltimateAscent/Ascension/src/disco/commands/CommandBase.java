package disco.commands;

import disco.OI;
import disco.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drivetrain drivetrain;
    public static Shooter shooter;
    public static CompressorSub compressor;
    public static Shifter shifter;
    public static Arduino arduino;

    public static void init() {
        drivetrain = new Drivetrain();
        System.out.println("Drivetrain initialization successful");
        shooter = new Shooter();
        System.out.println("Shooter initialization successful");
        compressor = new CompressorSub();
        System.out.println("Compressor initialization successful");
        shifter = new Shifter();
        System.out.println("Shifter initialization successful");
        arduino = new Arduino();
        System.out.println("Arduino initialization successful");
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        System.out.println("OI initialization successful");

        // Show what command your subsystem is running on the SmartDashboard
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}

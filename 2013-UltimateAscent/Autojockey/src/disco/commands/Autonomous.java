/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands;

import disco.commands.shooter.AutoShoot;
import disco.commands.shooter.ShooterToggle;
import disco.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Doris
 */
public class Autonomous extends CommandGroup {
    public static final int MODE_SAFE = 0;
    public static final int MODE_RISKY = 1;
    public static final int MODE_DANGEROUS = 2;
    public static final int MODE_NOTHING = 3;
    
    public Autonomous(int mode) {
        CommandBase.shooter.setSetpoint(CommandBase.shooter.getSetpoint());
        CommandBase.shooter.setMode(Shooter.MODE_BANG);
        switch (mode) {
            case Autonomous.MODE_RISKY:
                addSequential(new ShooterToggle());
//                addSequential(new WaitCommand(500));
                addSequential(new AutoShoot(5,2000,1000));
                addSequential(new ShooterToggle());
                System.out.println("Risky Auton");
                break;
            case Autonomous.MODE_DANGEROUS:// DON'T USE DANGEROUS
                addSequential(new ShooterToggle());
                addSequential(new AutoShoot(5,500));
                addSequential(new ShooterToggle());
                //addSequential(new DriveDistance(-90));
                System.out.println("Dangerous Auton");
                break;
            case Autonomous.MODE_SAFE:
                addSequential(new ShooterToggle());
                addSequential(new AutoShoot(5,3000,2000));
                addSequential(new ShooterToggle());
                System.out.println("Safe Auton");
                break;
            case Autonomous.MODE_NOTHING:
                break;
        }
    }
    protected void initialize() {

    }
    protected void execute() {
        
    }
    protected boolean isFinished() {
        return false;
    }
    protected void end() {
    }
    protected void interrupted() {
        end();
    }
}

package org.discobots.aerialassist.commands;

import org.discobots.aerialassist.commands.upperbody.AutonomousIntake;
import org.discobots.aerialassist.commands.drive.AutonomousTankDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.upperbody.FireLauncher;
import org.discobots.aerialassist.commands.upperbody.Intake;
import org.discobots.aerialassist.commands.upperbody.ToggleArm;

public class Autonomous extends CommandGroup {

    long time;

    public Autonomous(int mode) {
        autonomousMode3Init();

        /*switch (mode) {
         case 1:
         autonomousMode1Init();
         break;
         case 2:
         autonomousMode2Init();
         break;
         case 3:
         autonomousMode3Init();
         break;
         case 4:
         autonomousMode4Init();// MOVE FOWARD then ONE BALL: HIGH
         break;
         default:
         autonomousMode5Init();
         break;
         }*/
    }

    private void autonomousMode0Init() {
        // Do Nothing
    }

    private void autonomousMode1Init() { // TWO BALL: HIGH HIGH     This has been tested and works.
        addSequential(new ToggleCompressor());
        addSequential(new ToggleArm(false)); // arm down
        addSequential(new AutonomousTankDrive(-0.6, -0.6, 0.4 * Intake.IN, 2000)); // intake while moving to hold ball
        addSequential(new FireLauncher(true, 2));
        addSequential(new WaitCommand(1));
        addSequential(new AutonomousIntake(1 * Intake.IN, 2000));
        addSequential(new FireLauncher(true, 2));
    }

    private void autonomousMode2Init() { // TWO BALL: TRUSS 1PT
        addSequential(new ToggleCompressor());
        addSequential(new ToggleArm(false)); // arm down
        addSequential(new WaitCommand(.5));
        addSequential(new AutonomousTankDrive(-.4, -.4, 500));
        addSequential(new WaitCommand(.5));
        //       addSequential(new AutonomousIntake(.1 * Intake.IN, 100));
        addSequential(new FireLauncher(true, 3));
//        addParallel(new AutonomousIntake(.2 * Intake.IN, 1500));
        addSequential(new WaitCommand(.5));
        addSequential(new AutonomousTankDrive(.4, .4, 500));
//        addSequential(new WaitCommand(.5));
        addSequential(new AutonomousIntake(1 * Intake.IN, 2000));
        addSequential(new AutonomousTankDrive(-0.65, -0.6, 2500));
        addSequential(new FireLauncher(true, 1));
        addSequential(new WaitCommand(5));
    }

    private void autonomousMode3Init() { // TWO BALL: Double Truss
        addSequential(new ToggleCompressor());
        addSequential(new ToggleArm(false)); // arm down
        addSequential(new WaitCommand(1.5));
//        addSequential(new AutonomousTankDrive(-.6, -.65, 500));
//        addSequential(new WaitCommand(.5));
        addSequential(new FireLauncher(true, 3));
        addSequential(new WaitCommand(.5));
//        addSequential(new AutonomousTankDrive(.6, .65, 250));
//        addSequential(new AutonomousIntake(1 * Intake.IN, 2000));
//        addSequential(new AutonomousTankDrive(-0.6, -0.65, 250));
//        addSequential(new FirePneumatapult(true, 3));
        addSequential(new AutonomousTankDrive(-0.6, -0.65, 1000));
        addSequential(new WaitCommand(5));
    }

    private void autonomousMode4Init() { // ONE BALL: HIGH
        addSequential(new ToggleCompressor());
        addSequential(new ToggleArm(false)); // arm down
        addSequential(new WaitCommand(3.0));
        addSequential(new FireLauncher(true, 3));
        addSequential(new AutonomousTankDrive(-.6, -.6, 1000));
        addSequential(new WaitCommand(.5));
        // addSequential(new AutonomousIntake(.1 * Intake.IN, 100));

    }

    private void autonomousMode5Init() { // ONLY DRIVING
        //On Practice Bot the max speed (so far) is .7.  .6 is the default/what existed before
        double speed = SmartDashboard.getNumber("Speed");
        addSequential(new ToggleCompressor());
        addSequential(new ToggleArm(false)); // arm down
        //addSequential(new AutonomousIntake(0.3 * Intake.IN, 1500)); // intake
        addSequential(new AutonomousTankDrive(-6, -6.5/*, 0.4 * Intake.IN*/, 1000)); // intake while moving to hold ball
        //addSequential(new AutonomousTankDrive(0.8, 0.8, 0.5 * Intake.IN, 250));
        //addSequential(new AutonomousTankDrive(-0.8, -0.8, 250));
//        addSequential(new WaitCommand(0.5));
//        addSequential(new AutonomousIntake(1 * Intake.IN, 2000));
//        addSequential(new AutonomousTankDrive(0.8, 0.8, 0.5 * Intake.IN, 250));
//        addSequential(new AutonomousTankDrive(-0.8, -0.8, 250));
    }

    public void initialize() {
        super.initialize();
        time = System.currentTimeMillis();
    }

    public boolean isFinished() {
        if (super.isFinished()) {
            System.out.println("[Debug] Autonomous completed in " + (System.currentTimeMillis() - this.time) + " ms.");
            return true;
        } else {
            return false;
        }
    }
}

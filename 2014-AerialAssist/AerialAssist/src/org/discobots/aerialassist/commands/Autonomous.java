/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import org.discobots.aerialassist.commands.SetPneumaticsRunnable;
import org.discobots.aerialassist.commands.upperbody.AutonomousIntake;
import org.discobots.aerialassist.commands.drive.AutonomousTankDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.discobots.aerialassist.commands.upperbody.FirePneumatapult;
import org.discobots.aerialassist.commands.upperbody.Intake;
import org.discobots.aerialassist.commands.upperbody.ToggleArm;
import org.discobots.aerialassist.utils.BetterRobotDrive;

/**
 *
 * @author Seth
 */
public class Autonomous extends CommandGroup {
    
        long time;
    public Autonomous(int mode) {
        time = System.currentTimeMillis();
        switch (mode) {
            case 0:
                autonomousMode0Init();
                break;
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
                autonomousMode4Init();
                break;
        }
    }
    
    private void autonomousMode0Init() {
        // Do Nothing
    }
    
    private void autonomousMode1Init() { // TWO BALL
        addSequential(new ToggleCompressor());
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new ToggleArm(false));
        addSequential(new AutonomousTankDrive(-0.6, -0.6, 0.5*Intake.IN, 3000));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new AutonomousTankDrive(1, 1, 300));
        addSequential(new WaitCommand(1.500));
        addSequential(new AutonomousIntake(1*Intake.IN, 2500));
        addSequential(new FirePneumatapult(true, 2));
        addSequential(new AutonomousTankDrive(-1, -1, 300));
        addSequential(new AutonomousTankDrive(1, 1, 300));
    }
    
    private void autonomousMode2Init() { // NO GOAL : NO BALL : MOVE ONLY
        addSequential(new ToggleCompressor());
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new AutonomousTankDrive(-1.0, -1.0, 3000));
    }
    
    private void autonomousMode3Init() { // LOW/HIGH GOAL : THREE BALL : MOVE FIRST THEN FIRE
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new AutonomousIntake(Intake.IN, 1000));
        addParallel(new ToggleArm(false));
        addParallel(new AutonomousTankDrive(0.5, 0.5, 1000));
        addSequential(new AutonomousTankDrive(-1, -1, Intake.IN * 0.5, 1500)); // Move to the wall with three ball, let ball 1 enter the low goal
        addSequential(new WaitCommand(2));
        addSequential(new AutonomousTankDrive(0.5, 0.5, 750)); // Move back a tiny bit
        addSequential(new ToggleArm(true));
        addSequential(new AutonomousTankDrive(0, 0.6, 300)); // Move to the left a little
        addSequential(new ToggleArm(false));
//        addSequential(new WaitCommand(1.5)); // Move to the left a little
        addSequential(new FirePneumatapult(true, 2)); // Fire ball 2
        //addSequential(new AutonomousTankDrive(-0.25, -0.25, 450)); // Lower the catapult
        //addSequential(new AutonomousTankDrive(0.25, 0.25, 450)); // Lower the catapult
        addSequential(new WaitCommand(0.5));
        addSequential(new AutonomousIntake(Intake.IN, 1500)); // Intake ball 3
        addSequential(new WaitCommand(1));
        addSequential(new FirePneumatapult(true, 2)); // Fire ball 3
    }
    
    private void autonomousMode4Init() { // LOW GOAL : ONE BALL
        addSequential(new AutonomousTankDrive(1.0, 1.0, 4000));
        addSequential(new SetPneumaticsRunnable(true));
        addSequential(new ToggleArm(true));
        addSequential(new AutonomousIntake(Intake.OUT, 2500));
    }
    
    public void end() {
        System.out.println(System.currentTimeMillis() - time);
    }
}

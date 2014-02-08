/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.Drivetrain;

/**
 *
 * @author Dylan
 */
public class SwitchDrive extends CommandBase {

    private boolean newMode;
    private boolean useOwnData;

    public SwitchDrive(boolean check) {
        requires(drivetrainSub);
        newMode = check;
        useOwnData=false;
    }

    public SwitchDrive() {
        requires(drivetrainSub);
        newMode=!drivetrainSub.getDriveState();
        useOwnData=true;
    }

    protected void initialize() {
        System.out.print("newMode = ");
        if(newMode)
            System.out.println("TRACTION");
        else
            System.out.println("MECANUM");

        if(!useOwnData) {
            if (newMode == Drivetrain.TRACTION) { // (true) //!drivetrainSub.getDriveState()
                System.out.println("shifting to traction");
                drivetrainSub.shiftTraction();
                new TankDrive().start();
            } else {// (newMode == Drivetrain.TRACTION) { // (false)
                System.out.println("shifting to mecanum");
                drivetrainSub.shiftMecanum();
                new MecanumDrive().start();
            } 
        } else {
            if (!drivetrainSub.getDriveState()) { // (true) //!drivetrainSub.getDriveState()
                System.out.println("shifting to traction");
                drivetrainSub.shiftTraction();
                new TankDrive().start();
            } else {// (newMode == Drivetrain.TRACTION) { // (false)
                System.out.println("shifting to mecanum");
                drivetrainSub.shiftMecanum();
                new MecanumDrive().start();
            }
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

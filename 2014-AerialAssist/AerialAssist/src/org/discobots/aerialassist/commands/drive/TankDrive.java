/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class TankDrive extends CommandBase {

    public TankDrive() {
        requires(drivetrainSub);

    }

    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
        System.out.println("Traction wheels engaged\n");
    }

    protected void execute() {
        double l = oi.getRawAnalogStickALY();
        double r = -oi.getRawAnalogStickARY();

        drivetrainSub.tankDrive(l, r);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSub.tankDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }
}

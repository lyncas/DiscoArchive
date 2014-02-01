/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import robt.HW;
import robt.commands.arm.VariIntake;

/**
 *
 * @author Sam Dietrich / Team ORyon
 */
public class Intake extends Subsystem {

    Talon intakemotor = new Talon(HW.intakslot, HW.intakechannel);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new VariIntake());
    }

    public void setIntake(double power) {
        intakemotor.set(power);
    }
}

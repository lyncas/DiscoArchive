/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import robot.HW;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Dylan
 */
public class RMotor extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar rmotor;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public RMotor(){
        rmotor=new Jaguar(1,5);
    }
    public void set(double power){
        rmotor.set(power);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Dylan
 */
public class MotorTestSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
       
    Jaguar rmotor;
    Jaguar rmotor1;
    double power;
    int time;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public MotorTestSubsystem(){
        rmotor=new Jaguar(1,5);
        rmotor1=new Jaguar(1,10);
    }
    public void set(double power1){
        rmotor.set(power1);
        rmotor1.set(power1);
    }
    public void pIncrement(double power2){
        if((power+power2<=1)&&(power+power2>0))
            power+=power2;
        else if(power+power2>1)
            power=1;
        else if(power+power2<=0)
            power=0;        
    }
    public void tIncrement(int time1){
        time+=time1;
    }
    public int gett(){
        return time;
    }
     public double getp(){
        return power;
    }
}

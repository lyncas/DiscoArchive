/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import disco.commands.CommandBase;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.util.Timer;
import java.util.TimerTask;

public class SendPIDInfo {
    NetworkTable IT=NetworkTable.getTable("PIDInfo");
    
    TimerTask sender=new TimerTask(){
        public void run() {
            IT.putNumber("setpoint", CommandBase.shooter.getSetpoint());
            IT.putNumber("RPM", CommandBase.shooter.getFrontRPM());
            IT.putNumber("PWM", CommandBase.shooter.getFrontPower());
        }
        
    };
    Timer repeater=new Timer();
    
    public void start(){
        repeater.scheduleAtFixedRate(sender, 0, 10);
    }
    
}

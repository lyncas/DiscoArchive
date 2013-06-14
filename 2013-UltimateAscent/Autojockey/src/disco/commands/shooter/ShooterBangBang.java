/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.shooter;

import disco.commands.CommandBase;


public class ShooterBangBang extends CommandBase {
    private boolean done;
    private Control control=new Control();
    private boolean onTarget=false;

    public ShooterBangBang() {
        // Use requires() here to declare subsystem dependencies
        requires(shooter);
        requires(compressor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	done=false;
	compressor.set(false);
        control.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //on target if within 3%
        onTarget=Math.abs( (shooter.getFrontRPM()-shooter.getSetpoint()) / shooter.getSetpoint() )<0.03;
        shooter.setOnTarget(onTarget);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        
	shooter.setPower(0);
	compressor.set(true);
        shooter.setOnTarget(false);
        done=true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
    
    class Control extends Thread{
    public Control(){}
    
    public void run(){
        while(!done){
                if(shooter.getFrontRPM()>shooter.getSetpoint()){
                shooter.setFrontPower(0);
            }
            else{
                shooter.setFrontPower(1);
            }
            if(shooter.getBackRPM()>shooter.getSetpoint()-shooter.difference){
                shooter.setBackPower(0);
            } 
            else{
                shooter.setBackPower(1);
            }
            try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    System.out.println("Shooter BangBang thread dum!");
                }
            }
    }
}
}
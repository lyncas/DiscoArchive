/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands;

import disco.utils.GamePad;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Use this class to tune any PID!
 * just tell it which PIDController, and watch it work like magic!
 */
public class PIDTune extends CommandBase {
    private PIDController c;
    private String name;
    private double P=0,I=0,D=0,F=0;
    private double inc=0.001;
    private GamePad gp;

    public PIDTune(PIDController control, String name) {
	c=control;
	this.name=name;
	gp=(GamePad) oi.getJoy2();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	P=c.getP();
	I=c.getI();
	D=c.getD();
	F=c.getF();
	inc=0.001;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	//PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
	if(gp.getRawButton(gp.BUMPER_R)){
	    P+=inc;
	}
	else if(gp.getRawButton(gp.TRIGGER_R)){
	    P-=inc;
	}

	//IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
	if(gp.getRawButton(gp.BUMPER_L)){
	    I+=inc;
	}
	else if(gp.getRawButton(gp.TRIGGER_L)){
	    I-=inc;
	}

	//DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
	if(gp.getRawAxis(gp.DPAD_Y)>0.7){
	    D+=inc;
	}
	else if(gp.getRawAxis(gp.DPAD_Y)<-0.7){
	    D-=inc;
	}

	//FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
	if(gp.getRawAxis(gp.DPAD_X)>0.7){
	    F+=inc;
	}
	else if(gp.getRawAxis(gp.DPAD_X)<-0.7){
	    F-=inc;
	}

	//incincincincincincincincincincinc
	if(gp.getRawButton(gp.BTN_START)){
	    inc*=10;
	}
	else if(gp.getRawButton(gp.BTN_BACK)){
	    inc/=10;
	}

	c.setPID(P, I, D, F);
	SmartDashboard.putString("TuningName", name);
	SmartDashboard.putString("P", strRound6(P));
	SmartDashboard.putString("I", strRound6(I));
	SmartDashboard.putString("D", strRound6(D));
	SmartDashboard.putString("F", strRound6(F));
	SmartDashboard.putString("inc", strRound6(inc));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }


    //Truncate to 6 decimal places and format as a string
    private String strRound6(double in){
	if(in==0){
	    return String.valueOf(0);
	}
	in*=1000000;
	String mid = String.valueOf((int) in);
	int len=mid.length();
	return mid.substring(0, len-6)+"."+mid.substring(len-6, len);
    }
}

package robt.utils;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class GamePad extends Joystick {
    /**
     * Select the mode with the switch on the back of the joystick.
     * X uses the triggers as an axis but cannot use the vertical Dpad.
     * D allows the vertical Dpad with the triggers as buttons.
    */
    public static final int MODE_X=1, MODE_D=2;
    public static final int STICK_L=1, STICK_R=2;
    //For some reason, the base code only supports 6 axes and 12 buttons. Sigh. It's better than normal joysticks.
    private static final int m_numAxes=6;
    private static final int m_numButtons=12;

    private int thisMode;
    //Stores which axes are revered on this gamepad. Note axis k is element k-1
    private double[] reversedAxes=new double[m_numAxes];
    //Declare the axes and buttons.
    public int  AXIS_LX,  AXIS_LY,
                AXIS_RX,  AXIS_RY,
                DPAD_X,   DPAD_Y,
                TRIGGER;
    public int	BTN_X,	    BTN_Y,
                BTN_A,	    BTN_B,
		BUMPER_L,   BUMPER_R,
                TRIGGER_L,  TRIGGER_R,
                CLICK_L,    CLICK_R,
		BTN_BACK,   BTN_START;
    //Constants for using the digital pad as buttons
    public static final int DPAD_X_R=96,	DPAD_X_L=97,
			    DPAD_Y_U=98,	DPAD_Y_D=99;


    /**People will use this constructor...
     *
     * @param port which number joystick is this
     * @param mode what does the switch on the back of the controller say? D is suggested.
     */
    public GamePad(int port, int mode){
        this(port,m_numAxes,m_numButtons);
        switch(mode){
            case MODE_X: {XmodeInit(); thisMode=MODE_X; break;}
            case MODE_D: {DmodeInit(); thisMode=MODE_D; break;}
	    default: {DmodeInit(); thisMode=MODE_D; break;}
        }
	System.out.println(this.getClass().getName()+" initialized in mode "+(thisMode==MODE_D ? "D":"X"));
    }
    //...and this one is for us to make the joystick stuff work
    protected GamePad(int port, int numAxisTypes, int numButtonTypes){
        super(port,numAxisTypes,numButtonTypes);
	for(int i=0;i<m_numAxes;i++){
	    reversedAxes[i]=1.0;
	}
    }

    /**When the switch on the back of the controller says 'X'
     *
     */
    private void XmodeInit(){
        AXIS_LX=1;  AXIS_LY=2;
        AXIS_RX=4;  AXIS_RY=5;
        DPAD_X=6;   DPAD_Y=0;
        TRIGGER=3;

        BTN_X=3;        BTN_Y=4;
        BTN_A=1;        BTN_B=2;
        BUMPER_L=5;     BUMPER_R=6;
        TRIGGER_L=0;    TRIGGER_R=0;
        CLICK_L=9;      CLICK_R=10;
	BTN_BACK=7;     BTN_START=8;

        setAxisReversed(AXIS_LY,true);
        setAxisReversed(AXIS_RY,true);
        setAxisReversed(TRIGGER,true);
    }

    /**When the switch on the back of the controller says 'D'
     *
     */
    private void DmodeInit(){
        AXIS_LX=1;  AXIS_LY=2;
        AXIS_RX=3;  AXIS_RY=4;
        DPAD_X=5;   DPAD_Y=6;
        TRIGGER=0;

        BTN_X=1;        BTN_Y=4;
        BTN_A=2;        BTN_B=3;
        BUMPER_L=5;     BUMPER_R=6;
        TRIGGER_L=7;    TRIGGER_R=8;
        CLICK_L=11;     CLICK_R=12;
	BTN_BACK=9;     BTN_START=10;

        setAxisReversed(AXIS_LY,true);
        setAxisReversed(AXIS_RY,true);
        setAxisReversed(DPAD_Y,true);
    }

    /**Reverse axes if they are not in the expected direction.
     * This is preconfigured when you choose the mode.
     */
    public void setAxisReversed(int axis, boolean reversed){
	reversedAxes[axis-1]=(reversed ? -1.0 : 1.0);
    }

    public int getNumAxes(){
        return m_numAxes;
    }
    public int getNumButtons(){
        return m_numButtons;
    }
    public int getMode(){
	return thisMode;
    }


    public double getLX(){
        return getRawAxis(AXIS_LX)*reversedAxes[AXIS_LX-1];
    }
    public double getLY(){
        return getRawAxis(AXIS_LY)*reversedAxes[AXIS_LY-1];
    }
    public double getRX(){
        return getRawAxis(AXIS_RX)*reversedAxes[AXIS_RX-1];
    }
    public double getRY(){
        return getRawAxis(AXIS_RY)*reversedAxes[AXIS_RY-1];
    }
    public double getDpadX(){
        return getRawAxis(DPAD_X)*reversedAxes[DPAD_X-1];
    }
    /**Returns 0 if the axis is not supported in your mode.
     *
     */
    public double getDpadY(){
        if(DPAD_Y==0) {
	    return 0;//axis does not exist
	}
        else {
	    return getRawAxis(DPAD_Y)*reversedAxes[DPAD_Y - 1];
	}
    }
    /**Returns 0 if the axis is not supported in your mode.
     *
     */
    public double getTriggerAxis(){
        if(TRIGGER==0) {
	    return getRawAxis(TRIGGER_R)-getRawAxis(TRIGGER_L);
	}
        else {
	    return getRawAxis(TRIGGER)*reversedAxes[TRIGGER - 1];
	}
    }

    public double getMagnitude(int stick){
	double result=0;
	switch(stick){
	    case STICK_L:
		result=Math.sqrt(MathUtils.pow(getLX(), 2) + MathUtils.pow(getLY(), 2));
		break;
	    case STICK_R:
		result=Math.sqrt(MathUtils.pow(getRX(), 2) + MathUtils.pow(getRY(), 2));
		break;
	    default:
		throw new IllegalArgumentException();
	}
	return result;
    }

    public double getDirectionRadians(int stick) {
	double result=0;
	switch(stick){
	    case STICK_L:
		result=MathUtils.atan2(getLX(), -getLY());
		break;
	    case STICK_R:
		result=MathUtils.atan2(getRX(), -getRY());
		break;
	    default:
		throw new IllegalArgumentException();
	}
	return result;
    }

    public double getDirectionDegrees(int stick) {
        return Math.toDegrees(getDirectionRadians(stick));
    }




    /**
     * This lets us use axes as just another button.
     * Give it the button number like it was a normal button.
     */
    public static class AxisButton extends Button{
	public static final double kDefaultThreshold=0.7;
	private double m_threshold;
	private GamePad m_gp;
	private int m_buttonAxis;

	public AxisButton(GamePad gp, int buttonAxis){
	    this(gp,buttonAxis,kDefaultThreshold);
	}

	public AxisButton(GamePad gp, int buttonAxis, double threshold){
	    m_gp=gp;
	    m_buttonAxis=buttonAxis;
	    m_threshold=threshold;
	}

	public void setThreshold(double threshold){
	    m_threshold=threshold;
	}

	public boolean get(){
	    //Axes specific to X mode
	    if(m_gp.getMode()==GamePad.MODE_X){
		if(m_buttonAxis==m_gp.TRIGGER_R){
		    return m_gp.getTriggerAxis()>m_threshold;
		}
		if(m_buttonAxis==m_gp.TRIGGER_L){
		    return m_gp.getTriggerAxis()<-m_threshold;
		}
		if(m_buttonAxis==GamePad.DPAD_Y_U || m_buttonAxis==GamePad.DPAD_Y_D){
		    throw new IllegalArgumentException("The Y Dpad is not available in X mode.");
		}
	    }
	    //Axes specific to D mode
	    if(m_gp.getMode()==GamePad.MODE_D){
		if(m_buttonAxis==GamePad.DPAD_Y_U){
		    return m_gp.getDpadY()>m_threshold;
		}
		if(m_buttonAxis==GamePad.DPAD_Y_D){
		    return m_gp.getDpadY()<-m_threshold;
		}
	    }
	    //Axes in all modes
		if(m_buttonAxis==GamePad.DPAD_X_R){
		    return m_gp.getDpadX()>m_threshold;
		}
		if(m_buttonAxis==GamePad.DPAD_X_L){
		    return m_gp.getDpadX()<-m_threshold;
		}
	    //It's just a regular button. Why did the developer use this class? Silly developer! (ok, for compatibility, wise guy)
	    return m_gp.getRawButton(m_buttonAxis);
	}

    }
}



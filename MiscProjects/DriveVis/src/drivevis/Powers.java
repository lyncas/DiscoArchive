package drivevis;

/**
 *
 * @author sam
 */
public class Powers {
    private double l,r;

    public Powers(double left, double right){
	l=left;
	r=right;
    }

    public double getLeftPower(){
	return l;
    }
    public double getRightPower(){
	return r;
    }
}

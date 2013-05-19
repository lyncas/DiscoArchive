package drivevis;

/**
 *
 * @author sam
 */
public interface DriveMode {

    public Powers calcLR(double y, double x);

    public String Name();

}

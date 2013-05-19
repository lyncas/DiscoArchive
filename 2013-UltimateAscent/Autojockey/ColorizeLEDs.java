
import disco.commands.CommandBase;

public class ColorizeLEDs extends CommandBase {
    int type, r, g, b;
    boolean done = false;
    public ColorizeLEDs(int type) {
        requires(arduino);
        this.type = type;
        r = -1; 
        g = -1; 
        b = -1;
    }
    public ColorizeLEDs(int type, int r, int g, int b) {
        requires(arduino);
        this.type = type;
        this.r = r; 
        this.g = g; 
        this.b = b;
    }

    protected void initialize() {
    }

    protected void execute() {
        if (r == -1) {
            arduino.updateLEDS(type);
        } else {
            arduino.updateLEDS(type, r, g, b);
        }
        done = true;
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

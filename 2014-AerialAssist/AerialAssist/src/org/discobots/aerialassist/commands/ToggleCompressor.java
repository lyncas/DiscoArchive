package org.discobots.aerialassist.commands;

/**
 *
 * @author Nolan Shah
 */
public class ToggleCompressor extends CommandBase {

    public ToggleCompressor() {
        requires(compressorSub);
    }

    protected void initialize() {
        if (compressorSub.isEnabled()) {
            compressorSub.off();
            System.out.println("Compressor off");
        } else {
            compressorSub.on();
            System.out.println("Compressor on");
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

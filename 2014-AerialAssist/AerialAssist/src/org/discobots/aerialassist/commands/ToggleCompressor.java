package org.discobots.aerialassist.commands;

public class ToggleCompressor extends CommandBase {

    public ToggleCompressor() {
        requires(compressorSub);
    }

    protected void initialize() {
        if (compressorSub.isEnabled()) {
            compressorSub.off();
            System.out.println("Compressor toggled always OFF");
        } else {
            compressorSub.on();
            System.out.println("Compressor toggled ON when PSI < 120");
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

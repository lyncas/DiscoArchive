package org.discobots.frc.ascent.commands.drive;

import java.util.Random;
import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public final class SwagArcade extends CommandBase {
    
    private double swagError;
    
    private SwagArcade() {
        requires(drivetrainSubsystem);
        swagError = 0.0;
    }
    protected void initialize() {
        
    }

    protected void execute() {
        double incremError = (new Random().nextDouble() - 0.3d) / 20.0d;
        swagError += incremError;
        
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSubsystem.arcadeDrive(0, 0, false, false);
    }

    protected void interrupted() {
        end();
    }
}

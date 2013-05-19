package lejos.robotics.localization;

import lejos.robotics.navigation.Pose;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Provides the coordinate and heading info via a Pose object.
 * @author NXJ Team
 *
 */
public interface PoseProvider {
	
	public Pose getPose();
    
	public void setPose(Pose aPose);		
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;

/**
 *
 * @author thomasklau
 */
public class VisionTracking extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //Camera Constants for Axis Cameras
    public static final int Y_IMAGE_RES = 480;		//X Image resolution in pixels, should be 120, 240 or 480
    public static final double VIEW_ANGLE = 49;		//Axis M1013
    //final double VIEW_ANGLE = 41.7;		//Axis 206 camera
    //final double VIEW_ANGLE = 37.4;  //Axis M1011 camera

    //Score limits used for target identification
    public static final int  RECTANGULARITY_LIMIT = 40;
    public static final int ASPECT_RATIO_LIMIT = 55;

    //Score limits used for hot target determination
    public static final int TAPE_WIDTH_LIMIT = 50;
    public static final int  VERTICAL_SCORE_LIMIT = 50;
    public static final int LR_SCORE_LIMIT = 50;

    //Minimum area of particles to be considered
    public static final int AREA_MINIMUM = 150;

    //Maximum number of particles to process
    public static final int MAX_PARTICLES = 8;
    
    Preferences prefs;
    
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation


    public static final int hi = 0;
    public static final int hf = 255;
    public static final int si = 0;
    public static final int sf = 40;
    public static final int vi = 217;
    public static final int vf = 255;
    
    
    public VisionTracking()
    {
        camera = AxisCamera.getInstance();  // get an instance of the camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, AREA_MINIMUM, 65535, false);
        //type, area min, area max, outside range?
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public AxisCamera getCamera()
    {
        return camera;
    }
    
    public CriteriaCollection getCC()
    {
        return cc;
    }
}

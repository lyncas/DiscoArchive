/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import disco.commands.CommandBase;
import lejos.robotics.RangeReading;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeature;

/**
 *
 * @author Developer
 */
public class FeatureReporter implements FeatureListener {

    public void featureDetected(Feature feature, FeatureDetector detector) {
        if (feature instanceof RangeFeature) {
            RangeFeature Rfeature = (RangeFeature) feature;
            //Pose rPose = Rfeature.getPose(); IT DONT WORK. STUPID LEJOS
            Pose rPose;
            try{
               rPose=CommandBase.drivetrain.getPoseProvider().getPose();
            }
            catch(NullPointerException e){
                rPose=new Pose(0,0,90);
            }
            //System.out.println(rPose);
            RangeReading rr = Rfeature.getRangeReading();
            double dist = rr.getRange();
            double angle = rr.getAngle() + rPose.getHeading();
            double angleRad = Math.toRadians(angle);
            double x = rPose.getX() + dist * Math.cos(angleRad);
            double y = rPose.getY() + dist * Math.sin(angleRad);
//            System.out.println("Range: " + dist
//                    + "\nAngle: " + angle
//                    + "\nRobot: x=" + rPose.getX() + " y=" + rPose.getY());
            System.out.println("Feature detected at :" + Utilitate.roundHundredths(x) + " , " + Utilitate.roundHundredths(y));
        } else {
            System.out.println("Non-range feature detected. You might want to check that.");
        }
    }
}

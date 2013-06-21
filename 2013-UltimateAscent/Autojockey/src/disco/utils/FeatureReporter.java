/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import java.lejosawt.geom.Point2D;
import lejos.geom.Point;
import lejos.robotics.RangeReading;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeature;
import lejos.robotics.objectdetection.RangeFeatureDetector;

/**
 *
 * @author Developer
 */
public class FeatureReporter implements FeatureListener {

    public void featureDetected(Feature feature, FeatureDetector detector) {
        if (feature instanceof RangeFeature) {
            RangeFeature Rfeature = (RangeFeature) feature;
            Pose rPose = Rfeature.getPose();
            RangeReading rr = Rfeature.getRangeReading();
            double dist = rr.getRange();
            double angle = rr.getAngle() + rPose.getHeading();
            double x=rPose.getX() + dist * Math.cos(Math.toRadians(angle));
            double y= rPose.getY() + dist * Math.sin(Math.toRadians(angle));

            System.out.println("Feature detected at :" + Utilitate.roundHundredths(x)+" , "+Utilitate.roundHundredths(y));
        }
        else{
            System.out.println("Non-range feature detected. You might want to check that.");
        }
    }


}

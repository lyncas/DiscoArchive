/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper;

import java.util.Arrays;
import lejos.robotics.RangeReading;
import lejos.robotics.RangeReadings;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.objectdetection.RangeFeature;
import lejos.robotics.pathfinding.Path;

/**
 *
 * @author Sam Dietrich
 *
 * Like serialization, but painful.
 * Usual characters to split:
 *	"[: ]" between data pieces of an object
 *	"~" in ArrayLists
 */
public class StringParser {

    public static Pose getPose(String p) {
        String[] split = p.split("[: ]");
        float x = Float.parseFloat(split[1].trim());
        float y = Float.parseFloat(split[3].trim());
        float h = Float.parseFloat(split[5].trim());
        return new Pose(x, y, h);
    }

    public static Path getPath(String path) {
        String[] split = path.split("~");
        Path p = new Path();
        for (String w : split) {
            try {
                p.add(getWaypoint(w));
            } catch (ArrayIndexOutOfBoundsException e) {
                //yay!
            }
        }
        return p;
    }

    public static RangeFeature getFeature(String f){
	String[] split=f.split("|");
	Pose p=getPose(split[0].trim());
	RangeReadings rrs=getRangeReadings(split[1].trim());
	return new RangeFeature(rrs,p);
    }

    public static RangeReadings getRangeReadings(String rs){
	String[] split = rs.split("~");
        RangeReadings rrs = new RangeReadings(0);
        for (String r : split) {
            try {
                rrs.add(getRangeReading(r));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                //yay!
		//It could be that the RangeReadings says our reading is invalid.
		//It shouldn't, but just in case.
            }
        }
        return rrs;
    }

    public static RangeReading getRangeReading(String r){
	String[] split = r.split("[: ]");
	float angle=Float.parseFloat(split[1].trim());
	float range=Float.parseFloat(split[3].trim());
	return new RangeReading(angle,range);
    }


    public static Waypoint getWaypoint(String w) {
        String[] split = w.split("[: ]");
        float x = Float.parseFloat(split[1].trim());
        float y = Float.parseFloat(split[3].trim());
        float h = Float.parseFloat(split[5].trim());
        return new Waypoint(x, y, h);
    }
}

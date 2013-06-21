/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper;

import java.util.Arrays;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

/**
 *
 * @author Sam Dietrich
 *
 * Like serialization, but painful.
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

    public static Waypoint getWaypoint(String w) {
        String[] split = w.split("[: ]");
        float x = Float.parseFloat(split[1].trim());
        float y = Float.parseFloat(split[3].trim());
        float h = Float.parseFloat(split[5].trim());
        return new Waypoint(x, y, h);
    }
}

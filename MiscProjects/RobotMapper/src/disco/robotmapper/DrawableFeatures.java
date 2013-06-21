/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper;

import disco.robotmapper.leJOSExtended.WorldRangeReading;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import lejos.robotics.RangeReading;
import lejos.robotics.RangeReadings;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.RangeFeature;

/**
 *
 * @author Sam Dietrich
 *
 * Class to contain all leJOS RangeReadings and RangeFeatures collected by this
 * application, but with a fixed position in the world coordinate system.
 */
public class DrawableFeatures implements drawable {

    protected Color c = Color.BLACK;
    protected ArrayList<WorldRangeReading> readings = new ArrayList();
    protected int radius = 20;

    public DrawableFeatures(RangeFeature feature, Color c, int radius) {
	this.c = c;
	this.radius = radius;
	addFeature(feature);
    }

    public DrawableFeatures(Color c, int radius) {
	this(null, c, radius);
    }

    /*
     * Adds the points of a range feature to the points drawn by this object
     */
    public void addFeature(RangeFeature rf) {
	if (rf != null) {
	    Pose rPose = rf.getPose();
	    addRangeReadings(rf.getRangeReadings(), rPose);
	}
    }

    public void addRangeReadings(RangeReadings rrs, Pose p) {
	for (RangeReading rr : rrs) {
	    addRangeReading(rr, p);
	}
    }

    public void addRangeReading(RangeReading rr, Pose p) {
	readings.add(new WorldRangeReading(rr, p));
    }

    @Override
    public void draw(Graphics g, int window_centerX, int window_centerY) {
	if (readings.size() > 0) {
	    Graphics2D g2 = (Graphics2D) g;
	    Color old_color = g2.getColor();
	    Stroke old_stroke = g2.getStroke();

	    g2.setColor(c);

	    for (WorldRangeReading r : readings) {
		Point2D p = r.getLocation();
		g.drawOval((int) (window_centerX + p.getX()), (int) (window_centerY - p.getY()), radius * 2, radius * 2);
	    }

	    g2.setStroke(old_stroke);
	    g2.setColor(old_color);
	}
    }
}

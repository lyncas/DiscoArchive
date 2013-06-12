/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package discobots.smartdashboard.robotmapper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

/**
 *
 * @author Sam Dietrich
 *
 * Path is currently very inefficient, being sent each time. We should wait until path is changed and then update that.
 */
public class DrawablePath implements drawable {

    private Path p;
    private Path2D lineDrawing;
    //path color
    private Color c;

    public DrawablePath(Path p, Color c) {
	this.p = p;
    }

    public void setPath(Path p) {
	this.p = p;
    }

    @Override
    public void draw(Graphics g, int window_centerX, int window_centerY) {
	if (p != null) {
	    Graphics2D g2 = (Graphics2D) g;
	    Color old_color = g2.getColor();
	    Stroke old_stroke=g2.getStroke();

	    g2.setColor(c);
	    lineDrawing = new Path2D.Float(Path2D.WIND_EVEN_ODD);
	    lineDrawing.moveTo(window_centerX + p.get(0).getX(), window_centerY - p.get(0).getY());

	    for (Waypoint w : p) {
		lineDrawing.lineTo(window_centerX + w.getX(), window_centerY - w.getY());
	    }

	    g2.setStroke(new BasicStroke(2.0f));
	    g2.draw(lineDrawing);

	    g2.setStroke(old_stroke);
	    g2.setColor(old_color);
	}
    }
}

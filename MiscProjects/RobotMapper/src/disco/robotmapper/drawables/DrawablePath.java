/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper.drawables;

import disco.robotmapper.ViewHelper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	this.c=c;
    }

    public void setPath(Path p) {
	this.p = p;
    }

    @Override
    public void draw(Graphics g,ViewHelper v) {
	Point origin=v.getOrigin();
	if (p != null && p.size()>0) {
	    Graphics2D g2 = (Graphics2D) g;
	    Color old_color = g2.getColor();
	    Stroke old_stroke=g2.getStroke();

	    g2.setColor(c);
	    lineDrawing = new Path2D.Float(Path2D.WIND_EVEN_ODD);
	    lineDrawing.moveTo(origin.getX() + p.get(0).getX()*v.X_PixelsPerUnit(), origin.getY()- p.get(0).getY()*v.Y_PixelsPerUnit());

	    for (Waypoint w : p) {
		lineDrawing.lineTo(origin.getX() + w.getX()*v.X_PixelsPerUnit(), origin.getY() - w.getY()*v.Y_PixelsPerUnit());
	    }

	    g2.setStroke(new BasicStroke(2.0f));
	    g2.draw(lineDrawing);

	    g2.setStroke(old_stroke);
	    g2.setColor(old_color);
	}
    }
}

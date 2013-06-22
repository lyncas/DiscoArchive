/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper.drawables;

import disco.robotmapper.ViewHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Sam Dietrich
 */
public class Ruler implements drawable {

    Color xc, yc;

    public Ruler(Color xc, Color yc) {
	this.xc = xc;
	this.yc = yc;
    }

    /*
     * Try to plot 10 points each way
     */
    @Override
    public void draw(Graphics g, ViewHelper v) {
	Color old = g.getColor();
	Point origin=v.getOrigin();
	Dimension dim=v.getDimension();

	//Plot X values along screen bottom
	g.setColor(xc);//dark green
	for (double x = v.getX_min(); x <= v.getX_max(); x += (v.getX_max()-v.getX_min())/10) {
	    g.drawString(String.valueOf(x), (int) (origin.getX()+x*v.X_PixelsPerUnit()), dim.height);
	}


	//Plot Y values along screen left
	g.setColor(yc);//dark blue
	for (double y = v.getY_min(); y <= v.getY_max(); y += (v.getY_max()-v.getY_min())/10) {
	    g.drawString(String.valueOf(y), 0, (int) (origin.getY()-y*v.Y_PixelsPerUnit()));
	}
	g.setColor(old);
    }
}

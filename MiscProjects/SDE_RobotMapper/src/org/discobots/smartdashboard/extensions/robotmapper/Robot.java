/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.smartdashboard.extensions.robotmapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Sam Dietrich
 */
public class Robot {

    private int robotWidth = 20;
    private int robotLength = 20;
    Rectangle robotRect;
    Polygon arrow;

    public Robot(int width, int length) {
	robotWidth = width > 0 ? width : 20;
	robotLength = length > 0 ? length : 20;
	robotRect=new Rectangle(0,0,robotWidth,robotLength);
	arrow=new Polygon();
	arrow.addPoint(robotWidth/2, robotLength);
	arrow.addPoint(robotWidth/2, 0);
	arrow.addPoint(robotWidth/4, robotLength/4);
	arrow.addPoint(robotWidth/2, 0);
	arrow.addPoint(robotWidth*3/4, robotLength/4);
    }

    public void drawRobot(Graphics g, double centerX, double centerY, double heading) {
	Graphics2D g2=(Graphics2D)g;
	AffineTransform robotTrans=new AffineTransform();
	//May have to do these in the other order. Rotate robot components and translate them to robot location.
	robotTrans.rotate(heading-Math.PI/2);
	robotTrans.translate(centerX-robotWidth/2, centerY-robotLength/2);
	Rectangle robotRectT=(Rectangle) robotTrans.createTransformedShape(robotRect);
	g2.setColor(Color.CYAN);
	g2.fill(robotRectT);
	g2.setColor(Color.YELLOW);
	g2.fillArc((int) (centerX - robotWidth / 2), (int) (centerY - robotLength / 2), robotWidth, robotLength / 2, (int) (heading - 10), (int) (heading + 10));
    }
    public void drawDisabledRobot(Graphics g, double centerX, double centerY) {
        g.setColor(Color.RED);
	g.fillRect((int) (centerX - robotWidth / 2), (int) (centerY - robotLength / 2), robotWidth, robotLength / 2);

    }
}

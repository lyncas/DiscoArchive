/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.smartdashboard.extensions.robotmapper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import lejos.robotics.navigation.Pose;

/**
 *
 * @author Sam Dietrich, Nolan Shah
 */
public class Robot {

    private int robotWidth = 20;
    private int robotLength = 20;
    private Pose pose=new Pose(0,0,0);

    Rectangle robotRect;
    Path2D arrow;
    Path2D disabledX;

    public Robot(int width, int length) {
	//robot
	robotWidth = width > 0 ? width : 20;
	robotLength = length > 0 ? length : 20;
	robotRect=new Rectangle(0,0,robotWidth,robotLength);
	//direction arrow
	arrow=new Path2D.Float(Path2D.WIND_EVEN_ODD) {};
	arrow.moveTo(robotWidth/2, robotLength);
	arrow.lineTo(robotWidth/2, 0);
	arrow.lineTo(robotWidth/4, robotLength/4);
	arrow.lineTo(robotWidth*3/4, robotLength/4);
	arrow.lineTo(robotWidth/2, 0);
	//the X
	disabledX=new Path2D.Float(Path2D.WIND_EVEN_ODD);
	disabledX.moveTo(0,0);
	disabledX.lineTo(robotWidth, robotLength);
	disabledX.moveTo(robotWidth, 0);
	disabledX.lineTo(0, robotLength);
    }

    public void setPose(Pose p){
	pose=new Pose(p.getX(),p.getY(),p.getHeading());
    }

    public Pose getPose(){
	return new Pose(pose.getX(),pose.getY(),pose.getHeading());
    }

    public void drawRobot(Graphics g,int window_centerX,int window_centerY,boolean disabled) {
	//Compute where to draw robot, assuming window cetner is world coordinate (0,0)
	double centerX=pose.getX()+window_centerX;
	double centerY=window_centerY-pose.getY();
	double heading=pose.getHeading();
	Graphics2D g2=(Graphics2D)g;

	AffineTransform robotTrans=new AffineTransform();
	//Last things first
	robotTrans.translate(centerX-robotWidth/2, centerY-robotLength/2);
	//move back to 0rigin
	robotTrans.translate(robotWidth/2, robotLength/2);
	//rotate to correct angle
	robotTrans.rotate(-(Math.toRadians(heading) - Math.PI/2));
	//move so we rotate around center
	robotTrans.translate(-robotWidth/2, -robotLength/2);
	//Create properly transformed robot and draw it
	g2.setColor(Color.CYAN);
	g2.fill(robotTrans.createTransformedShape(robotRect));
	g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2.0f));

	g2.draw(robotTrans.createTransformedShape(arrow));
	if(disabled){
	    g2.setColor(Color.RED);
	    g2.setStroke(new BasicStroke(5.0f));
	    g2.draw(robotTrans.createTransformedShape(disabledX));
	}
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper.drawables;

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
public class Robot implements drawable{

    //state
    private int robotWidth = 20;
    private int robotLength = 20;
    private Pose pose=new Pose(0,0,0);
    private boolean disabled=true;

    //drawable objects
    Rectangle robotRect;
    Path2D arrow;
    Path2D disabledX;

    public Robot(int width, int length) {
	//robot
	robotWidth = width > 0 ? width : 20;
	robotLength = length > 0 ? length : 20;
	robotRect=new Rectangle(-robotWidth/2,-robotLength/2,robotWidth,robotLength);
	//direction arrow
	arrow=new Path2D.Float(Path2D.WIND_EVEN_ODD) {};
	arrow.moveTo(0, robotLength/2);
	arrow.lineTo(0, -robotLength/2);
	arrow.lineTo(-robotWidth/4, -robotLength/4);
	arrow.lineTo(robotWidth/4, -robotLength/4);
	arrow.lineTo(0, -robotLength/2);
	//the X
	disabledX=new Path2D.Float(Path2D.WIND_EVEN_ODD);
	disabledX.moveTo(-robotWidth/2,-robotLength/2);
	disabledX.lineTo(robotWidth/2, robotLength/2);
	disabledX.moveTo(robotWidth/2, -robotLength/2);
	disabledX.lineTo(-robotWidth/2, robotLength/2);
    }

    public void setPose(Pose p){
	pose=new Pose(p.getX(),p.getY(),p.getHeading());
    }

    public void setDisabled(boolean disabled){
	this.disabled=disabled;
    }

    public Pose getPose(){
	return new Pose(pose.getX(),pose.getY(),pose.getHeading());
    }

    @Override
    public void draw(Graphics g,int window_centerX,int window_centerY) {
	//Compute where to draw robot, assuming window cetner is world coordinate (0,0)
	double centerX=window_centerX+pose.getX();
	double centerY=window_centerY-pose.getY();
	double heading=pose.getHeading();
	Graphics2D g2=(Graphics2D)g;

	AffineTransform robotTrans=new AffineTransform();
	//Last things first
	//Move to screen location
	robotTrans.translate(centerX, centerY);
	//rotate to correct angle
	robotTrans.rotate(-(Math.toRadians(heading) - Math.PI/2));
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

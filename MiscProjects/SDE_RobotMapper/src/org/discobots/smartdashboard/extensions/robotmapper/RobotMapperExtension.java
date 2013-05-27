package org.discobots.smartdashboard.extensions.robotmapper;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author Nolan Shah, Sam Dietrich
 */
public class RobotMapperExtension extends StaticWidget {

    private NetworkTable table;
    private final String RobotMapperTableLocation = "LocationInformation";
    private double robotPositionX = 0.0, robotPositionY = 0.0;
    private double heading = 0.0;
    private int robotWidth = 0, robotLength = 0;
    private boolean connected = false;
    private dataReader reader;
    private Robot robot;

    public void setValue(Object o) {
    }

    @Override
    public void init() {
	setPreferredSize(new Dimension(200, 200));
	try {
	    table.setIPAddress("127.0.0.1"); // Temporary for testing. Should be 10.25.87.whatever
	    NetworkTable.getTable(RobotMapperTableLocation);
	    robotPositionX = table.getNumber("xPosition");
	    robotPositionY = table.getNumber("yPosition");
	    heading = table.getNumber("heading");
	    robotWidth = (int) table.getNumber("robot_Width", 20);
	    robotLength = (int) table.getNumber("robot_Length", 20);
	    connected = true;
	    reader = new dataReader();
	    reader.start();
	} catch (Exception e) {
	    e.printStackTrace();
	    connected = false;
	}
	robot = new Robot(robotWidth, robotLength);
    }

    @Override
    public void propertyChanged(Property p) {
    }

    @Override
    public void paint(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, getSize().width, getSize().height);
	g.setColor(Color.GREEN);
	g.drawString("" + robotPositionX + " " + robotPositionY + " " + heading + " " + connected, 0, 20);
	if (!connected) {
	    g.setColor(Color.ORANGE);
	    g.drawString("EXCEPTION A", 0, 40);
	}
	try {
	    int panelCenterX = getSize().width / 2;
	    int panelCenterY = getSize().height / 2;
	    double scaleWidth = 1.0;
	    double scaleHeight = 1.0;
	    double robotCenterX = (double) panelCenterX + robotPositionX * scaleWidth;
	    double robotCenterY = (double) panelCenterY - robotPositionY * scaleHeight;
	    robot.drawRobot(g, robotCenterX, robotCenterY, heading, !connected);
	} catch (Exception e) {
	    g.setColor(Color.ORANGE);
	    g.drawString("EXCEPTION B", 0, 80);
	}
    }

    private class dataReader extends Thread {

	@Override
	public void run() {
	    while (true) {
		try {
		    //update robot pose
		    robotPositionX = table.getNumber("xPosition", robotPositionX);
		    robotPositionY = table.getNumber("yPosition", robotPositionY);
		    heading = table.getNumber("heading", heading);
		    connected = true;
		} catch (Exception e) {
		    connected = false;
		}
		repaint();
		//wait a while to do it again.
		try {
		    sleep(200);
		} catch (InterruptedException ex) {
		}
	    }
	}
    }
}
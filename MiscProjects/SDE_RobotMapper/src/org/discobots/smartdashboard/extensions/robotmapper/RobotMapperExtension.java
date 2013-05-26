package org.discobots.smartdashboard.extensions.robotmapper;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.MultiProperty;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author Nolan Shah, Sam Dietrich
 */
public class RobotMapperExtension extends StaticWidget {

    private NetworkTable table;
    private static final String CONST_RobotMapperTableLocation = "LocationInformation";
    private double robotPositionX = 0.0, robotPositionY = 0.0;
    private double heading = 0.0;
    private int robotWidth = 0, robotLength = 0;
    private boolean connected = false;

    public void setValue(Object o) {
    }

    public void init() {
	setPreferredSize(new Dimension(200, 200));
	try {
	    table.setIPAddress("127.0.0.1");
	    table.getTable(RobotMapperExtension.CONST_RobotMapperTableLocation);
	    robotPositionX = table.getNumber("xPosition");
	    robotPositionY = table.getNumber("yPosition");
	    heading = table.getNumber("heading");
	    robotWidth = (int) table.getNumber("robot_Width", 20);
	    robotLength = (int) table.getNumber("robot_Length", 20);
	    connected = true;
	} catch (Exception e) {
	    e.printStackTrace();
	    connected = false;
	}
    }

    public void propertyChanged(Property p) {
    }

    public void paint(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, getSize().width, getSize().height);
	g.setColor(Color.GREEN);
	g.drawString("" + robotPositionX + " " + robotPositionY + " " + heading + " " + connected, 0, 20);
	try {
	    try {
		robotPositionX = table.getNumber("xPosition");
		robotPositionY = table.getNumber("yPosition");
		heading = table.getNumber("heading");
		connected = true;
	    } catch (Exception e) {
		connected = false;
		g.setColor(Color.ORANGE);
		g.drawString("EXCEPTION A", 0, 40);
	    }
	    int panelCenterX = getSize().width / 2;
	    int panelCenterY = getSize().height / 2;
	    if (!connected) { // <-------------------------------------------------- REMOVE ! WHEN TESTING WITH NETWORK TABLES WORKING. ADD IT WHEN TESTING CODE W/O NETWORK TABLES
		double scaleWidth = 1.0;
		double scaleHeight = 1.0;
		double robotCenterX = (double) panelCenterX + robotPositionX * scaleWidth;
		double robotCenterY = (double) panelCenterY - robotPositionY * scaleHeight;
		g.setColor(Color.CYAN);
		g.fillRect((int) (robotCenterX - robotWidth / 2 * scaleWidth), (int) (robotCenterY - robotLength / 2 * scaleHeight), (int) (robotWidth * scaleWidth), (int) (robotLength / 2 * scaleHeight));
		g.setColor(Color.YELLOW);
		g.fillArc((int) robotCenterX - 20, (int) robotCenterY - 20, (int) (40.0 * scaleWidth), (int) (40.0 * scaleHeight), (int) (heading - 10), (int) (heading + 10));
	    } else {
		g.setColor(Color.BLUE);
		g.drawOval(panelCenterX - 20, panelCenterY - 20, 40, 40);
	    }
	} catch (Exception e) {
	    g.setColor(Color.ORANGE);
	    g.drawString("EXCEPTION B", 0, 40);
	}
    }
}
package org.discobots.smartdashboard.extensions.robotmapper;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.MultiProperty;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.awt.Color;
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
    private boolean connected = false;
    
    public void setValue(Object o) {
    }

    public void init() {      
        table.getTable(RobotMapperExtension.CONST_RobotMapperTableLocation);
        try {
            robotPositionX = table.getNumber("xPosition");
            robotPositionY = table.getNumber("yPosition");
            heading = table.getNumber("heading");
            connected = true;
        } catch (TableKeyNotDefinedException e) {
            connected = false;
        }
    }

    public void propertyChanged(Property p) {
        
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        try {
            robotPositionX = table.getNumber("xPosition");
            robotPositionY = table.getNumber("yPosition");
            heading = table.getNumber("heading");
            connected = true;
        } catch (TableKeyNotDefinedException e) {
            connected = false;
        }
        int panelCenterX = this.getWidth() / 2;
        int panelCenterY = this.getHeight() / 2;
        double scaleWidth = this.getWidth() * 0.05;
        double scaleHeight = this.getHeight() * 0.05;
        if (connected) {
            double robotCenterX = (double) panelCenterX - robotPositionX * scaleWidth;
            double robotCenterY = (double) panelCenterY + robotPositionY * scaleHeight;
            g.setColor(Color.BLUE);
            g.drawOval((int) robotCenterX - 20, (int) robotCenterY - 20, (int) (40.0 * scaleWidth), (int) (40.0 * scaleHeight));
            g.drawArc((int) robotCenterX - 20, (int) robotCenterY - 20, (int) (40.0 * scaleWidth), (int) (40.0 * scaleHeight), (int) (heading - 10), (int) (heading + 10));
        } else {
            g.drawOval(panelCenterX - 20, panelCenterY - 20, 40, 40);
        }
        
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.smartdashboard.extensions.robotmapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Sam Dietrich
 */
public class Robot {

    private double robotWidth = 20;
    private double robotLength = 20;

    public Robot(int width, int length) {
	robotWidth = width > 0 ? width : 20;
	robotLength = length > 0 ? length : 20;
    }

    public void drawRobot(Graphics g, double centerX, double centerY, double heading) {
	Graphics2D g2=(Graphics2D)g;
	g.setColor(Color.CYAN);
	g.fillRect((int) (centerX - robotWidth / 2), (int) (centerY - robotLength / 2), (int) (robotWidth), (int) (robotLength / 2));
	g.setColor(Color.YELLOW);
	g.fillArc((int) (centerX - robotWidth / 2), (int) (centerY - robotLength / 2), (int) (robotWidth), (int) (robotLength / 2), (int) (heading - 10), (int) (heading + 10));
    }
    public void drawDisabledRobot(Graphics g, double centerX, double centerY) {
        g.setColor(Color.RED);
	g.fillRect((int) (centerX - robotWidth / 2), (int) (centerY - robotLength / 2), (int) (robotWidth), (int) (robotLength / 2));

    }
}

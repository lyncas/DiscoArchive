package discobots.smartdashboard.robotmapper;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

/**
 *
 * @author Nolan Shah, Sam Dietrich
 */
public class RobotMapperExtension extends JPanel {

    private int size;
    private NetworkTable table;
    private final String RobotMapperTableLocation = "LocationInformation",
	    KEY_POSE = "robot_Pose",
	    KEY_ROBOT_WIDTH = "robot_Width",
	    KEY_ROBOT_LENGTH = "robot_Length",
	    KEY_PATH = "robot_path";
    private volatile Pose robotPose = new Pose(0, 0, 90);
    private int robotWidth = 0, robotLength = 0;
    private boolean connected = false;
    private volatile Path robotPath = null;
    private DataReaderThread reader = new DataReaderThread();
    //drawables
    private Robot robot;
    private DrawablePath path_drawing;

    public RobotMapperExtension(int size) {
	this.size = size;
	init();
	robotPath = new Path();
	robotPath.add(new Waypoint(0, 0));
	robotPath.add(new Waypoint(50, 50));
	robotPath.add(new Waypoint(75, 50));
    }

    public void init() {
	setPreferredSize(new Dimension(size, size));
	setBackground(Color.WHITE);
	try {
	    table = NetworkTable.getTable(RobotMapperTableLocation);
	    robotPose = (Pose) table.getValue(KEY_POSE, new Pose(0, 0, 90));
	    robotWidth = (int) table.getNumber(KEY_ROBOT_WIDTH, 20);
	    robotLength = (int) table.getNumber(KEY_ROBOT_LENGTH, 30);
	    connected = true;
	} catch (Exception e) {
	    e.printStackTrace();
	    connected = false;
	}
	reader.start();
	robot = new Robot(robotWidth, robotLength);
	path_drawing = new DrawablePath(robotPath, Color.ORANGE);
    }

    @Override
    public void paint(Graphics g) {
	g.setColor(Color.LIGHT_GRAY);
	g.fillRect(0, 0, getSize().width, getSize().height);
	if (!connected) {
	    g.setColor(Color.ORANGE);
	    g.drawString("NOT CONNECTED", 0, 20);
	}
	int panelCenterX = getSize().width / 2;
	int panelCenterY = getSize().height / 2;

	robot.setPose(robotPose);
	robot.setDisabled(!connected);
	robot.draw(g, panelCenterX, panelCenterY);

	if (robotPath != null) {
	    path_drawing.setPath(robotPath);
	    path_drawing.draw(g, panelCenterX, panelCenterY);
	}

	drawRuler(g);
    }

    /*
     * Draws one number every 50 px
     * Assumes screen center is (0,0)
     */
    protected void drawRuler(Graphics g) {
	int width = getWidth();
	int height = getHeight();
	int X_AXIS = height / 2;//y location of x axis
	int Y_AXIS = width / 2;//x location of y axis

	//Plot X values along screen bottom
	g.setColor(new Color(0,70, 0));//dark green
	for (int x = 0; x < width; x += 50) {
	    g.drawString(String.valueOf(x - Y_AXIS), x, height);
	}

	//Plot Y values along screen left
	g.setColor(new Color(0, 0, 150));//dark blue
	for (int y = 0; y < height; y += 50) {
	    g.drawString(String.valueOf(X_AXIS - y), 0, y);
	}

    }

    private class DataReaderThread extends Thread {

	@Override
	public void run() {
	    while (true) {
		//update data
		try {
		    robotPose = (Pose) table.getValue(KEY_POSE);
		    try {
			robotPath = (Path) table.getValue(KEY_PATH);
			//make sure it draws it starting at us
			robotPath.add(0, new Waypoint(robotPose));
		    } catch (TableKeyNotDefinedException tk) {
			//if no path, don't worry.
		    }
		    connected = true;
		} catch (Exception e) {
		    connected = false;
		}
		//redraw with new data
		repaint();
		//wait a while to do it again.
		try {
		    Thread.sleep(50);
		} catch (Exception ex) {
		}
	    }
	}
    }
}

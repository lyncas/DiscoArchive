package discobots.smartdashboard.robotmapper;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
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

    private int size=800;
    private static final boolean DEBUGGING = false;
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

    public RobotMapperExtension(int size){
	this.size=size;
	init();
    }

    public void init() {
	setPreferredSize(new Dimension(size, size));
	setBackground(Color.WHITE);
	try {
	    //table.setIPAddress("10.25.87.7"); // Temporary for testing. Should be 10.25.87.7
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
	g.setColor(Color.BLACK);
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

	//So we draw from the robot to the first waypoint
	robotPath.add(0, new Waypoint(robotPose));
	path_drawing.setPath(robotPath);
	path_drawing.draw(g, panelCenterX, panelCenterY);

    }
    volatile boolean error = false;
    volatile int mode = 0, ia = 0;

    private class DataReaderThread extends Thread {

	@Override
	public void run() {
	    while (true) {
		//update data
		try {
		    robotPose = (Pose) table.getValue(KEY_POSE);
		    robotPath = (Path) table.getValue(KEY_PATH, robotPath);
		    connected = true;
		} catch (Exception e) {
		    connected = false;
		}
		//redraw with new data
		repaint();
		error = false;
		//wait a while to do it again.
		try {
		    Thread.sleep(50);
		} catch (Exception ex) {
		}
	    }
	}
    }
}

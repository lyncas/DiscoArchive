package disco.robotmapper;

import disco.robotmapper.drawables.Robot;
import disco.robotmapper.drawables.DrawablePath;
import disco.robotmapper.drawables.DrawableFeatures;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import lejos.robotics.RangeReading;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

/**
 *
 * @author Nolan Shah, Sam Dietrich
 */
public class RobotMapperExtension extends JPanel implements ITableListener {

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
    //drawables
    private Robot robot;
    private DrawablePath path_drawing;
    private DrawableFeatures features_drawing;

    public RobotMapperExtension(int size) {
	this.size = size;
	init();

	features_drawing.addRangeReading(new RangeReading(0, 50), robotPose);
    }

    public void init() {
	setPreferredSize(new Dimension(size, size));
	setBackground(Color.WHITE);
	try {
	    //COMMENT WHEN TESTING WITHOUT ROBOT.
	    //NetworkTable.setClientMode();
	    NetworkTable.setIPAddress("10.25.87.2");
	    table = NetworkTable.getTable(RobotMapperTableLocation);
	    robotPose = StringParser.getPose(table.getString(KEY_POSE, new Pose(0, 0, 90).toString()));
	    robotWidth = (int) table.getNumber(KEY_ROBOT_WIDTH, 20);
	    robotLength = (int) table.getNumber(KEY_ROBOT_LENGTH, 30);
	    connected = true;
	} catch (Exception e) {
	    e.printStackTrace();
	    connected = false;
	}
	robot = new Robot(robotWidth, robotLength);
	path_drawing = new DrawablePath(robotPath, Color.ORANGE);
	features_drawing = new DrawableFeatures(Color.RED, robotLength / 2);
    }

    @Override
    public void paint(Graphics g) {
	connected=table.isConnected();
	//BACKGROUND
	g.setColor(Color.LIGHT_GRAY);
	g.fillRect(0, 0, getSize().width, getSize().height);
	if (!connected) {
	    g.setColor(Color.ORANGE);
	    g.drawString("NOT CONNECTED", 0, 20);
	}
	//CALCULATIONS
	int panelCenterX = getSize().width / 2;
	int panelCenterY = getSize().height / 2;
	//PATH
	if (robotPath != null) {
	    path_drawing.setPath(robotPath);
	}
	path_drawing.draw(g, panelCenterX, panelCenterY);
	//FEATURES
	features_drawing.draw(g, panelCenterX, panelCenterY);
	//ROBOT
	robot.setPose(robotPose);
	robot.setDisabled(!connected);
	robot.draw(g, panelCenterX, panelCenterY);
	//RULER
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
	g.setColor(new Color(0, 70, 0));//dark green
	for (int x = 0; x < width; x += 50) {
	    g.drawString(String.valueOf(x - Y_AXIS), x, height);
	}

	//Plot Y values along screen left
	g.setColor(new Color(0, 0, 150));//dark blue
	for (int y = 0; y < height; y += 50) {
	    g.drawString(String.valueOf(X_AXIS - y), 0, y);
	}

    }

    /*
     * itable is the table
     * string is the key
     * o is the value
     * bln is whether this key is new on the table
     */
    @Override
    public void valueChanged(ITable itable, String string, Object o, boolean bln) {
	switch (string) {
	    case KEY_POSE:
		robotPose = StringParser.getPose(o.toString());
		break;
	    case KEY_PATH:
		robotPath = StringParser.getPath(o.toString());
		//make sure it draws it starting at us
		robotPath.add(0, new Waypoint(robotPose));
		break;
	    case KEY_ROBOT_WIDTH:
		robotWidth=(int)o;
		break;
	    case KEY_ROBOT_LENGTH:
		robotLength=(int)o;
		break;
	}
	repaint();
    }
}

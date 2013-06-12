package discobots.smartdashboard.robotmapper;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import lejos.robotics.navigation.Pose;

/**
 *
 * @author Nolan Shah, Sam Dietrich
 */
public class RobotMapperExtension extends StaticWidget {

    private static final boolean DEBUGGING = false;
    private NetworkTable table;

    private final String    RobotMapperTableLocation = "LocationInformation",
			    KEY_POSE="robot_Pose",
			    KEY_ROBOT_WIDTH="robot_Width",
			    KEY_ROBOT_LENGTH="robot_Length";

    private volatile Pose robotPose=new Pose(0,0,90);
    private int robotWidth = 0, robotLength = 0;
    private boolean connected = false;
    private DataReaderThread reader = new DataReaderThread();
    private Robot robot;

    public void setValue(Object o) {
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(200, 200));
        try {
            //table.setIPAddress("10.25.87.7"); // Temporary for testing. Should be 10.25.87.7
            table = NetworkTable.getTable(RobotMapperTableLocation);
            robotPose=(Pose)table.getValue(KEY_POSE, new Pose(0,0,90));
            robotWidth = (int) table.getNumber(KEY_ROBOT_WIDTH, 20);
            robotLength = (int) table.getNumber(KEY_ROBOT_LENGTH, 30);
            connected = true;
        } catch (Exception e) {
            e.printStackTrace();
            connected = false;
        }
        reader.start();
        robot = new Robot(robotWidth, robotLength);
    }

    @Override
    public void propertyChanged(Property p) {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (DEBUGGING) { // Debugging information (top left of screen)
            g.setColor(Color.GREEN);
            g.drawString("" + robotPose.getX() + " " + robotPose.getY() + " " + robotPose.getHeading() + " " + connected, 0, 20);
            g.drawString("MODE: " + mode + " :: " + ia, 0, 50);
            if (error) {
                g.drawString("DUMB THREAD ERROR", 0, 100);
            }
        }
        if (!connected) {
            g.setColor(Color.ORANGE);
            g.drawString("NOT CONNECTED", 0, 20);
        }
        try {
            int panelCenterX = getSize().width / 2;
            int panelCenterY = getSize().height / 2;

	    robot.setPose(robotPose);
            robot.drawRobot(g,panelCenterX,panelCenterY,!connected);
        } catch (Exception e) {
            g.setColor(Color.ORANGE);
            g.drawString("EXCEPTION B", 0, 0);
        }
    }
    volatile boolean error = false;
    volatile int mode = 0, ia = 0;

    private class DataReaderThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        //update robot pose
                        robotPose=(Pose) table.getValue(KEY_POSE, robotPose);
                        connected = true;
                    } catch (Exception e) {
                        connected = false;
                    }
                    if (DEBUGGING) { // Internal Autonomous  for testing and counter.
//                        ia += 1;
//                        switch (mode) {
//                            case 0:
//                                robotPositionY += 0.5;
//                                if (robotPositionY >= 20) {
//                                    mode = 1;
//                                }
//                                break;
//                            case 1:
//                                heading -= 0.5;
//                                if (heading <= 0) {
//                                    mode = 2;
//                                }
//                                break;
//                            case 2:
//                                robotPositionX -= 0.5;
//                                if (robotPositionX <= -50) {
//                                    mode = 3;
//                                }
//                        }
                    }
                    repaint();
                    error = false;

                } catch (Exception e) {
                    error = true;
                }
                try {
                    //wait a while to do it again.
                    Thread.sleep(50);
                } catch (Exception ex) {
                }
            }
        }
    }
}

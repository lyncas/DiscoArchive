/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package discobots.smartdashboard.robotmapper;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author sam
 */
public class Driver {
    static JFrame main_frame;
    static JPanel main_panel;
    static RobotMapperExtension map;
    public static final int size = 800;

    public static void main(String[] args) {
	createTopLevel();
	createContent();


	main_frame.setContentPane(main_panel);
	main_frame.pack();
	main_frame.setSize(size, size);
	main_frame.setVisible(true);
    }


        public static void createTopLevel() {
	main_frame = new JFrame();

	main_frame.setTitle("Robot Mapper for leJOS FRC robot.");
	main_frame.setSize(size, size);
	main_frame.setBackground(Color.white);
	main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	main_panel = new JPanel();
    }


	public static void createContent() {
	map = new RobotMapperExtension(size);
	map.setSize(size, size);
	main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
	main_panel.add(map);
    }

}

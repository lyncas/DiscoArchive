package drivevis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import res.MenuStrings;

/**
 *
 * @author sam
 *
 * Plots drivetrain outputs on a grid of inputs. inputs: joystick
 * [-1...1]x[-1...1] outputs: drivetrain [-1...1]x[-1...1]
 */
public class DriveVis {

    static JFrame main_frame;
    static JMenuBar menuBar;
    static JPanel main_panel;
    static JPanel options;
    static Grid g;
    //resolution of the grid
    public static final int divisions = 5;
    public static final int size = 800;
    //Flags
    private static boolean b_fastRefresh = false;
    private static boolean b_hc = false;
    private static boolean b_nums = true;

    public static void main(String[] args) {
	createTopLevel();
	createMenuBar();
	createGrid();
	createOptions();

	main_frame.setContentPane(main_panel);
	main_frame.pack();
	main_frame.setSize(size, size);
	main_frame.setVisible(true);
    }

    public static void createTopLevel() {
	main_frame = new JFrame();

	main_frame.setTitle("DriveVis by Sam Dietrich, FRC 2587 The Discobots");
	main_frame.setSize(size, size);
	main_frame.setBackground(Color.white);
	main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	main_panel = new JPanel();
    }

    public static void createMenuBar() {
	menuBar = new JMenuBar();
	JMenu menu;
	JCheckBoxMenuItem cbMenuItem;

	//GRID OPTIONS MENU
	menu = new JMenu(MenuStrings.menu_title);
	menuBar.add(menu);

	cbMenuItem = new JCheckBoxMenuItem(MenuStrings.btn_fast_refresh);
	cbMenuItem.setToolTipText(MenuStrings.bth_fast_refresh_tooltip);
	cbMenuItem.addItemListener(new menuListener());
	menu.add(cbMenuItem);

	cbMenuItem = new JCheckBoxMenuItem(MenuStrings.btn_high_contrast);
	cbMenuItem.setToolTipText(MenuStrings.btn_high_contrast_tooltip);
	cbMenuItem.addItemListener(new menuListener());
	menu.add(cbMenuItem);

	cbMenuItem = new JCheckBoxMenuItem(MenuStrings.btn_disp_numbers);
	cbMenuItem.setToolTipText(MenuStrings.btn_disp_numbers_tooltip);
	cbMenuItem.setSelected(true);
	cbMenuItem.addItemListener(new menuListener());
	menu.add(cbMenuItem);

	main_frame.setJMenuBar(menuBar);
    }

    public static void createGrid() {
	g = new Grid(divisions, size);

	main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
	main_panel.add(g);
    }

    public static void createOptions() {
	options = new JPanel();
	options.setMaximumSize(new Dimension(800, 100));
	options.setPreferredSize(new Dimension(800, 35));
	options.setMinimumSize(new Dimension(100, 25));
	ButtonGroup modeButs = new ButtonGroup();
	for (DriveMode m : Grid.getModes()) {
	    JRadioButton but = new JRadioButton(m.Name());
	    but.addActionListener(new modeListener());
	    but.setActionCommand(String.valueOf(Grid.getModes().indexOf(m)));
	    modeButs.add(but);
	    options.add(but);
	}
	//Grid resolution spinner
	SpinnerModel sp = new SpinnerNumberModel(divisions < 30 && divisions > 2 ? divisions : 5, 2, 30, 1);
	JSpinner resolutionSpinner = new JSpinner(sp);
	resolutionSpinner.setToolTipText(MenuStrings.spinner_resolution_tooltip);
	resolutionSpinner.addChangeListener(new spinnerListener());
	options.add(resolutionSpinner);

	main_panel.add(options);
    }

    static class spinnerListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
	    //we need to do this first because without it it won't draw. Don't ask. noone knows.
	    g.setNumbers(false);

	    JSpinner spin = (JSpinner) e.getSource();
	    int value = ((SpinnerNumberModel) spin.getModel()).getNumber().intValue();
	    //g.reset(value,size);
	    g = new Grid(value, size);
	    main_panel.remove(0);
	    main_panel.add(g, 0);
	    g.setHighContrast(b_hc);
	    g.setNumbers(b_nums);
	    main_panel.repaint();
	}
    }

    static class modeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    int newMode = Integer.parseInt(e.getActionCommand());
	    g.setMode(newMode);

	    if (b_fastRefresh) {
		main_panel.repaint();
	    }
	}
    }

    static class menuListener implements ActionListener, ItemListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	    JMenuItem source = (JMenuItem) e.getItemSelectable();
	    boolean selected = e.getStateChange() == ItemEvent.SELECTED ? true : false;
	    switch (source.getText()) {
		case MenuStrings.btn_fast_refresh:
		    b_fastRefresh = selected;
		    break;
		case MenuStrings.btn_high_contrast:
		    g.setHighContrast(selected);
		    b_hc = selected;
		    break;
		case MenuStrings.btn_disp_numbers:
		    g.setNumbers(selected);
		    b_nums = selected;
		    break;
	    }
	}
    }
}

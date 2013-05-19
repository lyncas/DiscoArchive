package drivevis;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author sam
 */
public class Grid extends JPanel {

    private Square[][] squares;
    private int center = 0;
    private int square_size;
    private double driveLeft = 0, driveRight = 0;
    private int squaresPerRow = 0;
    private DriveMode thisMode;
    private static final LinkedList<DriveMode> modes = new LinkedList<>();

    public Grid(int div_quad, int parentSize) {
	/*
	 * Just add a new <? implements DriveMode>, add it to this list, and it will work
	 */
	modes.add(new Arcade());
	modes.add(new Cheesy());
	modes.add(new Lerp());


	thisMode = modes.get(0);
	squaresPerRow = div_quad * 2 + 1;
	squares = new Square[squaresPerRow][squaresPerRow];
	center = div_quad - 1;//start at 0
	square_size = parentSize / (squaresPerRow);
	GridLayout layout = new GridLayout(squaresPerRow, squaresPerRow);
	this.setLayout(layout);

	createGrid();
    }

    private void createGrid() {
	for (int x = 0; x < squares.length; x++) {
	    for (int y = 0; y < squares[x].length; y++) {
		squares[y][x] = new Square(y - center - 1, center + 1 - x, square_size, (squaresPerRow - 1) / 2.0);//it's backwards
		Powers p=thisMode.calcLR(squares[y][x].getJoyY(), squares[y][x].getJoyX());
		squares[y][x].setOutput(round01(p.getLeftPower()), round01(p.getRightPower()));
		squares[y][x].setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(squares[y][x]);
	    }
	}
    }

    private void recalculate() {
	for (Square[] sarr : squares) {
	    for (Square s : sarr) {
		Powers p=thisMode.calcLR(s.getJoyY(), s.getJoyX());
		s.setOutput(round01(p.getLeftPower()), round01(p.getRightPower()));
	    }
	}
    }

    public void setMode(int newMode) {
	thisMode = modes.get(newMode);
	recalculate();
    }

    public void setHighContrast(boolean hc){
	    for (Square[] sarr : squares) {
	    for (Square s : sarr) {
		s.setHC(hc);
		s.refresh();
	    }
	}
    }

    public static LinkedList<DriveMode> getModes(){
	return modes;
    }

    /*
     * rounds to the hundredths place
     */
    public double round01(double in){
	return Math.round(in * 100) / 100.0;
    }
}

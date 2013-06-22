/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper;

import java.awt.Point;

/**
 *
 * @author Sam Dietrich
 *
 * Keep track of and calculate view information for main class to use in display
 */
public class ViewHelper {

    //Logical screen
    private double x_min, x_max, y_min, y_max;
    //Physical screen
    private int screen_width, screen_height;
    //Mathematical constructs
    private double x_range, y_range;
    private int x_axis, y_axis;// x position of y axis and y position of x axis in pixel world

    /*
     * For square screens.
     */
    public ViewHelper(double logical_size, int pixel_size) {
	this(logical_size, logical_size, pixel_size, pixel_size);
    }

    /*
     * For simpler screens centered around the origin.
     */
    public ViewHelper(double logical_width, double logical_height, int pixel_width, int pixel_height) {
	this(-logical_width / 2, logical_width / 2, -logical_height / 2, logical_height / 2, pixel_width, pixel_height);
    }

    /*
     * Precondidtion: max > min
     * Specify all bounds of logical window and physical screen size.
     * Make sure the x-y logical and x-y physical aspect ratios are the same to avoid distortion.
     */
    public ViewHelper(double x_min, double x_max, double y_min, double y_max, int screen_width, int screen_height) {
	this.x_min = x_min;
	this.x_max = x_max;
	this.y_min = y_min;
	this.y_max = y_max;

	this.screen_width = screen_width;
	this.screen_height = screen_height;

	x_range = x_max - x_min;
	y_range = y_max - y_min;
	findAxes();
    }

    public void updateView(double d_x_min, double d_x_max, double d_y_min, double d_y_max) {
	updateView(d_x_min, d_x_max, d_y_min, d_y_max, 0, 0);
    }

    public void updateView(double d_x_min, double d_x_max, double d_y_min, double d_y_max, int d_screen_width, int d_screen_height) {
	x_min += d_x_min;
	x_max += d_x_max;
	y_min += d_y_min;
	y_max += d_y_max;

	screen_width += d_screen_width;
	screen_height += d_screen_height;

	x_range = x_max - x_min;
	y_range = y_max - y_min;
	findAxes();
    }

    //Calcualte the pixel locations of the logical axes
    private void findAxes() {
	//The idea here is that the axes are a proportion of the screen from the left/top

	//Y-AXIS
	y_axis = (int) (-x_min / x_range * screen_width);

	//X-axis
	x_axis = (int) (-y_min / y_range * screen_height);
    }

    /*
     * Returns the location of the logical origin in the pixel system.
     */
    public Point getOrigin(){
	return new Point(y_axis,x_axis);
    }

    /*
     * Returns the number of pixels to cover to get one unit of whatever the input size was.
     * In the x direction.
     */
    public double X_PixelsPerUnit(){
	return screen_width/x_range;
    }

    /*
     * Returns the number of pixels to cover to get one unit of whatever the input size was.
     * In the y direction.
     */
    public double Y_PixelsPerUnit(){
	return screen_height/y_range;
    }
}

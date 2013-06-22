/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper;

/**
 *
 * @author Sam Dietrich
 *
 * Keep track of and calculate view information for main class to use in display
 */
public class ViewHelper {

    //Logical screen
    double x_min, x_max, y_min, y_max;
    //Physical screen
    double screen_width, screen_height;
    //Mathematical constructs
    double x_range, y_range;

    /*
     * For square screens.
     */
    public ViewHelper(double logical_size, double pixel_size) {
	this(logical_size, logical_size, pixel_size, pixel_size);
    }

    /*
     * For simpler screens centered around the origin.
     */
    public ViewHelper(double logical_width, double logical_height, double pixel_width, double pixel_height) {
	this(-logical_width / 2, logical_width / 2, -logical_height / 2, logical_height / 2, pixel_width, pixel_height);
    }

    /*
     * Precondidtion: max > min
     * Specify all bounds of logical window and physical screen size.
     * Make sure the x-y logical and x-y physical aspect ratios are the same to avoid distortion.
     */
    public ViewHelper(double x_min, double x_max, double y_min, double y_max, double screen_width, double screen_height) {
	this.x_min = x_min;
	this.x_max = x_max;
	this.y_min = y_min;
	this.y_max = y_max;

	this.screen_width = screen_width;
	this.screen_height = screen_height;

	x_range = x_max - x_min;
	y_range = y_max - y_min;
    }

    public void updateView(double d_x_min,double d_x_max,double d_y_min,double d_y_max){
	updateView(d_x_min,d_x_max,d_y_min,d_y_max,0,0);
    }

    public void updateView(double d_x_min,double d_x_max,double d_y_min, double d_y_max, double d_screen_width, double d_screen_height){
	x_min+=d_x_min;
	x_max+=d_x_max;
	y_min+=d_y_min;
	y_max+=d_y_max;

	screen_width+=d_screen_width;
	screen_height+=d_screen_height;
    }
}

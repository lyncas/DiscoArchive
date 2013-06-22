/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.robotmapper.drawables;

import disco.robotmapper.ViewHelper;
import java.awt.Graphics;

/**
 *
 * @author Sam Dietrich
 *
 * Interface for things that can draw themselves on the map. They need to scale themselves (later) and be in the correct position.
 */
interface drawable {

    public void draw(Graphics g, ViewHelper v);

}

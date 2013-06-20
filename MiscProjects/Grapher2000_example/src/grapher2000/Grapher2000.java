/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grapher2000;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.WindowConstants;
/**
 *
 * @author sam
 */
public class Grapher2000 extends JFrame{
    static Grapher2000 frame;
    static Graph area;

    public static void main(String[] args) {
        frame=new Grapher2000();
    }

    public Grapher2000(){
        setSize(1000,800);
        setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        area=new Graph(1000,800);
        area.setSize(getWidth(),getHeight());
        getContentPane().add(area);
        pack();
        setSize(1000,800);
        setVisible(true);
    }
}
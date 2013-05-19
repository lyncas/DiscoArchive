/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

/**
 *
 * @author sam
 */
public interface PIDTuneable {
    public double getP();
    public double getI();
    public double getD();
    public double getF();
    public void setPID(double p,double i, double d, double f);
}

/*
 *
 *      This program draws some neat-o graphs
 *	By Sam Dietrich
 *
 *	New and Improved!
 *	5/12/2012
 *	-migration away from Expo complete
 *	-added new taylor functions
 *      6/29/12
 *      -added variable range
 *      -added visual location assist
 *      -now supports virtual graphics
 *
 *      TODO (semi-sorted by priority):
 *      -INVERT Y
 *      -avod recalculating graph every time we move the mouse
 *      -add invalid min/max exceptions
 *      -add biginteger
 *      -add resolution control
 *      -add label resolution control
 *      -add zoom
*/
package grapher2000;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Graph extends Panel implements MouseListener,MouseMotionListener
{
    //*** Set these values ***//
    double xMin=-20;
    double xMax=20;
    double yMin=-2;
    double yMax=2;

    //*** Internal use only ***//
    double x=0;				// independent variable
    double y=0;				// dependent variable
    double xold=0;			// stores the x-value from the previous iteration
    double yold=0;			// stores the y-value from the previous iteration
    int unX=0; 				// unmodified x
    int unY=0; 				// unmodified y
    int X_AXIS, Y_AXIS;                 // The y-position of the x-axis and vice versa
    int width,height;                   // The dimensions of the window, hopefully
    double yRange,xRange;               // The range of values between min and max
    int xMov,yMov=0;                    // Used to show the mouse assist

    Image virtualMem;
    Graphics2D gBuff;
    Image bgMem;
    Graphics2D bg;
    boolean firstpaint=true;


    public Graph(int w,int h){
        width=w;
        height=h;
        init();
    }

    public void init(){
        setBackground(Color.white);
        findAxes();
        addMouseMotionListener(this);
    }
    @Override
    public void paint(Graphics g)
    {
        if(firstpaint){
            virtualMem=createImage(width,height);
            gBuff=(Graphics2D) virtualMem.getGraphics();
            bgMem=createImage(width,height);
            bg=(Graphics2D) bgMem.getGraphics();
            firstpaint=false;
        }
            //Draw the axes, numbers, and tick marks
            drawGrid(gBuff);
            // Draws graph
            //        minimum x; maximum x; resolution
            for (double i =xMin; i <= xMax; i+=0.001)
            {
                x=i;
/**********************************/
                y=Math.sin(x)+Math.sin(1.1*x);        //PUT EQUATION HERE. EX: y=1/x;		<<<<-----<<<<-----<<<<-----<<<<-----<<<<-----<<<<-----<<<<-----<<<<-----<<<<-----
/**********************************/

                //plot our new point
                plot(gBuff);
            }


            drawAssist(gBuff);
            //g.drawImage(bgMem, 0, 0, this);
            g.drawImage(virtualMem, 0, 0, this);
            gBuff.setColor(Color.WHITE);
            gBuff.fillRect(0,0,width,height);
            gBuff.setColor(Color.BLACK);
    }

    //calibrate graph
    public void findAxes(){
        //Y-axis
        xRange=xMax-xMin;
        if(xMin<=0 && xMax>=0){
            double xDist=-xMin/xRange;
            Y_AXIS=(int) (xDist*width);
        }
        else{
            Y_AXIS=(int) (-xMin/xRange*width);
        }

        //X-axis
        yRange=yMax-yMin;
        if(yMin<=0 && yMax>=0){
            double yDist=-yMin/yRange;
            X_AXIS=(int) (yDist*height);
        }
        else{
            X_AXIS=(int) (-yMin/yRange*height);
        }
    }

    public void drawGrid(Graphics2D g){
        // X-axis
        g.drawLine(0,X_AXIS,width,X_AXIS);
        // Y-axis
        g.drawLine(Y_AXIS,0,Y_AXIS,height);

        // Draws numbers and hashes
        int yG=Math.max(Y_AXIS, 0);     //Correction for offscreen axis
        int xG=Math.max(X_AXIS, 0);     //Correction for offscreen axis
        //Y
        for(int i=(int) Math.ceil(yMin); i<=yMax; i++){
        Integer yMark=new Integer(i);
        g.drawString(yMark.toString(),yG+10,(int)(X_AXIS+i*(height)/(yRange)));
        g.drawLine(yG-5,(int)(X_AXIS+i*(height)/(yRange)),yG+5,(int)(X_AXIS+i*(height)/(yRange)));
        }
        //X
        for(int i=(int) Math.ceil(xMin); i<=xMax; i++){
        Integer xMark=new Integer(i);
        g.drawString(xMark.toString(),(int)(Y_AXIS+i*(width)/(xRange))+3,xG);
        g.drawLine((int)(Y_AXIS+i*(width)/(xRange)),xG-5,(int)(Y_AXIS+i*(width)/(xRange)),xG+5);
        }
    }

    public void plot(Graphics2D g){
        // Saves raw x&y for later <unused>
            unX=(int)x;
            unY=(int)y;

            // Zooms in
            x*=width/xRange;
            y*=height/yRange;

            // Moves x&y to center of window
            x=x+Y_AXIS;
            y=(-1*y)+X_AXIS;

            //	Finally does some drawing
            g.drawLine((int)x,(int)y,(int)x,(int)y);
            g.drawLine((int)x,(int)y,(int)xold,(int)yold);

            // Stores x&y from this time to draw line next time
            xold=x;
            yold=y;
    }

    public void drawAssist(Graphics2D g){
        g.drawLine(xMov, 0, xMov, height);
        g.drawLine(0, yMov, width, yMov);
        float xLoc=(float) ((xMov-Y_AXIS)*xRange/width);
        float yLoc=(float) ((yMov-X_AXIS)*yRange/height);
        g.drawString(xLoc+","+yLoc, 30, 30);
    }

    public void zoom(int x,int y, double mult){

    }




    @Override
    public void mouseDragged(MouseEvent e) {mouseMoved(e);}

    @Override
    public void mouseMoved(MouseEvent e) {
        xMov=e.getX();
        yMov=e.getY();
        repaint();
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xClk=e.getX();
        int yClk=e.getY();
        if(e.getButton()==MouseEvent.BUTTON3){
            zoom(xClk,yClk,0.5);
        }
        else{
            zoom(xClk,yClk,2.0);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {    }

    @Override
    public void mouseReleased(MouseEvent e) {    }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }
}
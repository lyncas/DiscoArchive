/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

/**
 * 
 * Maxbotix sonar LV-Maxsonar-EZ4
 */
public class MaxbotixSonarYellowDot extends MaxbotixSonar{
    protected static final double k_InchesFactor = 102.4;
    protected static final double k_MillimetersFactor = 25.4*k_InchesFactor;
    
    public MaxbotixSonarYellowDot(int slot,int channel, Unit units){
        super(slot,channel,units);
    }
    public MaxbotixSonarYellowDot(int channel,Unit units){
        super(channel,units);
    }
}

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
    public static final double MAX_RANGE=254;//inches. about 22 feet.
    public static final double MAX_PEOPLE_RANGE=12*5;//inches. Maximum range for detecting objects the size of people.
    public static final double MIN_RANGE=6;
    public static final int MIN_READING_DELAY=50;//msec

    public MaxbotixSonarYellowDot(int slot,int channel, Unit units){
        super(slot,channel,units);
    }
    public MaxbotixSonarYellowDot(int channel,Unit units){
        super(channel,units);
    }
}

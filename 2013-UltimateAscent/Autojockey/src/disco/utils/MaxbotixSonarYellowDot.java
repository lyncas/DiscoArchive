/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

/**
 *
 * Maxbotix sonar LV-Maxsonar-EZ4
 */
public class MaxbotixSonarYellowDot extends MaxbotixSonar {

    public MaxbotixSonarYellowDot(int slot, int channel, Unit units) {
        super(slot, channel, units);
        k_InchesFactor = 102.4;
        k_MillimetersFactor = 25.4 * k_InchesFactor;
        MAX_RANGE = 254;//inches. about 22 feet.
        MAX_PEOPLE_RANGE = 12 * 5;//inches. Maximum range for detecting objects the size of people.
        MIN_RANGE = 6;
        MIN_READING_DELAY = 50;//msec
    }

    public MaxbotixSonarYellowDot(int channel, Unit units) {
        this(1, channel, units);
    }
}

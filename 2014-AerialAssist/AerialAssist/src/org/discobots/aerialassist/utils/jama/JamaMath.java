package org.discobots.aerialassist.utils.jama;

/**
 * JamaMath Math Utils for Jama
 *
 * Created by Nolan Shah to handle JAMA math for speedfrc
 */
public class JamaMath {

    /**
     * sqrt(a^2 + b^2) without under/overflow. *
     */
    public static double hypot(double a, double b) {

        double r;

        if (Math.abs(a) > Math.abs(b)) {

            r = b / a;

            r = Math.abs(a) * Math.sqrt(1 + r * r);

        } else if (b != 0) {

            r = a / b;

            r = Math.abs(b) * Math.sqrt(1 + r * r);

        } else {

            r = 0.0;

        }

        return r;

    }

}

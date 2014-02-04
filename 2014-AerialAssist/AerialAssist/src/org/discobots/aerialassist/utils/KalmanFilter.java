package org.discobots.aerialassist.utils;

import org.discobots.aerialassist.utils.jama.*;

/**
 * Kalman Filter.
 *
 * @author Nolan Shah (Based on version by Sam Dietrich & Wikipedia)
 */
public class KalmanFilter {

    private final Matrix F, H, Q, R, B;
    private Matrix X, Xbar, K, P, Pbar, I;

    public KalmanFilter(Matrix F, Matrix H, Matrix Q, Matrix R, Matrix B) {
        this.F = F;
        this.H = H;
        this.Q = Q;
        this.R = R;
        this.B = B;
    }

    public void setState(Matrix X, Matrix P) {
        this.X = X;
        this.P = P;
        I = (Matrix.identity(X.getRowDimension(), X.getRowDimension()));
    }

    public void calculate(Matrix Z, Matrix U) { // Taken from Wikipedia
        Xbar = (F.times(X)).plus(B.times(U));
        Pbar = ((F.times(P)).times(F.transpose())).plus(Q);
        Matrix Y = H.times(X).plus(Z.minus(H.times(Xbar)));
        Matrix S = H.times(Pbar).times(H.transpose()).plus(R);
        K = Pbar.times(H.transpose()).times(S.inverse());
        X = Xbar.plus(K.times(Y));
        P = (I.minus(K.times(H))).times(Pbar);
    }

    public Matrix getStateEstimate() {
        return X;
    }

    public Matrix getCovariance() {
        return P;
    }
}

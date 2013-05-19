package lejos.util;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */
/**
 * Implementation of a Kalman filter using the Matrix class
 * 5/16/2013 SMD Updated variable names to mirror wikipedia article
 */
public class KalmanFilter {

    private Matrix F, B, H, i, R, Q, Ft, Ht;
    private Matrix mu, sigma, muBar, sigmaBar, gain;

    public KalmanFilter(Matrix F, Matrix B, Matrix H, Matrix R, Matrix Q) {
	//State transition model which is applied to the previous state
	this.F = F;
	//Control input model which is applied to the control vector
	this.B = B;
	//Observation model which maps true state space into observed space (change units or other dimensional conversion)
	this.H = H;
	//Covariance of observation noise (assumed to be gaussian white noise centered at 0)
	this.R = R;
	//Covariance of process noise (assumes noise has multivariate normal distribution)
	this.Q = Q;
	this.Ft = F.transpose();
	this.Ht = H.transpose();
    }

    public void setState(Matrix mean, Matrix covariance) {
	//mu is xk|k in wikipedia "state estimate at time k given observations up to and including time k"
	this.mu = mean;
	//sigma is Pk|k in wikipedia "covariance at time k given observations up to and including time k"
	this.sigma = covariance;
	int n = mu.getRowDimension();
	this.i = Matrix.identity(n, n);
    }

    //Control vector is u in wikipedia article
    //Measurement is z in wikipedia article
    public void update(Matrix control, Matrix measurement) {
	//Control update step 1: calculate the predicted mean (state estimate)
	//muBar is xk|k-1 in wikipedia "state estimate at time k given observations up to and including time k-1"
	muBar = F.times(mu).plus(B.times(control));

	// Control update step 2: calculate the predicted covariance
	//sigmaBar is Pk|k-1 in wikipedia "estimated covariance at time k give observations up to and including time k-1"
	sigmaBar = F.times(sigma).times(Ft).plus(Q);

	// Calculate the Kalman Gain
	//gain is Kk in wikipedia "Optimal (MMSE) Kalman gain at time k"
	gain = sigmaBar.times(Ht).times(H.times(sigmaBar).times(Ht).plus(R).inverse());

	// Measurement update: calculate the new mean
	mu = muBar.plus(gain.times(measurement.minus(H.times(muBar))));

	// Calculate the new covariance
	sigma = i.minus(gain.times(H)).times(sigmaBar);
    }

    public Matrix getMean() {
	return mu;
    }

    public Matrix getCovariance() {
	return sigma;
    }

    public Matrix getPredictedMean() {
	return muBar;
    }

    public Matrix getPredictedCovariance() {
	return sigmaBar;
    }

    public Matrix getGain() {
	return gain;
    }
}

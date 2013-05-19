package lejos.robotics.objectdetection;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * Any class implementing this interface and registering with a FeatureDetector will receive
 *  notifications when a feature is detected. 
 *  @see lejos.robotics.objectdetection.FeatureDetector#addListener(FeatureListener)
 *  @author BB based on concepts by Lawrie Griffiths
 *
 */
public interface FeatureListener {
	
	/**
	 * The angle and range (in a RangeReading) of a feature is reported when a feature is detected.
	 * @param feature The RangeReading, which contains angle and range.
	 */
	public void featureDetected(Feature feature, FeatureDetector detector);
	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.vision;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.VisionTracking;

/**
 *
 * @author thomasklau
 */
public class identifyHot extends CommandBase{
    
    ColorImage image = null;
    BinaryImage thresholdImage = null;
    BinaryImage filteredImage;
    
    public identifyHot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public class Scores {
        double rectangularity;
        double aspectRatioVertical;
        double aspectRatioHorizontal;
    }
    
    public class TargetReport {
		int verticalIndex;
		int horizontalIndex;
		boolean Hot;
		double totalScore;
		double leftScore;
		double rightScore;
		double tapeWidthScore;
		double verticalScore;
    };
    public void filterImage() throws NIVisionException
    {
        TargetReport target = new TargetReport();
	int verticalTargets[] = new int[VisionTracking.MAX_PARTICLES];
	int horizontalTargets[] = new int[VisionTracking.MAX_PARTICLES];
	int verticalTargetCount, horizontalTargetCount;
        
        
            try {
                /**
                 * Do the image capture with the camera and apply the algorithm described above. This
                 * sample will either get images from the camera or from an image file stored in the top
                 * level directory in the flash memory on the cRIO. The file name in this case is "testImage.jpg"
                 * 
                 */
                try{
                image = visionSub.getCamera().getImage();   
                image.write("/image.bmp");
                }catch(Exception e)
                {
                    System.out.println("ERROR: Could not get image from the Axis Camera!");
                    e.printStackTrace();
                }
                
                //image = new RGBImage("/testImage.jpg"); //uncomment if using a test image from the cRIO		
                
                //thresholdImage = image.thresholdHSV(105, 137, 230, 255, 133, 183);   // keep only green objects
                //maybe try using thresholdRGB in addition to tresholdHSV?
                
                
                thresholdImage = image.thresholdHSV(VisionTracking.hi,VisionTracking.hf,VisionTracking.si,VisionTracking.sf,VisionTracking.vi,VisionTracking.vf);
                
                //to test hsv colors go to http://colorizer.org/
                
                
                try{
                thresholdImage.write("/threshold.bmp");
                }catch(Exception e)
                {
                    System.out.println("ERROR: Problem writing threshold image!");
                    e.printStackTrace();
                }
                
                filteredImage = thresholdImage.particleFilter(visionSub.getCC()); 
                try{
                filteredImage.write("/filteredImage.bmp");
                }catch(Exception e)
                {
                    System.out.println("ERROR: Problem writing filtered image!");
                    e.printStackTrace();
                }
                
                //iterate through each particle and score to see if it is a target
                int numParticles = filteredImage.getNumberParticles(); 
                Scores scores[] = new Scores[numParticles]; //store particle analysis reports for each particle as long as numParticle < MAX_PARTICLES
                System.out.println("The number of particles in the image is " + scores.length); //remove this line later, only for debugging purposes 
                horizontalTargetCount = verticalTargetCount = 0;
                
                if(numParticles > 0)
                {
			for (int i = 0; i < VisionTracking.MAX_PARTICLES && i < numParticles; i++) {
			ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
                        scores[i] = new Scores();
					
			//Score each particle on rectangularity and aspect ratio
			scores[i].rectangularity = scoreRectangularity(report);
			scores[i].aspectRatioVertical = scoreAspectRatio(filteredImage, report, i, true);
			scores[i].aspectRatioHorizontal = scoreAspectRatio(filteredImage, report, i, false);			
					
			//Check if the particle is a horizontal target, if not, check if it's a vertical target
                        System.out.println("#" + (i++) + " :TARGET REPORT");
			if(scoreCompare(scores[i], false)) //check for horizonal target?
			{
                            System.out.println("particle: " + i + "is a Horizontal Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
                            horizontalTargets[horizontalTargetCount++] = i; //Add particle to target array and increment count
			} else if (scoreCompare(scores[i], true)) { //check for vertical target?
                            System.out.println("particle: " + i + "is a Vertical Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
                            verticalTargets[verticalTargetCount++] = i;  //Add particle to target array and increment count
			} else { //not a target
                            System.out.println("particle: " + i + "is not a Target centerX: " + report.center_mass_x + "centerY: " + report.center_mass_y);
			}
                            System.out.println("rect: " + scores[i].rectangularity + "ARHoriz: " + scores[i].aspectRatioHorizontal);
                            System.out.println("ARVert: " + scores[i].aspectRatioVertical);	
			}

			//Zero out scores and set verticalIndex to first target in case there are no horizontal targets
			target.totalScore = target.leftScore = target.rightScore = target.tapeWidthScore = target.verticalScore = 0;
			target.verticalIndex = verticalTargets[0];
			for (int i = 0; i < verticalTargetCount; i++)
			{
				ParticleAnalysisReport verticalReport = filteredImage.getParticleAnalysisReport(verticalTargets[i]);
				for (int j = 0; j < horizontalTargetCount; j++)
				{
                                    ParticleAnalysisReport horizontalReport = filteredImage.getParticleAnalysisReport(horizontalTargets[j]);
                                    double horizWidth, horizHeight, vertWidth, leftScore, rightScore, tapeWidthScore, verticalScore, total;
	
                                    //Measure equivalent rectangle sides for use in score calculation
                                    horizWidth = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[j], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
                                    vertWidth = NIVision.MeasureParticle(filteredImage.image, verticalTargets[i], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
                                    horizHeight = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[j], false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
						
                                    //Determine if the horizontal target is in the expected location to the left of the vertical target
                                    leftScore = ratioToScore(1.2*(verticalReport.boundingRectLeft - horizontalReport.center_mass_x)/horizWidth);
                                    //Determine if the horizontal target is in the expected location to the right of the  vertical target
                                    rightScore = ratioToScore(1.2*(horizontalReport.center_mass_x - verticalReport.boundingRectLeft - verticalReport.boundingRectWidth)/horizWidth);
                                    //Determine if the width of the tape on the two targets appears to be the same
                                    tapeWidthScore = ratioToScore(vertWidth/horizHeight);
                                    //Determine if the vertical location of the horizontal target appears to be correct
                                    verticalScore = ratioToScore(1-(verticalReport.boundingRectTop - horizontalReport.center_mass_y)/(4*horizHeight));
                                    total = leftScore > rightScore ? leftScore:rightScore;
                                    total += tapeWidthScore + verticalScore;

                                    //If the target is the best detected so far store the information about it
                                    if(total > target.totalScore)
                                    {
                                            target.horizontalIndex = horizontalTargets[j];
                                            target.verticalIndex = verticalTargets[i];
                                            target.totalScore = total;
                                            target.leftScore = leftScore;
                                            target.rightScore = rightScore;
                                            target.tapeWidthScore = tapeWidthScore;
                                            target.verticalScore = verticalScore;
                                    }
                                }
                                //Determine if the best target is a Hot target
                                target.Hot = hotOrNot(target);
                            }

                            if(verticalTargetCount > 0)
                            {
                                    //Information about the target is contained in the "target" structure
                                    //To get measurement information such as sizes or locations use the
                                    //horizontal or vertical index to get the particle report as shown below
                                    ParticleAnalysisReport distanceReport = filteredImage.getParticleAnalysisReport(target.verticalIndex);
                                    double distance = computeDistance(filteredImage, distanceReport, target.verticalIndex);
                                    if(target.Hot)
                                    {
                                            System.out.println("Hot target located");
                                            System.out.println("Distance: " + distance);
                                    } else {
                                            System.out.println("NO hot target present");
                                            System.out.println("Distance: " + distance);
                                    }
                            }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }finally{
                filteredImage.free();
                thresholdImage.free();
                image.free();
            }
        
    }
    
    
    /**
     * Computes the estimated distance to a target using the height of the particle in the image. For more information and graphics
     * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
     * 
     * @param image The image to use for measuring the particle estimated rectangle
     * @param report The Particle Analysis Report for the particle
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * @return The estimated distance to the target in Inches.
     */
    double computeDistance (BinaryImage image, ParticleAnalysisReport report, int particleNumber) throws NIVisionException {
            double rectLong, height;
            int targetHeight;

            rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
            //using the smaller of the estimated rectangle long side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(report.boundingRectHeight, rectLong);
            targetHeight = 32;

            return VisionTracking.Y_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VisionTracking.VIEW_ANGLE*Math.PI/(180*2)));
    }
    
    /**
     * Computes a score (0-100) comparing the aspect ratio to the ideal aspect ratio for the target. This method uses
     * the equivalent rectangle sides to determine aspect ratio as it performs better as the target gets skewed by moving
     * to the left or right. The equivalent rectangle is the rectangle with sides x and y where particle area= x*y
     * and particle perimeter= 2x+2y
     * 
     * @param image The image containing the particle to score, needed to perform additional measurements
     * @param report The Particle Analysis Report for the particle, used for the width, height, and particle number
     * @param outer	Indicates whether the particle aspect ratio should be compared to the ratio for the inner target or the outer
     * @return The aspect ratio score (0-100)
     */
    public double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean vertical) throws NIVisionException
    {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = vertical ? (4.0/32) : (23.5/4);	//Vertical reflector 4" wide x 32" tall, horizontal 23.5" wide x 4" tall
	
        //Divide width by height to measure aspect ratio
        if(report.boundingRectWidth > report.boundingRectHeight){
            //particle is wider than it is tall, divide long by short
            aspectRatio = ratioToScore((rectLong/rectShort)/idealAspectRatio);
        } else {
            //particle is taller than it is wide, divide short by long
            aspectRatio = ratioToScore((rectShort/rectLong)/idealAspectRatio);
        }
	return aspectRatio;
    }
    
    /**
     * Compares scores to defined limits and returns true if the particle appears to be a target
     * 
     * @param scores The structure containing the scores to compare
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * 
     * @return True if the particle meets all limits, false otherwise
     */
    boolean scoreCompare(Scores scores, boolean vertical){
	boolean isTarget = true;

	isTarget &= scores.rectangularity > VisionTracking.RECTANGULARITY_LIMIT;
	if(vertical){
            isTarget &= scores.aspectRatioVertical > VisionTracking.ASPECT_RATIO_LIMIT;
	} else {
            isTarget &= scores.aspectRatioHorizontal > VisionTracking.ASPECT_RATIO_LIMIT;
	}

	return isTarget;
    }
    
    /**
     * Computes a score (0-100) estimating how rectangular the particle is by comparing the area of the particle
     * to the area of the bounding box surrounding it. A perfect rectangle would cover the entire bounding box.
     * 
     * @param report The Particle Analysis Report for the particle to score
     * @return The rectangularity score (0-100)
     */
    double scoreRectangularity(ParticleAnalysisReport report){
            if(report.boundingRectWidth*report.boundingRectHeight !=0){
                    return 100*report.particleArea/(report.boundingRectWidth*report.boundingRectHeight);
            } else {
                    return 0;
            }
    }
    
    	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}
	
	/**
	 * Takes in a report on a target and compares the scores to the defined score limits to evaluate
	 * if the target is a hot target or not.
	 * 
	 * Returns True if the target is hot. False if it is not.
	 */
	boolean hotOrNot(TargetReport target)
	{
		boolean isHot = true;
		
		isHot &= target.tapeWidthScore >= VisionTracking.TAPE_WIDTH_LIMIT;
		isHot &= target.verticalScore >= VisionTracking.VERTICAL_SCORE_LIMIT;
		isHot &= (target.leftScore > VisionTracking.LR_SCORE_LIMIT) | (target.rightScore > VisionTracking.LR_SCORE_LIMIT);
		
		return isHot;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
        try {
            filterImage();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

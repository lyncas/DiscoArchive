package disco.utils;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author mrdouglass
 */
public class DiscoCounterEncoder extends Counter implements PIDSource{
    
    private double cyclesPerRev;
    private Filter filter;
    
    public DiscoCounterEncoder(int slot, int channel, double a){
        super(slot, channel);
        cyclesPerRev = a;
        filter=new Filter();
        filter.start();
    }
    
    public double pidGet(){
        return getRPM();
    }
    
    public double getRPM(){
        return 60.0/getPeriod()/cyclesPerRev;
    }
    
    public double getFilteredRPM(){
        return filter.averageRPM();
        
    }

    class Filter extends Thread{
        private int length=12;
        private double[] periods=new double[length];
        private int index=0;
        public Filter(){
            
        }

        public void run(){
            while(true){
                periods[index]=getRPM();
                index++;
                if(index>length-1){
                    index=0;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    System.out.println("Shooter Filter dum!");
                }
            }
        }
        
        public double averageRPM(){
            double sum=0.0;
            for(int i=0; i<length;i++){
                sum+=periods[i];
            }
            return sum/length;
        }
    }
}
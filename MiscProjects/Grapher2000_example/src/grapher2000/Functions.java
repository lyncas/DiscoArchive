/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grapher2000;

/**
 *
 * @author sam
 */
public class Functions {
    public double EtoX(double Xin, int iterations){
            double sum=0;
            for(int i=0;i<=iterations;i++){
                    sum+=Math.pow(Xin,i)/factorial(i);
            }
            return sum;
    }

    public double Sin(double Xin, int iterations){
            double sum=0;
            for(int i=0;i<=iterations;i++){
                    sum+=Math.pow(-1,i)*Math.pow(Xin,2*i+1)/factorial(2*i+1);
            }
            return sum;
    }

    public double Cos(double Xin, int iterations){
            double sum=0;
            for(int i=0;i<=iterations;i++){
                    sum+=Math.pow(-1,i)*Math.pow(Xin,2*i)/factorial(2*i);
            }
            return sum;
    }

    public long factorial(int n){
            if(n==0) return 1;
            if(n>0){
                    long result=1;
                    for(int i=1;i<=n;i++){
                            result*=i;
                    }
                    return result;
            }
            return 0;	//really undefined
    }
}

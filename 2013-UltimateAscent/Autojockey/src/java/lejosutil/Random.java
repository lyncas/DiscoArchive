package java.lejosutil;

import java.lejoslang.Math;

/**
 * Pseudo-random number generation.
 */
public class Random
{
	//TODO make this class JDK compliant

  private int iPrevSeed, iSeed;
  private boolean haveNextNextGaussian;
  private double nextNextGaussian;

  public Random (long seed)
  {
    iPrevSeed = 1;
    iSeed = (int) seed;
  }

    public Random()
    {
	this(System.currentTimeMillis());
    }

  /**
   * @return A random positive or negative integer.
   */
  public int nextInt()
  {
    int pNewSeed = (iSeed * 48271) ^ iPrevSeed;
    iPrevSeed = iSeed;
    iSeed = pNewSeed;
    return pNewSeed;
  }

  	private int next(int bits)
  	{
  		// just some work in progress, should replace nextInt()
  		int mask;
  		if (bits < 32)
  			mask = (1 << bits) - 1;
  		else
  			mask = -1;
  		return nextInt() & mask;
  	}

    /**
     * Returns a random integer in the range 0...n-1.
     * @param n  the bound
     * @return A random integer in the range 0...n-1.
     */
    public int nextInt (int n)
    {
	int m = nextInt() % n;
	return m >= 0 ? m : m + n;
    }

    public long nextLong()
    {
		int n1 = this.next(32);
		int n2 = this.next(32);
		return ((long)n1 << 32) + n2;
    }

    /**
     * Returns a random boolean in the range 0-1.
     * @return A random boolean in the range 0-1.
     */
    public boolean nextBoolean()
    {
		return this.next(1) != 0;
    }

    public float nextFloat()
    {
    	// we need 24 bits number to create 23 bit mantissa
		return this.next(24) * 5.9604644775390625E-8f;
    }

    public double nextDouble()
    {
    	// we need 53 bits number to create 52 bit mantissa
		int n1 = this.next(26);	//26 bits
		int n2 = this.next(27);	//27 bits
		long r = ((long)n1 << 27) | n2;
		return r * 1.1102230246251565E-16;
    }

    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 and standard deviation 1.0 from this random number generator's sequence.
     * @return Returns the next pseudorandom, Gaussian ("normally") distributed double value
     */
    public double nextGaussian(){
    	//http://java.sun.com/j2se/1.4.2/docs/api/java/util/Random.html#nextGaussian()
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return nextNextGaussian;
	    } else {
	            double v1, v2, s;
	            do {
	                    v1 = 2 * nextDouble() - 1;   // between -1.0 and 1.0
	                    v2 = 2 * nextDouble() - 1;   // between -1.0 and 1.0
	                    s = v1 * v1 + v2 * v2;
	            } while (s >= 1 || s == 0);
	            double multiplier = Math.sqrt(-2 * Math.log(s)/s);
	            nextNextGaussian = v2 * multiplier;
	            haveNextNextGaussian = true;
	            return v1 * multiplier;
	    }
    }
}

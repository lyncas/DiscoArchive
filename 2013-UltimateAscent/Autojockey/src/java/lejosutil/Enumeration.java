package java.lejosutil;
/**
 * Enumeration object allows you to go through
 * collections one object at a time.
 * @author BB
 *
 */
public interface Enumeration {
	public boolean hasMoreElements();

	/**
	 *
	 * @return Returns Object which must be cast appropriately. Returns null if no more objects are available.
	 */
	public Object nextElement();

}

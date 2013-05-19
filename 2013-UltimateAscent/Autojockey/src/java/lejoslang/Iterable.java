package java.lejoslang;

import java.lejosutil.Iterator;

/**
 * Interface needed by Java foreach loops. It just provides an Iterator.
 *
 * @author Sven Köhler
 * @param <E> type of the elements
 */
public interface Iterable
{
	Iterator iterator();
}

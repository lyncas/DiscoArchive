package java.lejosutil;

import java.lejoslang.Iterable;

/**
 * @author Sven Köhler
 * @param <Object> type of the elements
 */
public interface Collection extends Iterable
{
	boolean add(Object e);
	boolean remove(Object o);

	boolean addAll(Collection c);
	boolean removeAll(Collection c);
	boolean retainAll(Collection c);

	boolean contains(Object o);
	boolean containsAll(Collection c);

	boolean equals(Object o);
	int hashCode();

	int size();
	boolean isEmpty();
	void clear();

	Iterator iterator();

	Object[] toArray();
	 Object[] toArray(Object[] a);
}

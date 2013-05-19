package java.lejosutil;

/**
 * @author Sven Köhler
 * @param <Object> type of the elements
 */
public interface ListIterator extends Iterator
{
	int nextIndex();
	int previousIndex();

	boolean hasPrevious();
	Object previous();

	void add(Object e);
	void set(Object e);
}

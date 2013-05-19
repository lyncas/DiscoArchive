package java.lejosutil;

/**
 * @author Sven Köhler
 * @param <Object> type of the elements
 */
public interface List extends Collection{
	void add(int index, Object e);
	boolean addAll(int index, Collection c);

	Object get(int index);
	Object set(int index, Object e);
	Object remove(int index);

	int indexOf(Object o);
	int lastIndexOf(Object o);

	ListIterator listIterator();
	ListIterator listIterator(int index);

	List subList(int start, int end);
}

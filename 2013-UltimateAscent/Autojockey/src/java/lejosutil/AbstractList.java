package java.lejosutil;

/**
 * This is non-public because it's not compatible with the JDK, yet.
 *
 * @author Sven Köhler
 * @param <E> type of the elements
 */
abstract class AbstractList extends AbstractCollection implements List
{
	protected transient int modCount = 0;

	public boolean contains(Object o)
	{
		return this.indexOf(o) >= 0;
	}


	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof List))
			return false;

		Iterator it1 = this.iterator();
		Iterator it2 = ((List)o).iterator();

		boolean n1, n2;
		while (true)
		{
			n1 = it1.hasNext();
			n2 = it2.hasNext();
			if (!n1 || !n2)
				break;

			Object o1 = it1.next();
			Object o2 = it2.next();

			if (o1 != o2 && (o1 == null || o2 == null || !o1.equals(o2)))
			{
				return false;
			}
		}

		return n1 == n2;
	}


	public int hashCode()
	{
		int r = 1;
		Object[] o=this.toArray();
		for (int i=0;i<o.length;i++)
			r = 31 * r + (o == null ? 0 : o.hashCode());

		return r;
	}

	public Iterator iterator()
	{
		return this.listIterator();
	}

	public ListIterator listIterator()
	{
		return this.listIterator(0);
	}
}

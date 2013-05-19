package java.lejosutil;


import java.lejoslang.UnsupportedOperationException;

/**
 * This is non-public because it's not compatible with the JDK, yet.
 *
 * @author Sven Köhler
 * @param <E> type of the elements
 */
abstract class AbstractCollection implements Collection
{
	public boolean addAll(Collection c)
	{
		boolean r = false;
		Object[] o=c.toArray();
		for (int i=0;i<o.length;i++) {

		    r |= this.add(o[i]);
		}

		return r;
	}

	public boolean containsAll(Collection c)
	{
	    Object[] o=c.toArray();
		for (int i=0;i<o.length;i++)
			if (!this.contains(o[i]))
				return false;

		return true;
	}

	public boolean isEmpty()
	{
		return this.size() <= 0;
	}

	public boolean removeAll(Collection c)
	{
		boolean r = false;
		Object[] o=c.toArray();
		for (int i=0;i<o.length;i++)
			r |= this.remove(o[i]);

		return r;
	}

	public boolean retainAll(Collection c)
	{
		boolean r = false;
		for (Iterator i = this.iterator(); i.hasNext();)
		{
			Object element = i.next();
			if (!c.contains(element))
			{
				r = true;
				i.remove();
			}
		}

		return r;
	}

	public Object[] toArray()
	{
		int size = this.size();
		return this.toArray(new Object[size]);
	}

	public Object[] toArray(Object[] dest)
	{
		int j = 0;
		int max = dest.length;

		for (Iterator i = this.iterator(); i.hasNext();)
		{
		    Object element = i.next();
			if (j >= max)
				throw new UnsupportedOperationException("Array is too small and expanding is not supported.");

			//whether elements are compatible with dest can only be checked at runtime
			dest[j++] = element;
		}

		return dest;
	}


	public String toString()
    {
    	String sb = new String();
    	sb+='[';

    	Iterator it = this.iterator();
    	if (it.hasNext())
    	{
    		sb+=it.next();
    		while (it.hasNext())
    		{
    			//Note: if <code>this</code> is returned by the iterator, the JDK appends "(this Collection)"
    			//However: this is not documented anywhere, and it workarounds a stack overflow only for very simple scenarios.
    			//Also related: http://stackoverflow.com/questions/995477/why-does-java-tostring-loop-infinitely-on-indirect-cycles
    			sb+=", ";
    			sb+=it.next();
			}
    	}
    	sb+=']';
    	return sb.toString();
    }
}

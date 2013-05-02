package code.pliant.common.core.order;

import java.util.Comparator;

/**
 * A generic natural ordering Comparator for the {@link Orderable} interface.
 * 
 * @author Daniel Rugg
 */
public class OrderableComparator implements Comparator<Orderable> {

	/**
	 * Singleton instance.
	 */
	public static final OrderableComparator SELF = new OrderableComparator();
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Orderable o1, Orderable o2) {
		if(o1 == null || o2 == null){
			if(o1 != null) return 1;
			if(o2 != null) return -1;
			return 0;
		}
		int order1 = o1.getOrder();
		int order2 = o2.getOrder();
		return order1 - order2;
	}
}

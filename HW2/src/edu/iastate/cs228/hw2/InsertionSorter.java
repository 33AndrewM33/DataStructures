package edu.iastate.cs228.hw2;

/**
 *  
 * @author Andrew Meder
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		this.algorithm = "insertion sort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		int n = points.length;
		Point candidate;
		for (int i = 1; i < n; i++) {
			candidate = points[i];
			int j = i - 1;
			while (j > -1 && pointComparator.compare(candidate, points[j]) > 0) {
				points[j + 1] = points[j];
				j--;
			}
			points[j + 1] = candidate;
		}
	}
}

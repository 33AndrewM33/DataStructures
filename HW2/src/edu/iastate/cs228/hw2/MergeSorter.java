package edu.iastate.cs228.hw2;

/**
 *  
 * @author Andrew Meder
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		this.algorithm = "mergesort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		if (n <= 1) {
			return;
		}
		int mid = n / 2;
		Point[] left = new Point[mid];
		Point[] right = new Point[n - mid];

		for (int i = 0; i < mid; i++) {
			left[i] = pts[i];
		}
		for (int j = mid; j < n; j++) {
			right[j - mid] = pts[j];
		}

		mergeSortRec(left);
		mergeSortRec(right);
		merge(pts, left, right, left.length, right.length);
	}

	private void merge(Point[] pts, Point[] left, Point[] right, int l, int r) {
		int i = 0, j = 0, k = 0;
		while (i < l && j < r) {
			if (this.pointComparator.compare(left[i], right[j]) >= 0) { // if right[j] - left[i] >= 0
				pts[k++] = left[i++]; // next point comes from left array
			} else {
				pts[k++] = right[j++]; // next point comes from right array
			}
		}
		// for any missed points
		while (i < l) {
			pts[k++] = left[i++];
		}
		while (j < r) {
			pts[k++] = right[j++];
		}
	}

}

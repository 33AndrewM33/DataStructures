package edu.iastate.cs228.hw2;

/**
 * @author Andrew Meder
 */

import java.util.Comparator;

public class CompareY implements Comparator<Point> {

	/**
	 * @return - >0 if p2 y is larger, <0 if p2 y is smaller, 0 if p2 and p1 have
	 *         same y-value
	 */
	@Override
	public int compare(Point p1, Point p2) {
		return p2.getY() - p1.getY();
	}

}

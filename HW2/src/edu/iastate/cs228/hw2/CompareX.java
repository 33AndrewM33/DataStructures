package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * 
 * @author Andrew Meder
 *
 */

public class CompareX implements Comparator<Point> {

	/**
	 * @return - >0 if p2 x is larger, <0 if p2 x is smaller, 0 if p2 and p1 have
	 *         same x-value
	 */
	@Override
	public int compare(Point p1, Point p2) {
		return p2.getX() - p1.getX();
	}

}

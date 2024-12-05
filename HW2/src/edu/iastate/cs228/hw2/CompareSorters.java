package edu.iastate.cs228.hw2;

/**
 *  
 * @author Andrew Meder
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		// Comparing the efficiency of different sorting methods.
		PointScanner[] scanners = new PointScanner[4];
		Scanner input = new Scanner(System.in); // Scanner for user input
		// 10 rounds of testing
		for (int r = 1; r < 11; r++) {
			System.out.println("Keys: 1(random integers) | 2(file input) | 3(exit)");
			int userIn = input.nextInt();
			if (userIn == 1) {
				System.out.println("Trial " + r); // output trial number
				System.out.print("Enter number of random points: ");
				int numPoints = input.nextInt();
				Random rand = new Random();
				Point[] points = generateRandomPoints(numPoints, rand); // using generateRandomPoints for however many
																		// user specifies
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort); // SelectionSort PointScanner
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort); // InsertionSort PointScanner
				scanners[2] = new PointScanner(points, Algorithm.MergeSort); // MergeSort PointScanner
				scanners[3] = new PointScanner(points, Algorithm.QuickSort); // QuickSort PointScanner

				for (int i = 0; i < scanners.length; i++) {
					scanners[i].scan(); // calling scan for each sorting method
				}
				// Output stats for each sorting method along with the size of the Point array
				System.out.println("algorithm	 size	time(ns)");
				System.out.println("-------------------------------------");
				System.out.println(scanners[0].stats());
				System.out.println(scanners[1].stats());
				System.out.println(scanners[2].stats());
				System.out.println(scanners[3].stats());
				System.out.println("-------------------------------------");

			} else if (userIn == 2) {
				System.out.println("Trial " + r);
				System.out.print("Enter file name: ");
				String fileName = input.next(); // getting file name from user
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort); // SelectionSort PointScanner
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort); // InsertionSort PointScanner
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort); // MergeSort PointScanner
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort); // QuickSort PointScanner

				for (int i = 0; i < scanners.length; i++) {
					scanners[i].scan();
				}
				System.out.println("algorithm	 size	time(ns)");
				System.out.println("-------------------------------------");
				System.out.println(scanners[0].stats());
				System.out.println(scanners[1].stats());
				System.out.println(scanners[2].stats());
				System.out.println(scanners[3].stats());
				System.out.println("-------------------------------------");
			} else if (userIn == 3) {
				break; // Breaks loop and ends program
			} else {
				System.out.println("INVALID INPUT"); // for if user ever inputs anything other than 1,2, or 3.
			}
		}

		input.close(); // Closing input scanner

	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts >= 1) {
			Point[] points = new Point[numPts];
			int x, y;
			for (int i = 0; i < numPts; i++) {
				// generating random points for numPts times.
				// All points between [-50,50]
				x = rand.nextInt(101) - 50;
				y = rand.nextInt(101) - 50;
				points[i] = new Point(x, y);
			}
			return points;
		} else {
			throw new IllegalArgumentException(); // if points less than 1 throws IllegalArgumentException
		}
	}

}

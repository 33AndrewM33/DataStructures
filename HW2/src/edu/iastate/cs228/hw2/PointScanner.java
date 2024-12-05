package edu.iastate.cs228.hw2;

/**
 * 
 * @author Andrew Meder
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		this.points = pts;
		this.sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName - name of input file
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		File f = new File(inputFileName);
		Scanner scnr = new Scanner(f);
		this.sortingAlgorithm = algo;
		int i = 0;
		// Checking for odd number of ints
		while (scnr.hasNextLine()) {
			while (scnr.hasNextInt()) {
				scnr.nextInt();
				i++;
			}
			if (scnr.hasNextLine()) {
				scnr.nextLine();
			}
		}

		scnr.close();

		// if odd number of integers in the file
		if (i % 2 != 0) {
			throw new InputMismatchException();
		}

		Scanner scnr2 = new Scanner(f);
		// Declaring and Populating points array
		this.points = new Point[i / 2];
		i = 0;
		while (scnr2.hasNextLine()) {
			while (scnr2.hasNextInt()) {
				int x = scnr2.nextInt();
				int y = scnr2.nextInt();
				this.points[i] = new Point(x, y);
				i++;
			}
			if (scnr2.hasNextLine()) {
				scnr2.nextLine();
			}
		}
		scnr2.close();
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		AbstractSorter aSorter;
		if (this.sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(this.points);
		} else if (this.sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(this.points);
		} else if (this.sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(this.points);
		} else {
			aSorter = new SelectionSorter(this.points);
		}

		// start time
		long start = System.nanoTime();

		// round 1
		aSorter.setComparator(0);
		aSorter.sort();
		int x = aSorter.getMedian().getX();

		// round 2
		aSorter.setComparator(1);
		aSorter.sort();
		int y = aSorter.getMedian().getY();

		// set scanTime and medianCoordinatePoint
		this.medianCoordinatePoint = new Point(x, y);
		long end = System.nanoTime();
		this.scanTime = end - start;

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		if (this.sortingAlgorithm == Algorithm.InsertionSort || this.sortingAlgorithm == Algorithm.SelectionSort) {
			return this.sortingAlgorithm.toString() + "    " + this.points.length + "    " + this.scanTime;
		} else {
			return this.sortingAlgorithm.toString() + "	 " + this.points.length + "    " + this.scanTime;
		}
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		return "MCP: " + this.medianCoordinatePoint.toString();
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile(String outputFileName) throws FileNotFoundException {
		File f = new File(outputFileName);
		try {
			FileWriter writer = new FileWriter(f);
			for (int i = 0; i < this.points.length; i++) {
				writer.write(this.points[i].toString());
				if (i != this.points.length - 1) {
					writer.write("\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

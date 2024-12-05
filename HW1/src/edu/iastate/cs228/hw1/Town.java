package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Andrew Meder
 *
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		this.grid = new TownCell[this.length][this.width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simply throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File f = new File(inputFileName);
		Scanner scnr = new Scanner(f);
		this.length = scnr.nextInt();
		this.width = scnr.nextInt();
		this.grid = new TownCell[this.length][this.width];
		scnr.nextLine();

		int currRow = 0;
		int currCol = 0;

		while (scnr.hasNextLine() && currRow <= this.length) {
			String line = scnr.nextLine();
			Scanner read = new Scanner(line);
			while (read.hasNext()) {
				String c = read.next();
				if (c.equals("C")) {
					grid[currRow][currCol] = new Casual(this, currRow, currCol);
				} else if (c.equals("O")) {
					grid[currRow][currCol] = new Outage(this, currRow, currCol);
				} else if (c.equals("S")) {
					grid[currRow][currCol] = new Streamer(this, currRow, currCol);
				} else if (c.equals("E")) {
					grid[currRow][currCol] = new Empty(this, currRow, currCol);
				} else if (c.equals("R")) {
					grid[currRow][currCol] = new Reseller(this, currRow, currCol);
				} else {
					System.out.println("ERROR: Invalid file format!");
				}
				currCol++;
			}
			currRow++;
			currCol = 0;
			read.close();
		}

		scnr.close();

	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		int choice = -1;
		Random rand = new Random(seed);
		for (int i = 0; i < this.length; i++) {
			for (int j = 0; j < this.width; j++) {
				choice = rand.nextInt(5);
				if (choice == 0) {
					grid[i][j] = new Reseller(this, i, j);
				} else if (choice == 1) {
					grid[i][j] = new Empty(this, i, j);
				} else if (choice == 2) {
					grid[i][j] = new Casual(this, i, j);
				} else if (choice == 3) {
					grid[i][j] = new Outage(this, i, j);
				} else if (choice == 4) {
					grid[i][j] = new Streamer(this, i, j);
				} else {
					System.out.println("Invalid Grid Assignment");
				}
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < this.length; i++) {
			for (int j = 0; j < this.width; j++) {
				if (this.grid[i][j].who() == State.CASUAL) {
					s += "C";
				} else if (this.grid[i][j].who() == State.OUTAGE) {
					s += "O";
				} else if (this.grid[i][j].who() == State.RESELLER) {
					s += "R";
				} else if (this.grid[i][j].who() == State.STREAMER) {
					s += "S";
				} else if (this.grid[i][j].who() == State.EMPTY) {
					s += "E";
				} else {
					System.out.println("Invalid Character");
				}
				// only add a space if not at the end of the row.
				if (j != this.width - 1) {
					s += " ";
				}
			}
			if (i != this.length - 1) {
				s += "\n";
			}
		}
		return s;
	}
}

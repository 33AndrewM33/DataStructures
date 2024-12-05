package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Andrew Meder
 *
 *         The ISPBusiness class performs simulation over a grid plain with
 *         cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int i = 0; i < tNew.getLength(); i++) {
			for (int j = 0; j < tNew.getWidth(); j++) {
				tOld.grid[i][j].census(TownCell.nCensus);
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for (int i = 0; i < town.getLength(); i++) {
			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {
					profit++;
				}
			}

		}
		return profit;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
		int userChoice = scnr.nextInt();
		double totalProfit = 0;
		double bestProfit = -1;
		if (userChoice == 1) {
			System.out.print("Please enter file path: ");
			String path = scnr.next();
			Town t = new Town(path);
			bestProfit = t.getLength() * t.getWidth();
			System.out.println("Start: ");
			for (int i = 0; i < 12; i++) {
				if (i != 0) {
					System.out.println("After itr: " + i);
				}
				System.out.println(t.toString());
				totalProfit += getProfit(t);
				System.out.println("Profit: " + getProfit(t));
				System.out.print("\n");
				t = updatePlain(t);
			}

		} else if (userChoice == 2) {
			System.out.println("Provide rows, cols and seed integer seperated by spaces: ");
			int rows = scnr.nextInt();
			int cols = scnr.nextInt();
			int seed = scnr.nextInt();
			Town t2 = new Town(rows, cols);
			t2.randomInit(seed);
			bestProfit = t2.getLength() * t2.getWidth();
			System.out.println("Start: ");
			for (int i = 0; i < 12; i++) {
				if (i != 0) {
					System.out.println("After itr: " + i);
				}
				System.out.println(t2.toString());
				totalProfit += getProfit(t2);
				System.out.println("Profit: " + getProfit(t2));
				System.out.print("\n");
				t2 = updatePlain(t2);
			}

		} else {
			System.out.println("Invalid Choice!");
		}

		System.out.printf("%.2f", (totalProfit * 100) / (bestProfit * 12));
		System.out.print("%");

		scnr.close();
	}
}

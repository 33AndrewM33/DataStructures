package edu.iastate.cs228.hw4;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * @author Andrew Meder
 *
 */

public class Test {

	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enter filename to decode:");
		String filename = scnr.nextLine();
		scnr.close();

		String s = new String(Files.readAllBytes(Paths.get(filename))).trim();
		int index = s.lastIndexOf('\n');
		String pattern = s.substring(0, index);
		String binaryCode = s.substring(index).trim();
		String pattern2 = "";

		// removing ^
		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) != '^') {
				pattern2 += pattern.charAt(i);
			}
		}

		MsgTree root = new MsgTree(pattern);
		MsgTree.printCodes(root, pattern2);
		root.decode(root, binaryCode);

	}

}

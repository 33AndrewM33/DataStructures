package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class TownTest {

	@Test
	void testToString() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		String expected = "O R O R\nE E C O\nE S O S\nE O R R";
		assertEquals(expected, t.toString());
	}

	@Test
	void testTown() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		String expected = "";
		for (int i = 0; i < t.getLength(); i++) {
			for (int j = 0; j < t.getWidth(); j++) {
				if (t.grid[i][j].who() == State.CASUAL) {
					expected = "C";
				} else if (t.grid[i][j].who() == State.EMPTY) {
					expected = "E";
				} else if (t.grid[i][j].who() == State.RESELLER) {
					expected = "R";
				} else if (t.grid[i][j].who() == State.STREAMER) {
					expected = "S";
				} else if (t.grid[i][j].who() == State.OUTAGE) {
					expected = "O";
				}
				assertEquals(expected, t.grid[i][j].toString());
			}
		}
	}

}

package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class CasualTest {

	@Test
	void who() {
		Town t = new Town(4, 4);
		Casual c = new Casual(t, 0, 0);
		assertEquals(State.CASUAL, c.who());
	}

	@Test
	void next() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[1][2].census(TownCell.nCensus);
		TownCell c = t.grid[1][2].next(t);
		assertEquals(State.OUTAGE, c.who());
	}

}

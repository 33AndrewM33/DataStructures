package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class OutageTest {

	@Test
	void who() {
		Town t = new Town(4, 4);
		Outage o = new Outage(t, 0, 0);
		assertEquals(State.OUTAGE, o.who());
	}

	@Test
	void next() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[1][2].census(TownCell.nCensus);
		TownCell o = t.grid[0][0].next(t);
		assertEquals(State.EMPTY, o.who());
	}

}

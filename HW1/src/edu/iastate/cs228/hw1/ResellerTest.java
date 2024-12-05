package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class ResellerTest {

	@Test
	void who() {
		Town t = new Town(4, 4);
		Reseller r = new Reseller(t, 0, 0);
		assertEquals(State.RESELLER, r.who());
	}

	@Test
	void next() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[0][1].census(TownCell.nCensus);
		TownCell c = t.grid[0][1].next(t);
		assertEquals(State.EMPTY, c.who());
	}

}

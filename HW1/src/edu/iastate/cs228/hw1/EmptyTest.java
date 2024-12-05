package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class EmptyTest {

	@Test
	void who() {
		Town t = new Town(4, 4);
		Empty e = new Empty(t, 0, 0);
		assertEquals(State.EMPTY, e.who());
	}

	@Test
	void next() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		TownCell c = t.grid[0][0].next(t);
		assertEquals(State.EMPTY, c.who());
	}

}

package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class StreamerTest {

	@Test
	void who() {
		Town t = new Town(4, 4);
		Streamer s = new Streamer(t, 0, 0);
		assertEquals(State.STREAMER, s.who());
	}

	@Test
	void next() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[2][1].census(TownCell.nCensus);
		TownCell c = t.grid[2][1].next(t);
		assertEquals(State.OUTAGE, c.who());
	}

}

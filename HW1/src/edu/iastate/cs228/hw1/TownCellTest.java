package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class TownCellTest {

	@Test
	void census() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[0][0].census(TownCell.nCensus);
		assertEquals(TownCell.nCensus[TownCell.RESELLER], 1);
		assertEquals(TownCell.nCensus[TownCell.EMPTY], 2);
	}
	
	@Test
	void census2() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		t.grid[1][1].census(TownCell.nCensus);
		assertEquals(TownCell.nCensus[TownCell.RESELLER], 1);
		assertEquals(TownCell.nCensus[TownCell.EMPTY], 2);
		assertEquals(TownCell.nCensus[TownCell.OUTAGE], 3);
		assertEquals(TownCell.nCensus[TownCell.STREAMER], 1);
		assertEquals(TownCell.nCensus[TownCell.CASUAL], 1);
	}

}

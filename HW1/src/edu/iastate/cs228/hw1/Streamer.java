package edu.iastate.cs228.hw1;

/**
 * 
 * @author Andrew Meder
 *
 */

public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1)
			return new Reseller(tNew, this.row, this.col); // rule 6a
		if (nCensus[RESELLER] > 0)
			return new Outage(tNew, this.row, this.col); // rule 2a
		if (nCensus[OUTAGE] > 0)
			return new Empty(tNew, this.row, this.col); // rule 2b

		return new Streamer(tNew, this.row, this.col); // remains unchanged
	}

	@Override
	public String toString() {
		return "S"; // for debugging
	}

}

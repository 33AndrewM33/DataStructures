package edu.iastate.cs228.hw1;

/**
 * 
 * @author Andrew Meder
 *
 */

public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1)
			return new Reseller(tNew, this.row, this.col); // rule 6a
		if (nCensus[RESELLER] > 0)
			return new Outage(tNew, this.row, this.col); // rule 1a
		if (nCensus[STREAMER] > 0)
			return new Streamer(tNew, this.row, this.col); // rule 1b
		if (nCensus[CASUAL] >= 5)
			return new Streamer(tNew, this.row, this.col); // rule 6b

		return new Casual(tNew, this.row, this.col); // remains unchanged
	}

	@Override
	public String toString() {
		return "C"; // for debugging
	}

}

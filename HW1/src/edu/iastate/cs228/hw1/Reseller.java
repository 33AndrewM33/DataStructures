package edu.iastate.cs228.hw1;

/**
 * 
 * @author Andrew Meder
 *
 */

public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		if (nCensus[CASUAL] <= 3)
			return new Empty(tNew, this.row, this.col); // rule 3a
		if (nCensus[EMPTY] >= 3)
			return new Empty(tNew, this.row, this.col); // rule 3b
		if (nCensus[CASUAL] >= 5)
			return new Streamer(tNew, this.row, this.col); // rule 6b

		return new Reseller(tNew, this.row, this.col); // remains unchanged
	}

	@Override
	public String toString() {
		return "R"; // for debugging
	}

}

package edu.iastate.cs228.hw1;

/**
 * 
 * @author Andrew Meder
 *
 */

public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1)
			return new Reseller(tNew, this.row, this.col); // rule 6a

		return new Casual(tNew, this.row, this.col); // rule 5
	}

	@Override
	public String toString() {
		return "E"; // for debugging
	}

}

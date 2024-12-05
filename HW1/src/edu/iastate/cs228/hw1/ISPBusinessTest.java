package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class ISPBusinessTest {

	@Test
	void getProfit() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		assertEquals(ISPBusiness.getProfit(t), 1);
	}

	@Test
	void updatePlain() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		assertEquals(ISPBusiness.getProfit(t), 1);
		Town t2 = ISPBusiness.updatePlain(t);
		assertEquals(ISPBusiness.getProfit(t2), 4);
		Town t3 = ISPBusiness.updatePlain(t2);
		assertEquals(ISPBusiness.getProfit(t3), 12);
	}

}

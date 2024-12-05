package edu.iastate.cs228.hw3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoutListTest {
	StoutList<Integer> stouts = new StoutList<Integer>(4);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		stouts.add(1);
		stouts.add(2);
		stouts.add(3);
		stouts.add(4);
		stouts.add(5);
		stouts.add(6);
		
	}
	
	@Test
	void testSize() {
		int size = stouts.size();
		assertEquals(6, size);
		
	}

	@Test
	void testStoutList() {
		stouts = new StoutList<Integer>(4);
	}

	@Test
	void testStoutListInt() {
		fail("Not yet implemented");
	}

	@Test
	void testStoutListNodeNodeIntInt() {
		fail("Not yet implemented");
	}

	@Test
	void testAddE() {
		stouts = new StoutList<Integer>(4);
		
		stouts.add(1);
		stouts.add(2);
		stouts.add(3);
		stouts.add(4);
		stouts.add(5);
		stouts.add(6);
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 3, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
	}

	@Test
	void testAddIntE() {
		stouts.add(2, 0);
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		stouts.add(7, 7);
		stouts.add(8, 8);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8)]";
		assertEquals(shouldBe, output);
		
		stouts.add(9, 9);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8), (9, -, -, -)]";
		assertEquals(shouldBe, output); 
	}
	
	@Test
	void testSet() {
		stouts.set(2, 0);
		
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 0, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		ListIterator<Integer> iter = stouts.listIterator();
		iter.next();
		iter.set(9);
		
		output = stouts.toStringInternal();
		shouldBe = "[(9, 2, 0, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		iter.next();
		iter.previous();
		iter.set(8);
		
		output = stouts.toStringInternal();
		shouldBe = "[(9, 8, 0, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		iter.next();
		iter.next();
		iter.next();
		iter.set(5);
		iter.set(5);
		
		output = stouts.toStringInternal();
		shouldBe = "[(9, 8, 0, 5), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
	}

	@Test
	void testRemoveInt() {
		stouts.add(2, 0);
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		stouts.add(7, 7);
		stouts.add(8, 8);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8)]";
		assertEquals(shouldBe, output);
		
		stouts.add(9, 9);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8), (9, -, -, -)]";
		assertEquals(shouldBe, output); 
		
		stouts.remove(9);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8)]";
		assertEquals(shouldBe, output); 
		
		stouts.remove(4);
		stouts.remove(4);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 6, -, -), (7, 8, -, -)]";
		assertEquals(shouldBe, output); 
		
		stouts.remove(4);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 7, 8, -)]";
		assertEquals(shouldBe, output); 
		
		
	}

	@Test
	void testSort() {
		stouts.add(2, 0);
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		stouts.add(7, 7);
		stouts.add(8, 8);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8)]";
		assertEquals(shouldBe, output);
		
		stouts.sort();
		
		output = stouts.toStringInternal();
		shouldBe = "[(0, 1, 2, 3), (4, 5, 6, 7), (8, -, -, -)]";
		assertEquals(shouldBe, output);
		
	}

	@Test
	void testSortReverse() {
		stouts.add(2, 0);
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		stouts.add(7, 7);
		stouts.add(8, 8);
		
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 0, -), (3, 4, -, -), (5, 6, 7, 8)]";
		assertEquals(shouldBe, output);
		
		stouts.sortReverse();
		
		output = stouts.toStringInternal();
		shouldBe = "[(8, 7, 6, 5), (4, 3, 2, 1), (0, -, -, -)]";
		assertEquals(shouldBe, output);
	}

	@Test
	void testIterator() {
		ListIterator<Integer> iter = stouts.listIterator();
		
		String output = stouts.toStringInternal();
		String shouldBe = "[(1, 2, 3, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		assertEquals(6, stouts.size());
		
		int value = iter.next();
		assertEquals(1, value);
		
		iter.remove();
		output = stouts.toStringInternal();
		shouldBe = "[(2, 3, 4, -), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
		
		iter.add(1);
		output = stouts.toStringInternal();
		shouldBe = "[(1, 2, 3, 4), (5, 6, -, -)]";
		assertEquals(shouldBe, output);
	}

	@Test
	void testListIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testListIteratorInt() {
		fail("Not yet implemented");
	}

	@Test
	void testToStringInternal() {
		fail("Not yet implemented");
	}

	@Test
	void testToStringInternalListIteratorOfE() {
		fail("Not yet implemented");
	}

}

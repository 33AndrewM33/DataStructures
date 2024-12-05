package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Andrew Meder
 */

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Searches through the list for a duplicate before adding an item.
	 * 
	 * @param item - item to search for
	 * @return - true if in list, false otherwise.
	 */
	public boolean contains(E item) {
		if (this.size < 1) {
			return false;
		}

		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				if (temp.data[i].equals(item)) {
					return true;
				}
			}
			temp = temp.next;
		}

		return false;

	}

	/**
	 * Adds/appends item to the end of the list
	 * 
	 * @param item - item to add
	 */
	@Override
	public boolean add(E item) {
		if (item == null) {
			throw new NullPointerException();
		}

		if (this.contains(item)) {
			return false;
		}

		if (this.size == 0) {
			Node n = new Node();
			n.addItem(item);
			head.next = n;
			n.previous = head;
			n.next = tail;
			tail.previous = n;
		} else {
			if (tail.previous.count < nodeSize) {
				tail.previous.addItem(item);
			} else {
				Node n = new Node();
				n.addItem(item);
				Node temp = tail.previous;
				temp.next = n;
				n.previous = temp;
				n.next = tail;
				tail.previous = n;

			}
		}

		size++;
		return true;
	}

	/**
	 * Adds item at user-specified index
	 * 
	 * @param pos  - index in list to add
	 * @param item - item to add
	 */
	@Override
	public void add(int pos, E item) {
		// check if index out of bounds
		if (pos < 0 || pos > this.size) {
			throw new IndexOutOfBoundsException();
		}

		// check if list is empty, new node at offset 0
		if (this.size == 0) {
			add(item);
		}

		NodeInfo findN = find(pos);
		Node temp = findN.node;
		int off = findN.offset;

		if (off == 0) {
			if (temp.previous != head && temp.previous.count < nodeSize) {
				temp.previous.addItem(item);
				size++;
				return;
			} else if (temp == tail) {
				add(item);
				size++;
				return;
			}
		}

		if (temp.count < nodeSize) {
			temp.addItem(off, item);
		} else {
			Node newNext = new Node();
			int half = nodeSize / 2;
			int c = 0;
			while (c < half) {
				newNext.addItem(temp.data[half]);
				temp.removeItem(half);
				c++;
			}

			Node oldNext = temp.next;

			temp.next = newNext;
			newNext.previous = temp;
			newNext.next = oldNext;
			oldNext.previous = newNext;

			if (off <= nodeSize / 2) {
				temp.addItem(off, item);
			} else if (off > nodeSize / 2) {
				newNext.addItem((off - nodeSize / 2), item);
			}
		}
		size++;
	}

	/**
	 * Removes an item at a specific index.
	 * 
	 * @param pos - position of item to be removed
	 * @return - item that was removed
	 */
	@Override
	public E remove(int pos) {
		if (pos > size || pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		NodeInfo findN = find(pos);
		Node temp = findN.node;
		int off = findN.offset;
		E nodeVal = temp.data[off];

		if (temp.next == tail && temp.count == 1) {
			Node prev = temp.previous;
			prev.next = temp.next;
			temp.next.previous = prev;
			temp = null;
		} else if (temp.next == tail || temp.count > nodeSize / 2) {
			temp.removeItem(off);
		} else {
			temp.removeItem(off);
			Node next = temp.next;
			if (next.count > nodeSize / 2) {
				temp.addItem(next.data[0]);
				next.removeItem(0);
			} else if (next.count <= nodeSize / 2) {
				for (int i = 0; i < next.count; i++) {
					temp.addItem(next.data[i]);
				}
				temp.next = next.next;
				next.next.previous = temp;
				next = null;
			}
		}
		size--;
		return nodeVal;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] dataList = (E[]) new Comparable[size];

		int tempI = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				dataList[tempI] = temp.data[i];
				tempI++;
			}
			temp = temp.next;
		}

		head.next = tail;
		tail.previous = head;

		insertionSort(dataList, new DataComparator());
		size = 0;
		for (int j = 0; j < dataList.length; j++) {
			add(dataList[j]);
		}
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		E[] dataList = (E[]) new Comparable[size];

		int tempI = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				dataList[tempI] = temp.data[i];
				tempI++;
			}
			temp = temp.next;
		}

		head.next = tail;
		tail.previous = head;

		bubbleSort(dataList);
		size = 0;
		for (int j = 0; j < dataList.length; j++) {
			add(dataList[j]);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
			// useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	private NodeInfo find(int pos) {
		Node temp = head.next;
		int tempIndex = 0;
		while (temp != tail) {
			if (tempIndex + temp.count < pos) {
				tempIndex += temp.count;
				temp = temp.next;
				continue;
			}
			NodeInfo result = new NodeInfo(temp, pos - tempIndex);
			return result;
		}
		return null;
	}

	private class StoutListIterator implements ListIterator<E> {
		final int LEFT = 0;
		final int RIGHT = 1;

		public int curIndex; // cursor index
		public int direction; // 1: Rightward, 0: Leftward, -1: Hasn't moved yet
		public E data[]; // linked list in array format

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			curIndex = 0;
			direction = -1;
			ArrayFormat();
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos - index of node
		 */
		public StoutListIterator(int pos) {
			curIndex = pos;
			direction = -1;
			ArrayFormat();
		}

		@Override
		public boolean hasNext() {
			return curIndex < size;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			direction = RIGHT;
			return data[curIndex++];
		}

		@Override
		public void remove() {
			if (direction == RIGHT) {
				StoutList.this.remove(curIndex - 1);
				direction = -1;
				curIndex--;
				if (curIndex < 0) {
					curIndex = 0;
				}
			} else if (direction == LEFT) {
				StoutList.this.remove(curIndex);
				ArrayFormat();
				direction = -1;
			} else {
				throw new IllegalArgumentException();
			}
		}

		@Override
		public boolean hasPrevious() {
			return curIndex > 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			direction = LEFT;
			curIndex--;
			return data[curIndex];
		}

		@Override
		public int nextIndex() {
			return curIndex;
		}

		@Override
		public int previousIndex() {
			return curIndex - 1;
		}

		@Override
		public void set(E e) {
			if (direction == RIGHT) {
				NodeInfo findN = find(curIndex - 1);
				findN.node.data[findN.offset] = e;
				data[curIndex - 1] = e;
			} else if (direction == LEFT) {
				NodeInfo findN = find(curIndex);
				findN.node.data[findN.offset] = e;
				data[curIndex] = e;
			} else {
				throw new IllegalStateException();
			}

		}

		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			StoutList.this.add(curIndex, e);
			curIndex++;
			ArrayFormat();
			direction = -1;
		}

		private void ArrayFormat() {
			data = (E[]) new Comparable[size];
			Node temp = head.next;
			int tempIndex = 0;
			while (temp.next != tail) {
				for (int i = 0; i < temp.count; i++) {
					data[tempIndex] = temp.data[i];
					tempIndex++;
				}
				temp = temp.next;
			}
		}
	}

	/**
	 * Comparator for insertion sort
	 */
	public class DataComparator<E extends Comparable<E>> implements Comparator<E> {

		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}

	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E key = arr[i];
			int j = i - 1;

			while (j >= 0 && comp.compare(arr[j], key) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

}
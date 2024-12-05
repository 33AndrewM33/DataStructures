package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * @author Andrew Meder
 */

public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	private static String binaryCode;

	/**
	 * Constructor to build a tree from a string lastAct cooresponds to the l
	 * 
	 * @param encoding - String from data file
	 */
	public MsgTree(String encoding) {
		if (encoding == null || encoding.length() < 2) {
			return;
		}

		Stack<MsgTree> stack = new Stack<>();
		int index = 0;
		this.payloadChar = encoding.charAt(index++);
		stack.push(this);
		MsgTree curr = this;
		// last action performed either in, or out from the stack, 0 = in, 1 = out.
		int lastAct = 0;
		while (index < encoding.length()) {
			MsgTree node = new MsgTree(encoding.charAt(index++));
			if (lastAct == 0) {
				curr.left = node;
				if (node.payloadChar == '^') {
					curr = stack.push(node);
					lastAct = 0;
				} else {
					if (!stack.empty()) {
						curr = stack.pop();
					}
					lastAct = 1;
				}
			} else { // lastAct == 1 (out)
				curr.right = node;
				if (node.payloadChar == '^') {
					curr = stack.push(node);
					lastAct = 0;
				} else {
					if (!stack.empty()) {
						curr = stack.pop();
					}
					lastAct = 1;
				}
			}
		}
	}

	/**
	 * Constructor for a node with null children
	 * 
	 * @param payload
	 */
	public MsgTree(char payload) {
		this.payloadChar = payload;
		this.left = null;
		this.right = null;
	}

	/**
	 * Gets the code and recursively calls itself, which sets the alphabet
	 * 
	 * @param root
	 * @param c
	 * @param path
	 * @return
	 */
	private static boolean getCode(MsgTree root, char c, String path) {
		if (root != null) {
			if (root.payloadChar == c) {
				binaryCode = path;
				return true;
			}
			return getCode(root.left, c, path + "0") || getCode(root.right, c, path + "1");
		}
		return false;
	}

	/**
	 * Decodes the message using the pulled code alphabet
	 * 
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
		MsgTree curr = codes;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			if (c == '0') {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
			if (curr.payloadChar != '^') {
				binaryCode = "";
				getCode(codes, curr.payloadChar, binaryCode);
				sb.append(curr.payloadChar);
				curr = codes;
			}
		}
		System.out.println(sb.toString());
		stats(msg, sb.toString());
	}

	/**
	 * Prints characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("charcter code\n--------------------");
		char[] codeArr = code.toCharArray();
		binaryCode = "";
		char c;
		for (int i = 0; i < codeArr.length; i++) {
			c = codeArr[i];
			getCode(root, c, binaryCode);
			if (c == '\n') {
				System.out.println("    " + "\\n" + "    " + binaryCode);
			} else {
				System.out.println("    " + c + " " + "    " + binaryCode);
			}

		}
	}

	/**
	 * Extra Credit Statistics, uses endoed and decoded strings data to print.
	 * 
	 * @param encodedStr - encoded string
	 * @param decodedStr - decoded string
	 */
	private void stats(String encodedStr, String decodedStr) {
		System.out.println("STATISTICS");
		System.out.println(String.format("Avg bits/char:\t%.1f", encodedStr.length() / (double) decodedStr.length()));
		System.out.println("Total Characters:\t" + decodedStr.length());
		System.out.println(String.format("Space Savings:\t%.1f%%",
				(1d - decodedStr.length() / (double) encodedStr.length()) * 100));
	}

}
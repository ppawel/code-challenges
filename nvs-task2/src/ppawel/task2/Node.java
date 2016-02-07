package ppawel.task2;

import java.util.PriorityQueue;

/**
 * Represents a binary tree node. Holds word count as value and implements
 * {@link Comparable} interface in order to be used in a {@link PriorityQueue}.
 *
 */
public class Node implements Comparable<Node> {

	/**
	 * Word count associated with this node.
	 */
	private Long wordCount;

	/**
	 * Left child or <code>null</code> if child does not exist.
	 */
	private Node left;

	/**
	 * Right child or <code>null</code> if child does not exist.
	 */
	private Node right;

	/**
	 * Initializes a node with given word count.
	 * 
	 * @param wordCount
	 *            word count
	 */
	public Node(Long wordCount) {
		this.wordCount = wordCount;
	}

	/**
	 * Initializes a node with given word count and children.
	 * 
	 * @param wordCount
	 *            word count
	 * @param left
	 *            left child
	 * @param right
	 *            right child
	 */
	public Node(Long wordCount, Node left, Node right) {
		this.wordCount = wordCount;
		this.left = left;
		this.right = right;
	}

	/**
	 * Compares this node to given node - value of {@link #wordCount} is used
	 * for comparison according to task specification.
	 */
	@Override
	public int compareTo(Node o) {
		return wordCount.compareTo(o.getWordCount());
	}

	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount(Long wordCount) {
		this.wordCount = wordCount;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}

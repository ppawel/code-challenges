package ppawel.task2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of tree printer - prints to {@link System#out}, output is
 * meant to be viewed with a fixed-width font.
 * 
 * @author ppawel
 *
 */
public class TreePrinter {

	/**
	 * Max node width - max number of characters to reserve for a single tree
	 * node. This is used to properly align nodes.
	 */
	private long maxNodeWidth;

	/**
	 * Height of the tree.
	 */
	private long treeHeight;

	/**
	 * Prints a tree to stdout.
	 * 
	 * @param root
	 *            root node of the tree to print;
	 * @param width
	 *            maximum width (in characters) of a single node content
	 */
	public void print(Node root, long width) {
		this.maxNodeWidth = width;

		// Calculate tree height - needed to calculate subtree width
		treeHeight = getTreeHeight(root);

		// Print the tree level by level, starting at the root node (level 1)
		List<Node> nodes = Collections.singletonList(root);
		for (int level = 1; level < treeHeight; level++) {
			printLevel(nodes, level);
			nodes = getNextLevel(nodes);
		}

		printLastLevel(nodes);
	}

	/**
	 * Prints a list of nodes from the same level to standard output. This
	 * method prints exactly one line with the newline character at the end.
	 * 
	 * @param nodes
	 *            list of nodes, including <code>null</code> values for non
	 *            existing nodes
	 * @param level
	 *            level number
	 */
	private void printLevel(List<Node> nodes, long level) {
		long subtreeHeight = treeHeight - level + 1;
		long subtreeWidth = 0;
		long leafCount = (long) Math.pow(2, subtreeHeight - 1);

		// Calculate width of a subtree at the current level - this is done by
		// calculating the number of leafs of the subtree and adding spaces
		// which separate leafs at the last level.
		subtreeWidth = (long) (maxNodeWidth * leafCount // total width of
														// content of the leaf
														// nodes
				+ leafCount // each leaf is followed by a space + one space to
							// follow the whole subtree
		);

		// Print out preceding spaces in the line - first node of the level must
		// be printed out centered in relation to its subtree.
		printSpaces((long) (subtreeWidth / 2.0 - maxNodeWidth / 2 - 1));

		for (int i = 1; i <= nodes.size(); i++) {
			Node node = nodes.get(i - 1);

			if (node != null) {
				// Print current node
				printNodeValue(node);
			} else {
				// If node doesn't exist, just print empty string to fill the
				// space and keep proper alignment.
				printSpaces(maxNodeWidth);
			}

			// Print out a separator - this moves the printing caret to the next
			// subtree in a centered position in relation to that subtree's
			// width.
			printSpaces(subtreeWidth - maxNodeWidth);
		}

		System.out.println();
	}

	/**
	 * Prints out the last level which is a special case because we assume that
	 * all nodes (leafs) are separated by a single space and there is no
	 * preceding whitespace.
	 * 
	 * @param nodes
	 *            list of nodes for the level
	 */
	private void printLastLevel(List<Node> nodes) {
		for (Node node : nodes) {
			if (node != null) {
				// Print node value
				printNodeValue(node);
			} else {
				// Node doesn't exist - print empty placeholder to keep
				// proper alignment.
				printSpaces(maxNodeWidth);
			}

			// Separator at the last level is always a single space
			System.out.print(" ");
		}

		System.out.println();
	}

	/**
	 * Calculates height of a tree rooted in given node.
	 * 
	 * @param node
	 *            root of the tree
	 * @return calculated height (or 0 if root is <code>null</code>)
	 */
	private long getTreeHeight(Node node) {
		if (node == null) {
			return 0;
		}
		// Recursively calculate height of each subtree, take the higher one and
		// add 1 to account for the current level.
		return Math.max(getTreeHeight(node.getLeft()), getTreeHeight(node.getRight())) + 1;
	}

	/**
	 * Prints out node value ({@link Node#getWordCount()}). Properly aligns the
	 * value according to max node width.
	 * <p>
	 * For example, if node value is 3 and {@link #maxNodeWidth} is 5 then this
	 * method prints out string "  3  ".
	 * 
	 * @param node
	 */
	private void printNodeValue(Node node) {
		long padding = maxNodeWidth - String.valueOf(node.getWordCount()).length();
		printSpaces(padding / 2);
		System.out.print(node.getWordCount());
		printSpaces((long) Math.ceil(padding / 2.0));
	}

	/**
	 * For given nodes, retrieves next level - all child nodes or
	 * <code>null</code> when there are not child nodes.
	 * 
	 * @param nodes
	 *            nodes to process
	 * @return list of child nodes including <code>null</code> values for
	 *         non-existing child nodes
	 */
	private List<Node> getNextLevel(List<Node> nodes) {
		List<Node> result = new LinkedList<>();
		for (Node node : nodes) {
			if (node != null) {
				result.add(node.getLeft());
				result.add(node.getRight());
			} else {
				result.add(null);
				result.add(null);
			}
		}
		return result;
	}

	/**
	 * Prints given number of spaces to stdout.
	 * 
	 * @param count
	 *            number of spaces to print
	 */
	private void printSpaces(long count) {
		for (int i = 0; i < count; i++) {
			System.out.print(" ");
		}
	}
}

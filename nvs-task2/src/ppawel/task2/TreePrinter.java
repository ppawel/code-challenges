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
	 * node (always an odd number).
	 */
	private long maxNodeWidth;

	/**
	 * Height of the tree.
	 */
	private long treeHeight;

	public void print(Node root, long maxNodeWidth) {
		this.maxNodeWidth = maxNodeWidth % 2 == 0 ? (maxNodeWidth + 1) : maxNodeWidth;
		treeHeight = getTreeHeight(root);
		printNodes(Collections.singletonList(root), 1);
	}

	private void printNodes(List<Node> nodes, long level) {
		List<Node> nextLevel = getNextLevel(nodes);

		if (level < treeHeight) {
			printLevel(nodes, level);
			System.out.println();

			if (!isLevelEmpty(nextLevel)) {
				printNodes(nextLevel, level + 1);
			}
		} else {
			printLastLevel(nodes);
		}
	}

	private void printLevel(List<Node> nodes, long level) {
		long subtreeHeight = treeHeight - level + 1;
		long subtreeWidth = 0;

		if (level < treeHeight) {
			subtreeWidth = (long) (maxNodeWidth * Math.pow(2, subtreeHeight - 1) + Math.pow(2, subtreeHeight - 1));
			System.out.print(repeat(" ", (long) (subtreeWidth / 2.0 - maxNodeWidth / 2 - 1)));
		}
		int i = 0;

		for (Node node : nodes) {
			i++;

			String separator = "";

			if (i > 0) {
				separator = repeat(" ", subtreeWidth - maxNodeWidth);
			}

			if (node != null) {
				printNodeValue(node);
				System.out.print(separator);
			} else {
				System.out.print(repeat(" ", maxNodeWidth) + separator);
			}
		}
	}

	private void printLastLevel(List<Node> nodes) {
		for (Node node : nodes) {
			if (node == null) {
				System.out.print(repeat(" ", maxNodeWidth + 1));
				continue;
			}
			printNodeValue(node);
			System.out.print(" ");
		}
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

	private void printNodeValue(Node node) {
		long padding = maxNodeWidth - String.valueOf(node.getWordCount()).length();
		System.out.print(repeat(" ", padding / 2));
		System.out.print(node.getWordCount());
		System.out.print(repeat(" ", padding / 2));
	}

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

	private boolean isLevelEmpty(List<Node> nextLevel) {
		return nextLevel.stream().allMatch(node -> node == null);
	}

	private String repeat(String str, long count) {
		StringBuilder result = new StringBuilder((int) count);
		for (int i = 0; i < count; i++) {
			result.append(str);
		}
		return result.toString();
	}
}

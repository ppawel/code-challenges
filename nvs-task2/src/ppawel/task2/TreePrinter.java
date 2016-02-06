package ppawel.task2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TreePrinter {

	private long treeHeight;

	public void printTree(Node root) {
		treeHeight = getTreeHeight(root);
		printNodes(Collections.singletonList(root), 1);
	}

	private void printNodes(List<Node> nodes, long level) {
		List<Node> nextLevel = new LinkedList<>();
		long subtreeWidth = 0;

		if (level < treeHeight) {
			subtreeWidth = (long) (Math.pow(2, treeHeight - 1 - level) + 5 * Math.pow(2, treeHeight - level - 1) - 1);
		}

		System.out.print(getSpaces((long) Math.ceil(subtreeWidth / 2.0)));

		boolean nextLevelEmpty = true;
		int i = 0;
		for (Node node : nodes) {
			i++;

			String separator = null;

			if (level < treeHeight) {
				separator = getSpaces(subtreeWidth);
			} else {
				separator = i % 2 == 0 ? " " : getSpaces(5);
			}

			if (node == null) {
				System.out.print(separator + " ");
				continue;
			}

			System.out.print(node.getWordCount() + separator);

			nextLevel.add(node.getLeft());
			nextLevel.add(node.getRight());

			if (node.getLeft() != null || node.getRight() != null) {
				nextLevelEmpty = false;
			}
		}

		System.out.println();

		if (!nextLevelEmpty) {
			printNodes(nextLevel, level + 1);
		}
	}

	private long getTreeHeight(Node node) {
		if (node == null) {
			return 0;
		}
		return Math.max(getTreeHeight(node.getLeft()), getTreeHeight(node.getRight())) + 1;
	}

	private String getSpaces(long count) {
		StringBuilder result = new StringBuilder((int) count);
		for (int i = 0; i < count; i++) {
			result.append(" ");
		}
		return result.toString();
	}
}

package ppawel.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Task2 {

	public static void main(String[] args) throws FileNotFoundException {
		Task2 task = runTaskWithFile(new File("/home/ppawel/test.txt"));

		// Print out word counts
		System.out.println(task.getWordCounts());

		// Print out the tree
		new TreePrinter().printTree(task.getRoot());
	}

	public static Task2 runTaskWithFile(File file) throws FileNotFoundException {
		Task2 task = new Task2();
		task.readWordCounts(file);
		task.constructTree();
		return task;
	}

	private Map<String, Long> wordCounts = new HashMap<>();

	private PriorityQueue<Node> nodes = new PriorityQueue<>();

	private Node root;

	public void readWordCounts(File file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			scanner.forEachRemaining(word -> {
				wordCounts.compute(word, (key, count) -> count == null ? 1 : count + 1);
			});
		}
	}

	public void constructTree() {
		wordCounts.forEach((word, count) -> nodes.offer(new Node(count, word)));

		while (!nodes.isEmpty()) {
			Node node1 = nodes.poll();
			Node node2 = nodes.poll();
			if (node2 != null) {
				root = new Node(node1.getWordCount() + node2.getWordCount(), node1, node2);
				nodes.offer(root);
			} else {
				root = node1;
			}
		}
	}

	public Map<String, Long> getWordCounts() {
		return wordCounts;
	}

	public Node getRoot() {
		return root;
	}
}

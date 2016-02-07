package ppawel.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Implementation of task2.
 * <p>
 * Uses a priority queue during tree construction to hold pending nodes - nodes
 * which are still to be connected with other nodes and added to the tree.
 * {@link PriorityQueue} class is implemented using a heap so it provides
 * O(logn) complexity for the operations used in this task - adding new elements
 * and removing an element with the smallest word count in the queue.
 * 
 * @author ppawel
 *
 */
public class Task2 {

	/**
	 * Executes the task using file from command line arguments.
	 * 
	 * @param args
	 *            must contain exactly one argument - path to the file with data
	 *            to be processed
	 * @throws FileNotFoundException
	 *             if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: Task2 <path to file>");
			System.exit(1);
		}

		// Create new task instance and use helper method to execute it
		Task2 task = runTaskWithFile(new File(args[0]));

		// Print out word counts
		System.out.println(task.getWordCounts());

		Long maxWordCount = task.getWordCounts().values().stream().reduce(Long::max).orElse(0l);

		// Print out the tree
		new TreePrinter().print(task.getRoot(), String.valueOf(maxWordCount).length());
	}

	/**
	 * Runs task2 logic using given file.
	 * 
	 * @param file
	 *            file to use
	 * @return {@link Task2} instance containing word counts and tree
	 *         constructed according to task specification
	 * @throws FileNotFoundException
	 *             if given file not found
	 */
	public static Task2 runTaskWithFile(File file) throws FileNotFoundException {
		Task2 task = new Task2();
		task.readWordCounts(file);
		task.constructTree();
		return task;
	}

	/**
	 * Holds a mapping from words to word counts (number of occurrences of a
	 * word in the input text).
	 */
	private Map<String, Long> wordCounts = new HashMap<>();

	/**
	 * Points to the root of the tree after a {@link #constructTree()} call.
	 */
	private Node root;

	/**
	 * Reads tokens from given file and for every unique token sums up the
	 * number of its occurrences.
	 * 
	 * @param file
	 *            file to read
	 * @throws FileNotFoundException
	 *             if givenfile not found
	 */
	public void readWordCounts(File file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			scanner.forEachRemaining(word -> {
				// If word does not exist yet: word count is 1
				// If word exists: word count increments by 1
				wordCounts.compute(word, (key, count) -> count == null ? 1 : count + 1);
			});
		}
	}

	/**
	 * Constructs the tree according to task specification. After calling this
	 * method tree root node can be accessed via {@link #root} or
	 * {@link #getRoot()}.
	 */
	public void constructTree() {
		PriorityQueue<Node> nodeQueue = new PriorityQueue<>();

		// Add new node to the queue for every unique word together with its
		// word count.
		wordCounts.forEach((word, count) -> nodeQueue.offer(new Node(count, word)));

		// Now let's build the tree!
		while (!nodeQueue.isEmpty()) {
			// Take two nodes from the queue - it is a priority queue so by
			// definition we will always get two nodes with the smallest word
			// count (unless the queue contains only one node - read below).

			// First node is always non-null - otherwise while loop would not
			// enter the iteration (means queue was empty).
			Node node1 = nodeQueue.poll();

			// Second node can be null when the queue contained only single node
			// at the start of this loop iteration.
			Node node2 = nodeQueue.poll();

			if (node2 != null) {
				// When we get two nodes, follow the task specification - create
				// a parent node with node1 and node2 as children and
				// appropriate word count.
				Node parent = new Node(node1.getWordCount() + node2.getWordCount(), node1, node2);

				// Add the new parent node to the queue - it needs to be
				// involved in further tree construction.
				nodeQueue.offer(parent);
			} else {
				// When we only get one node from the queue it means that we
				// have completed tree construction in the previous loop
				// iteration. The queue contained only one node and it is the
				// root of the tree. While loop will end (queue is empty now).
				root = node1;
			}
		}
	}

	/**
	 * Returns word counts - useful getter for testing and printing.
	 * 
	 * @return word counts map
	 */
	public Map<String, Long> getWordCounts() {
		return wordCounts;
	}

	/**
	 * Returns root node of the tree - useful getter for testing and printing.
	 * 
	 * @return root node
	 */
	public Node getRoot() {
		return root;
	}
}

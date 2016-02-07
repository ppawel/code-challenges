package ppawel.task2;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Simple test suite for task2. Uses text files from testdata directory.
 * 
 * @author ppawel
 *
 */
public class Tests {

	private static String testDir = "testdata/";

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			// Test dir specified on command line
			testDir = args[0];
		}

		testReferenceExample();
		testOneWord();
		testEmpty();
		testLong();
		testCat();
	}

	public static void testReferenceExample() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File(testDir, "test_example.txt"));
		if (task.getWordCounts().get("had") != 2) {
			throw new IllegalStateException();
		}
		if (task.getWordCounts().get("She") != 1) {
			throw new IllegalStateException();
		}
		if (task.getWordCounts().get("address") != 2) {
			throw new IllegalStateException();
		}
		if (task.getWordCounts().size() != 5) {
			throw new IllegalStateException();
		}
		if (task.getRoot().getWordCount() != 7) {
			throw new IllegalStateException();
		}
	}

	public static void testOneWord() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File(testDir, "test_one_word.txt"));
		if (task.getWordCounts().get("one") != 1) {
			throw new IllegalStateException();
		}
		if (task.getWordCounts().size() != 1) {
			throw new IllegalStateException();
		}
		if (task.getRoot().getWordCount() != 1) {
			throw new IllegalStateException();
		}
		if (task.getRoot().getLeft() != null || task.getRoot().getRight() != null) {
			throw new IllegalStateException();
		}
	}

	public static void testEmpty() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File(testDir, "test_empty.txt"));
		if (!task.getWordCounts().isEmpty()) {
			throw new IllegalStateException();
		}
		if (task.getRoot() != null) {
			throw new IllegalStateException();
		}
	}

	public static void testLong() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File(testDir, "test_long.txt"));
		System.out.println(task.getWordCounts());
	}

	public static void testCat() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File(testDir, "test_cat.txt"));
		if (task.getWordCounts().get("cat") != 272) {
			throw new IllegalStateException();
		}
		if (task.getRoot().getWordCount() != 272) {
			throw new IllegalStateException();
		}
	}
}

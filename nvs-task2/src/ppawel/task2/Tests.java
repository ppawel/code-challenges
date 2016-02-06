package ppawel.task2;

import java.io.File;
import java.io.FileNotFoundException;

public class Tests {

	public static void main(String[] args) throws FileNotFoundException {
		testReferenceExample();
		testOneWord();
	}

	public static void testReferenceExample() throws FileNotFoundException {
		Task2 task = Task2.runTaskWithFile(new File("testdata/test_example.txt"));
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
		Task2 task = Task2.runTaskWithFile(new File("testdata/test_one_word.txt"));
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
}

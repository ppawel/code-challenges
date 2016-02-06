package ppawel.task2;

public class Node implements Comparable<Node> {

	private Long wordCount;

	private String word;

	private Node left;

	private Node right;

	public Node(Long wordCount, String word) {
		this.wordCount = wordCount;
		this.word = word;
	}

	public Node(Long wordCount, Node left, Node right) {
		this.wordCount = wordCount;
		this.left = left;
		this.right = right;
	}

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

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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

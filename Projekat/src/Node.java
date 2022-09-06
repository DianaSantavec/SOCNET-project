
public class Node {
	private String label;
	
	public Node(String label) {
		this.label = label;
	}
	
	public Node() {
		this("");
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return label;
	}

}


public class Link {
	private String label;
	private int weight;
	
	public Link(String label, int weight) {
		this.label = label;
		this.weight = weight;
	}
	
	public Link() {
		this("", 0);
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getWeight() {
		return weight;
	}
	@Override
	public String toString() {
		return label + " " + weight;
	}

}

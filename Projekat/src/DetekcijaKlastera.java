import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class DetekcijaKlastera{//<Node, Link> {
	private UndirectedSparseGraph<Node, Link> inputGraph;
	public List<UndirectedSparseGraph<Node, Link>> listOfClusters = new ArrayList<UndirectedSparseGraph<Node, Link>>(); //javno samo za testiranje
	private boolean isClusterable;
	private Set<Link> edgesToRemove = new HashSet<Link>();
	
	private HashSet<Node> visited = new HashSet<Node>();
	
	public DetekcijaKlastera(UndirectedSparseGraph<Node, Link> graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Pointer to graph is null");
		}
		else if (graph.getVertexCount() == 0) {
			throw new IllegalArgumentException("Number of vertexes is zero");
		}
		
		inputGraph = graph;
		proveraKlasterabilnosti();
	}
	
	public void proveraKlasterabilnosti() {
		// Automatski se poziva sa konsutrktorom
		
		getComponents();
		
		isClusterable = checkIsClusterable();
		System.out.println("Da li je klasterabilan: " + isClusterable);
	}
	
	
	private void getComponents() {
		boolean flag;
		Iterator<Node> it = inputGraph.getVertices().iterator();
		while (it.hasNext()) {
			Node currentNode = it.next();
			if (!visited.contains(currentNode)) {
				identifyComponentBFS(currentNode);
			}
		}
	}

	private void identifyComponentBFS(Node startNode) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(startNode);
		UndirectedSparseGraph<Node, Link> newComponent = new UndirectedSparseGraph<Node, Link>();
		newComponent.addVertex(startNode);
		
		while(!queue.isEmpty()) {
			Node current = queue.removeFirst();
			//Iterator<V> it = inputGraph.getNeighbors(current).iterator();
			for (Node neighbor : inputGraph.getNeighbors(current)) { 
				if (!visited.contains(neighbor)) {
					Link link = inputGraph.findEdge(current, neighbor);
					if (link.getWeight() > 0) {
						visited.add(neighbor);
						queue.addLast(neighbor);
						newComponent.addVertex(neighbor);
						
						if (newComponent.findEdge(current, neighbor) == null) { 
							//if (link.polarity != - || polarity == - && newComponent.contains(neighbour) ) -> moze se podesiti neki flag odmah da javi da graf nije klasterizovan
							newComponent.addEdge(link, current, neighbor);
						}
					}
				}
			}
		}
		listOfClusters.add(newComponent);
	}
	
	private boolean checkIsClusterable() {
		// Go trough all "clasters" and check are any node connected with (-) in original graph
		
		for (UndirectedSparseGraph<Node, Link> g : listOfClusters){
			for (Node node : g.getVertices()) {
				for (Node neighbour : inputGraph.getNeighbors(node)) {
					if (g.findEdge(neighbour, node) == null && g.getVertices().contains(neighbour)){// containsVertex(neighbour)) {
						Link link = inputGraph.findEdge(neighbour, node);
						if (link.getWeight() < 0 ) {
							return false;
						}
					}
				}
			}	
		}
		return true;
	}
	
	public Set<Link> graneZaUklanjanje() {
		//vraca prazan set ako je graf klasterabilan
		if (!isClusterable) {
			for (UndirectedSparseGraph<Node, Link> g : listOfClusters){
				for (Node node : g.getVertices()) {
					for (Node neighbour : inputGraph.getNeighbors(node)) {
						if (g.findEdge(neighbour, node) == null && g.getVertices().contains(neighbour)){
							Link link = inputGraph.findEdge(neighbour, node); 
							if (g.containsVertex(neighbour) && link.getWeight() < 0) {
								edgesToRemove.add(link);
							}
						}
					}
				}	
			}
		}
		return edgesToRemove;
	}
	
	public int brojGranaZaUklanjanje() {
		if (edgesToRemove == null) {
			return 0;
		}
		return edgesToRemove.size();
	}
	
	
	

}

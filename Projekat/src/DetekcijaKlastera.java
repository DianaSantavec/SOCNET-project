
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class DetekcijaKlastera<N, L>{
	private EdgeTransformer<N> tr;
	private UndirectedSparseGraph<N, L> inputGraph;
	public List<UndirectedSparseGraph<N, L>> listOfClusters = new ArrayList<UndirectedSparseGraph<N, L>>(); //javno samo za testiranje
	private boolean isClusterable;
	private Set<L> edgesToRemove = new HashSet<L>();
	
	private HashSet<N> visited = new HashSet<N>();
	
	public DetekcijaKlastera(UndirectedSparseGraph<N, L> graph, EdgeTransformer<N> transformer) {
		this.tr = transformer;
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
		for (N currentNode : inputGraph.getVertices()) {
			if (!visited.contains(currentNode)) {
				identifyComponentBFS(currentNode);
			}
		}
	}

	private void identifyComponentBFS(N startNode) {
		LinkedList<N> queue = new LinkedList<N>();
		queue.add(startNode);
		UndirectedSparseGraph<N, L> newComponent = new UndirectedSparseGraph<N, L>();
		newComponent.addVertex(startNode);
		
		while(!queue.isEmpty()) {
			N current = queue.removeFirst();
			//Iterator<V> it = inputGraph.getNeighbors(current).iterator();
			for (N neighbor : inputGraph.getNeighbors(current)) { 
				if (!visited.contains(neighbor)) {
					L link = inputGraph.findEdge(current, neighbor);
					if (positiveConnected(current, neighbor)) {
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
		
		for (UndirectedSparseGraph<N, L> g : listOfClusters){
			for (N node : g.getVertices()) {
				for (N neighbour : inputGraph.getNeighbors(node)) {
					if (g.findEdge(neighbour, node) == null && g.getVertices().contains(neighbour)){// containsVertex(neighbour)) {
						//L link = inputGraph.findEdge(neighbour, node);
						if (!positiveConnected(neighbour, node) ) {
							return false;
						}
					}
				}
			}	
		}
		return true;
	}
	
	public Set<L> graneZaUklanjanje() {
		//vraca prazan set ako je graf klasterabilan
		if (!isClusterable) {
			for (UndirectedSparseGraph<N, L> g : listOfClusters){
				for (N node : g.getVertices()) {
					for (N neighbour : inputGraph.getNeighbors(node)) {
						if (g.findEdge(neighbour, node) == null && g.getVertices().contains(neighbour)){
							L link = inputGraph.findEdge(neighbour, node); 
							if (g.containsVertex(neighbour) && !positiveConnected(neighbour, node)) {
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
	
	
	private boolean positiveConnected(N x, N y) {
		return tr.transform(x, y) > 0;
		
	}

}

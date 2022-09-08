
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;


public class DetekcijaKlastera<N, L>{
	private EdgeTransformer<N> tr;    
	private UndirectedSparseGraph<N, L> inputGraph;
	private List<UndirectedSparseGraph<N, L>> listOfClusters = new ArrayList<UndirectedSparseGraph<N, L>>(); //javno samo za testiranje
	private List<UndirectedSparseGraph<N, L>> koalicije = new ArrayList<UndirectedSparseGraph<N, L>>();
	private List<UndirectedSparseGraph<N, L>> nisuKoalicije = new ArrayList<UndirectedSparseGraph<N,L>>();
	private boolean isClusterable;
	private Set<L> edgesToRemove = new HashSet<L>();
	private HashSet<N> visited = new HashSet<N>();
	
	public List<UndirectedSparseGraph<N, L>> getListOfClusters(){
		return listOfClusters;
	}
	
	public DetekcijaKlastera(UndirectedSparseGraph<N, L> graph, EdgeTransformer<N> transformer) {
		if (graph == null) {
			throw new IllegalArgumentException("Pointer to graph is null");
		}
		else if (graph.getVertexCount() == 0) {
			throw new IllegalArgumentException("Number of vertexes is zero");
		}
		this.inputGraph = graph;
		this.tr = transformer;
		
		getComponents();
	}
	
	
	private void getComponents() {
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
		visited.add(startNode);
		
		while(!queue.isEmpty()) {
			N current = queue.removeFirst();
			for (N neighbor : inputGraph.getNeighbors(current)) { 
				if (!visited.contains(neighbor)) {
					L link = inputGraph.findEdge(current, neighbor);
					if (positiveConnected(current, neighbor)) {
						visited.add(neighbor);
						queue.addLast(neighbor);
						newComponent.addVertex(neighbor);
						
						if (newComponent.findEdge(current, neighbor) == null) { 
							newComponent.addEdge(link, current, neighbor);
						}
					}
				}
			}
		}
		listOfClusters.add(newComponent);
	}
	
	public boolean checkIsClusterable() {		
		isClusterable = checkIsClusterable_private();
		return isClusterable;
	}
	
	private boolean checkIsClusterable_private() {
		for (UndirectedSparseGraph<N, L> g : listOfClusters){
			for (N node : g.getVertices()) {
				for (N neighbour : inputGraph.getNeighbors(node)) {
					if (g.containsVertex(neighbour)){
						if (!positiveConnected(neighbour, node)) {
							return false;
						}
					}
				}
			}	
		}
		return true;
	}
	
	public Set<L> edgesToRemove() {
		if (!isClusterable) {
			for (UndirectedSparseGraph<N, L> g : listOfClusters){
				for (N node : g.getVertices()) {
					for (N neighbour : inputGraph.getNeighbors(node)) {
						if (g.getVertices().contains(neighbour)){
							L link = inputGraph.findEdge(neighbour, node); 
							if (g.containsVertex(neighbour) && !positiveConnected(neighbour, node)) {
								edgesToRemove.add(link);
								if (!nisuKoalicije.contains(g)) {
									nisuKoalicije.add(g);
								}
							}
						}
					}
				}
				if (!nisuKoalicije.contains(g)) {
					koalicije.add(g);
				}
			}
		}
		return edgesToRemove;
	}
	
	public int numberOfEdgesToRemove() {
		if (edgesToRemove == null) {
			return 0;
		}
		return edgesToRemove.size();
	}
	
	
	private boolean positiveConnected(N x, N y) {
		return tr.transform(x, y) > 0;
	}
	
	public int numberOfClusters() {
		return listOfClusters.size();
	}
	
	public List<UndirectedSparseGraph<N, L>> getKoalicije() {
		koalicije = new ArrayList<UndirectedSparseGraph<N, L>>();
		edgesToRemove();		
		return koalicije;	
	}
	
	public List<UndirectedSparseGraph<N, L>> getNisuKoalicije() {
		nisuKoalicije = new ArrayList<UndirectedSparseGraph<N,L>>();
		edgesToRemove();
		return nisuKoalicije;
	}
	
	public UndirectedSparseGraph<Integer, Pair<Integer>> getClusterNetwork() {
		Integer i = 0;
		Integer j = 0;
		UndirectedSparseGraph<Integer, Pair<Integer>> clusterNetwork = new UndirectedSparseGraph<>();
		for (i = 0; i< listOfClusters.size(); i++) {
			clusterNetwork.addVertex(i);
		}
		
		i = 0;
		j = 0;

		for (UndirectedSparseGraph<N, L> g : listOfClusters) {
			for (UndirectedSparseGraph<N, L> ng : listOfClusters) {
				if (g != ng) {
					for (N node : g.getVertices()) {
						for (N neighbour : g.getNeighbors(node)) {
							if (ng.containsVertex(neighbour) && !positiveConnected(neighbour, node)) {
								clusterNetwork.addEdge(new Pair<Integer>(i, j), i, j);
							}
						}
					}
					j++;
				}
			}
			i++;
		}
		return clusterNetwork;
	}

}

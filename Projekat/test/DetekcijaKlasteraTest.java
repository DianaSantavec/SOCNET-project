import org.junit.Assert;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 */

/**
 * @author dianas
 *
 */
public class DetekcijaKlasteraTest extends TestCase{
/*
	public DetekcijaKlasteraTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DetekcijaKlasteraTest.class);
	}
	
	public void testMain() {
		assertTrue(true);
	}
	
	private UndirectedSparseGraph<Integer, String> g;
	
	protected void setUp() {
		g = new UndirectedSparseGraph<>();
		
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);	
		
		g.addEdge("1", 1, 2);
		g.addEdge("2", 2, 3);
		g.addEdge("3", 3, 4);
		g.addEdge("4", 4, 5);
	}
	
	public void testClusterabilityAllPositive() {
				
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(g, new TransforemerAllPositiveLinks<>());
		
		Assert.assertTrue("Graph with all positive nodes has 1 cluster", detekcijaKlastera.checkClusterability());
		Assert.assertEquals(1, detekcijaKlastera.numberOfClusters());
		
	}
	public void testClusterabilityAllNegative() {
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(g, new TransformerAllNegativeLinks<>());
		
		
		Assert.assertTrue("Graph with all negative nodes has 5 clusters", detekcijaKlastera.checkClusterability());
		//System.out.println(detekcijaKlastera.numberOfClusters());
		Assert.assertEquals(5, detekcijaKlastera.numberOfClusters());
	}
	
	public void testNonClusterableGraph() {
		UndirectedSparseGraph<Integer, String> gn = new UndirectedSparseGraph<>();
		
		gn.addVertex(1);
		gn.addVertex(2);
		gn.addVertex(3);
		gn.addVertex(4);
		
		gn.addEdge("1", 1, 2);
		gn.addEdge("2", 1, 4);
		gn.addEdge("3", 2, 3);
		gn.addEdge("4", 3, 4);
		gn.addEdge("5", 1, 3);
		
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(gn, new TransformerOneNegativeLink<>());
		
		Assert.assertFalse("This graph with one negative node has no clusters", detekcijaKlastera.checkClusterability());
				
	}
*/
}

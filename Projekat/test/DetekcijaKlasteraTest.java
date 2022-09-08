import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class DetekcijaKlasteraTest extends TestCase{

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
		
		g.addEdge("1", 1, 2);
		g.addEdge("2", 1, 4);
		g.addEdge("3", 2, 3);
		g.addEdge("4", 3, 4);
		g.addEdge("5", 1, 3);
	}
	
	public void testClusterabilityAllPositive() {
				
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(g, new TransforemerAllPositiveLinks<>());
		
		Assert.assertTrue("Graph with all positive nodes has 1 cluster", detekcijaKlastera.checkIsClusterable());
		Assert.assertEquals(1, detekcijaKlastera.numberOfClusters());
		
	}
	public void testClusterabilityAllNegative() {
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(g, new TransformerAllNegativeLinks<>());
		
		
		Assert.assertTrue("Graph with all negative nodes has 5 clusters", detekcijaKlastera.checkIsClusterable());
		Assert.assertEquals(4, detekcijaKlastera.numberOfClusters());
	}
	
	public void testNonClusterableGraph() {	
		DetekcijaKlastera<Integer, String> detekcijaKlastera = new DetekcijaKlastera<>(g, new TransformerOneNegativeLink<>());
		Assert.assertFalse("This graph with one negative node has no clusters", detekcijaKlastera.checkIsClusterable());
		//System.out.println(detekcijaKlastera.getNisuKoalicije());
		g.removeEdge("5");
		//System.out.println(detekcijaKlastera.getNisuKoalicije());
		Assert.assertTrue("Edge with error removed", detekcijaKlastera.checkIsClusterable());
		System.out.println(detekcijaKlastera.getClusterNetwork());
	}
}

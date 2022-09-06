import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.*;

public class Tests {

	public static void main(String[] args) {
		UndirectedSparseGraph<Node, Link> g = new UndirectedSparseGraph<Node, Link>();
	/*	
		Node n1 = new Node("1");
		g.addVertex(n1);
		Node n2 = new Node("2");
		g.addVertex(n2);
		Node n3 = new Node("3");
		Node n4 = new Node("4");
		Node n5 = new Node("5");
		Node n6 = new Node("6");
		Node n7 = new Node("7");
		Node n8 = new Node("8");
		Node n9 = new Node("9");
		Node n10 = new Node("10");
		Node n11 = new Node("11");
		Node n12 = new Node("12");
		Node n13 = new Node("13");
		Node n14 = new Node("14");
		Node n15 = new Node("15");
		
		g.addVertex(n3);
		g.addVertex(n4);
		g.addVertex(n5);
		g.addVertex(n6);
		g.addVertex(n7);
		g.addVertex(n8);
		g.addVertex(n9);
		g.addVertex(n10);
		g.addVertex(n11);
		g.addVertex(n12);
		g.addVertex(n13);
		g.addVertex(n14);
		g.addVertex(n15);
				
		
		g.addEdge(new Link("1", -1), n1, n2);
		g.addEdge(new Link ("2", -1), n1, n3);
		g.addEdge(new Link("3" , -1), n2, n3);
		g.addEdge(new Link("4", 1), n2, n5);
		g.addEdge(new Link ("5", -1), n3, n6);
		g.addEdge(new Link ("6", -1), n5, n6);
		g.addEdge(new Link ("7", 1), n6, n8);
		g.addEdge(new Link ("8", -1), n6, n11);
		g.addEdge(new Link ("9", -1), n8, n11);
		g.addEdge(new Link ("10", -1), n2, n4);
		g.addEdge(new Link ("11", -1), n4, n9);
		g.addEdge(new Link ("12", -1), n4, n7);
		g.addEdge(new Link ("13", 1), n7, n12);
		g.addEdge(new Link ("14", 1), n9, n12);
		g.addEdge(new Link ("15", 1), n12, n10);
		g.addEdge(new Link ("16", 1), n12, n13);
		g.addEdge(new Link ("17", -1), n13, n15);
		g.addEdge(new Link ("18", -1), n13, n11);
		g.addEdge(new Link ("19", -1), n10, n11);
		g.addEdge(new Link ("20", -1), n13, n15);
		g.addEdge(new Link ("21", -1), n15, n14);
		g.addEdge(new Link ("22", -1), n14, n11);
		*/
		/*
		Node n1 = new Node("1");
		g.addVertex(n1);
		Node n2 = new Node("2");
		g.addVertex(n2);
		
		g.addEdge(new Link("1", 1), n1,n2);
		*/
try {		
		BufferedReader buf = new BufferedReader(new FileReader("/home/dianas/Faks/SOCNET/Projekat/Projekat/data/soc-sign-Slashdot090216.txt"));
        ArrayList<String> words = new ArrayList<>();
        String lineJustFetched = null;
        String[] wordsArray;

        while(true){
        	boolean skip1 = false, skip2 = false;
            lineJustFetched = buf.readLine();
            if(lineJustFetched == null){  
                break; 
            }else{
                wordsArray = lineJustFetched.split("\t");
                
                Node newNode1 = new Node(wordsArray[0]);
                Node newNode2 = new Node(wordsArray[1]);
                
                Link newLink = new Link("", Integer.parseInt(wordsArray[2].trim()));
                
                for (Node v : g.getVertices()) {
                	if (v.getLabel().equals(newNode1.getLabel())) {
                		skip1 = true;
                	}
                	if (v.getLabel().equals(newNode2.getLabel())) {
                		skip2 = true;
                	}
                }
                if (!skip1) {
                	g.addVertex(newNode1);
                }
                if (!skip2) {
                	g.addVertex(newNode2);
                }
                g.addEdge(newLink, newNode1, newNode2);
            }
        
            
        }
        buf.close();
}
        catch(Exception e){
        	System.out.println("ovde");
            System.out.println(e.getMessage());
        }
   
		
		//System.out.println(g);
		
		DetekcijaKlastera d = new DetekcijaKlastera(g);
		try {
			FileWriter fw = new FileWriter("/home/dianas/Faks/SOCNET/Projekat/output.txt");
			fw.write("Grane:" + d.graneZaUklanjanje());
			fw.write("br grana: " + d.brojGranaZaUklanjanje());
			fw.write("Klateri: " + d.listOfClusters);
			fw.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		/*System.out.println("Grane:" + d.graneZaUklanjanje());
		System.out.println("br grana: " + d.brojGranaZaUklanjanje());
		System.out.println("Klateri: " + d.listOfClusters);*/

		//SimpleGraphView sgv = new SimpleGraphView(); //We create our graph in here    // The Layout<V, E> is parameterized by the vertex and edge types   
		/*Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(300,300)); // sets the initial size of the space     
		// The BasicVisualizationServer<V,E> is parameterized by the edge types     
		BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size            
		JFrame frame = new JFrame("Simple Graph View");     
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		frame.getContentPane().add(vv);      
		frame.pack();     
		frame.setVisible(true);*/
		
		

	}

}

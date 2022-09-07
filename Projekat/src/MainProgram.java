import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.LinkedTransferQueue;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class MainProgram {

	public static void main(String[] args) {
		
		String filename = "soc-sign-Slashdot081106.txt";//args[1]; //soc-sign-Slashdot081106.txt
		Scanner scanner;
		try {
			scanner = new Scanner (new File ("D:\\Faks\\SOCNET\\SOCNET-project\\data\\" + filename));
		}
		catch(Exception e){
			System.out.println("Fajl ne postoji: " + filename);
			return;
		}
		
		HashSet<Integer> nodes = new HashSet<>();
		HashMap<Pair<Integer>, Integer> weights = new HashMap<>();
		UndirectedSparseGraph<Integer, Pair<Integer>> inputGraph = new UndirectedSparseGraph<>();
		
		while (scanner.hasNext()) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int w = scanner.nextInt();
			
			
			if (!nodes.contains(x)) {
				nodes.add(x);
				inputGraph.addVertex(x);
			}
			if (!nodes.contains(y)) {
				nodes.add(y);
				inputGraph.addVertex(y);
			}
			weights.put(new Pair<Integer>(x,y), w);
			inputGraph.addEdge(new Pair<Integer>(x, y), x, y);
		}
		scanner.close();
		
		DetekcijaKlastera<Integer, Pair<Integer>> detekcijaKlastera = new DetekcijaKlastera<Integer, Pair <Integer>>(inputGraph, new EdgeTransformer<Integer>(){
			@Override
			public int transform(Integer x, Integer y) {
                Integer sign =  weights.get(new Pair<Integer>(x,y));
                if (sign == null) 
                {
                    return weights.get(new Pair<Integer>(y,x)) >= 0 ? 1 : -1;
                }
                else return sign >= 0 ? 1 : -1;
            }
			
		}
		);
			
		String outputFileName = "output_" + filename; 
		try {
			FileWriter fw = new FileWriter("data/" + outputFileName);
			fw.write("Da li je klasterabilan: " + detekcijaKlastera.checkClusterability() + "\n");
			fw.write("Grane za uklanjanje:" + detekcijaKlastera.graneZaUklanjanje() + "\n");
			fw.write("Broj grana za uklanjanje: " + detekcijaKlastera.brojGranaZaUklanjanje() + "\n");
			fw.write("Svi klateri: " + detekcijaKlastera.listOfClusters + "\n");
			fw.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}	

	}

}

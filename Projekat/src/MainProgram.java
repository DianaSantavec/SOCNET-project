import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.Line;

public class MainProgram {

	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		
		for (int i = 0; i < n; i++) {
			
			String filename = args[1+i];
			Scanner scanner;
			try {
				scanner = new Scanner (new File ("input_data//" + filename));
			}
			catch(Exception e){
				System.out.println("Fajl ne postoji: " + filename);
				return;
			}
		
			HashSet<Integer> nodes = new HashSet<>();
			HashMap<Pair<Integer>, Integer> weights = new HashMap<>();
			UndirectedSparseGraph<Integer, Pair<Integer>> inputGraph = new UndirectedSparseGraph<>();
		
			
			//int lastVoted = 0;
			while (scanner.hasNext()) {
			/*	String line = scanner.nextLine();
				if (line.length() > 0 && line.charAt(0) == 'U') {
					String splitted[] = line.split("\t");
					int x = Integer.parseInt(splitted[1]);
					if (!nodes.contains(x)) {
						nodes.add(x);
						inputGraph.addVertex(x);
					}
					lastVoted = x;
				}
				if (line.length() > 0 && line.charAt(0) == 'V') {
					String splitted[] = line.split("\t");
					int y = Integer.parseInt(splitted[2]);
					if (!nodes.contains(y)) {
						nodes.add(y);
						inputGraph.addVertex(y);
					}
					int w = Integer.parseInt(splitted[1]);
					if (inputGraph.findEdge(lastVoted, y) == null) {
						weights.put(new Pair<Integer>(lastVoted, y), w);
						inputGraph.addEdge(new Pair<Integer>(lastVoted, y), lastVoted, y);
					}
					else if (w < 0){
						Integer sign = weights.get(new Pair<Integer>(lastVoted, y));
						if (sign == null)  {
							sign = weights.get(new Pair<Integer>(y, lastVoted));
							weights.put(new Pair<Integer>(y, lastVoted), w);
						}
						else {
							weights.put(new Pair<Integer>(lastVoted, y), w);
						}
						
					}
				}
				*/
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
				if (inputGraph.findEdge(x, y) == null) {
					weights.put(new Pair<Integer>(x, y), w);
					inputGraph.addEdge(new Pair<Integer>(x, y), x, y);
				}
				else if (w < 0){
					Integer sign = weights.get(new Pair<Integer>(x, y));
					if (sign == null)  {
						sign = weights.get(new Pair<Integer>(y, x));
						weights.put(new Pair<Integer>(y, x), w);
					}
					else {
						weights.put(new Pair<Integer>(x, y), w);
					}
					
				}
			}
				//weights.put(new Pair<Integer>(x,y), w);
				//inputGraph.addEdge(new Pair<Integer>(x, y), x, y);
			
			scanner.close();
		
		
			DetekcijaKlastera<Integer, Pair<Integer>> detekcijaKlastera = new DetekcijaKlastera<Integer, Pair <Integer>>(inputGraph, new EdgeTransformer<Integer>(){
				@Override
				public int transform(Integer x, Integer y) {
					Integer sign =  weights.get(new Pair<Integer>(x,y));
					if (sign == null)  {
						sign = weights.get(new Pair<Integer>(y,x));
						if (sign >= 0) {
							return 1;
						}
						else {
							return -1;
						}
					}
					else {
						if (sign >= 0) {
							return 1;
						}
						else {
							return -1;
						}
					}
				}
			});
			String outputFileName = "output_" + filename; 
			try {
				FileWriter fw = new FileWriter("output_data//" + outputFileName);
				fw.write("Da li je klasterabilan: " + detekcijaKlastera.checkIsClusterable() + "\n");
				fw.write("Linkovi za uklanjanje:" + detekcijaKlastera.edgesToRemove() + "\n");
				fw.write("Svi klateri: " + detekcijaKlastera.getListOfClusters() + "\n");
				fw.write("Broj linkova za uklanjanje: " + detekcijaKlastera.numberOfEdgesToRemove() + "\n");
				fw.write("Broj klastera: " + detekcijaKlastera.numberOfClusters());
				fw.close();
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}	
		}
	}
}

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.zip.GZIPInputStream;

import javax.swing.text.html.HTMLDocument.Iterator;

public class DepthFirst {
	// 4820
	// http://www.java2novice.com/java-collections-and-util/hashmap/all-keys/
	public static void main(String[] args) throws IOException {
		FileInputStream fileStream = new FileInputStream("adj.txt");
		// GZIPInputStream zipStream = new GZIPInputStream(fileStream);
		Scanner sc = new Scanner(fileStream);
		DFS(sc);
	}

	public static void DFS(Scanner sc) throws FileNotFoundException {
		int graphSize = sc.nextInt();
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
		String[] IDCodes = new String[graphSize];
		int connectedComponents = 0;
		int componentSize = 0;
		ArrayList<Integer> ComponentSizes = new ArrayList<Integer>();
		sc.nextLine();
		// Set everything to false and make a list of IDs
		// Put graph in hashmap/arraylist structure
		for (int i = 0; i < graphSize; i++) {
			String[] line = sc.nextLine().split(" ");
			ArrayList<String> list = new ArrayList<String>();
			graph.put(line[0], list);
			IDCodes[i] = line[0];
			for (int j = 1; j < line.length; j++) {
				list.add(line[j]);
			}
			visited.put(line[0], false);
		}

		for (int i = 0; i < graphSize; i++) {
			if (visited.get(IDCodes[i]) == false) {
				componentSize = dfsVisit(IDCodes[i], visited, graph);
				ComponentSizes.add(componentSize);
				connectedComponents++;
			}
		}
		printFrequencies(ComponentSizes);
	}

	public static int dfsVisit(String u, HashMap<String, Boolean> visited, HashMap<String, ArrayList<String>> graph)
			throws FileNotFoundException {
		Stack<String> verticesToVisit = new Stack<String>();
		int componentSize = 0;
		verticesToVisit.push(u);
		visited.put(u, true);
		while (verticesToVisit.size() > 0) {
			u = verticesToVisit.pop();
			componentSize++;
			for (int k = 0; k < graph.get(u).size(); k++) {
				if (!visited.get(graph.get(u).get(k))) {
					verticesToVisit.push(u);
					verticesToVisit.push(graph.get(u).get(k));
					visited.put(graph.get(u).get(k), true);
				}
			}
		}
		return componentSize;
	}

	public static void printFrequencies(ArrayList<Integer> ComponentSizes) {
		HashMap<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
		for (int i = 0; i < 4820; i++) {
			if (frequencies.get(ComponentSizes.get(i)) == null) {
				frequencies.put(ComponentSizes.get(i), 1);
			} else {
				frequencies.put(ComponentSizes.get(i), frequencies.get(ComponentSizes.get(i)) + 1);
			}
		}

		for (Entry<Integer, Integer> entry : frequencies.entrySet()) {
			System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		}
	}
}
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;

public class BreadthFirst {

	public static void main(String[] args) throws IOException {
		int[] ID = listOfCodes();
		for (int i = 0; i < 100; i++) { // get random 100 info
			FileInputStream fileStream = new FileInputStream("adj.txt");
			// GZIPInputStream zipStream = new GZIPInputStream(fileStream);
			Scanner scanner = new Scanner(fileStream);
			int rand = (int) (517900 * Math.random());
			BFS(scanner, Integer.toString(ID[rand]));
		}
		// BFS(sc, "357650"); for when I get Kevin Bacon's Stats
	}

	public static int[] listOfCodes() throws IOException {
		FileInputStream fileStream = new FileInputStream("adj.txt");
		// GZIPInputStream zipStream = new GZIPInputStream(fileStream);
		Scanner scanner = new Scanner(fileStream);
		scanner.nextLine();
		int[] IDC = new int[517900];
		for (int i = 0; i < 517900; i++) {
			String[] space = scanner.nextLine().split(" ");
			IDC[i] = (Integer.parseInt(space[0]));
		}
		return IDC;
	}

	public static void BFS(Scanner sc, String ID) throws FileNotFoundException {
		int graphSize = sc.nextInt();
		HashMap<String, Integer> distance = new HashMap<String, Integer>();
		HashMap<String, String> parents = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();

		sc.nextLine();

		for (int i = 0; i < graphSize; i++) {
			String[] line = sc.nextLine().split(" ");
			ArrayList<String> list = new ArrayList<String>();
			graph.put(line[0], list);
			for (int j = 1; j < line.length; j++) {
				list.add(line[j]);
			}
			distance.put(line[0], -1);
			parents.put(line[0], null);
		}

		Queue<String> myQueue = new LinkedList<String>();
		distance.put(ID, 0);
		myQueue.add(ID);

		while (myQueue.size() > 0) {
			String u = myQueue.poll();
			for (int k = 0; k < graph.get(u).size(); k++) {
				String v = graph.get(u).get(k);
				if (distance.get(v) == -1) {
					distance.put(v, 1 + distance.get(u));
					myQueue.add(v);
					parents.put(v, u);
				}
			}
		}

		HashMap<Integer, Integer> uniqueDist = new HashMap<Integer, Integer>();
		for (Entry<String, Integer> entry : distance.entrySet()) {
			if (uniqueDist.get(entry.getValue()) == null) {
				uniqueDist.put(entry.getValue(), 1);
			} else {
				uniqueDist.put(entry.getValue(), uniqueDist.get(entry.getValue()) + 1);
			}
		}

		/*
		 * for (Entry<Integer, Integer> entry : uniqueDist.entrySet()) {
		 * System.out.println("key=" + entry.getKey() + ", value=" +
		 * entry.getValue()); } ****to print the distance frequency table
		 ******/

		// to print avg ditance and fraction
		System.out.print("ID" + ID + " ");
		double avgDist = 0;
		double reached = 0;
		for (Entry<Integer, Integer> entry : uniqueDist.entrySet()) {
			if (entry.getKey() > 0) {
				avgDist += entry.getKey() * entry.getValue();
				reached += entry.getValue();
			}
		}
		System.out.println(avgDist / 517899);
		System.out.println("Fraction: " + reached / 517900);
	}
}



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OSM {
	public OSM(File file) {
	}

	private static Scanner scanner;

	static String extractStringFromVal(String haystack, String needle) {
		if (needle.equals("v")) {
			String[] array = haystack.split("\"");
			return array[3];
		} else {
			String[] array = haystack.split("\\s+");

			for (String a : array) {
				String[] b = a.split("=");
				if (b.length != 2) {
					continue;
				}
				String key = b[0];
				String val = b[1];
				if (needle.compareToIgnoreCase(key) == 0) {
					return val.replaceAll("\"", "").replaceAll("\\/", "")
							.replaceAll(">", "");
				}
			}
		}
		return null;
	}

	public static void extractBoundaries(File file) {
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				// System.out.println(line);
				if (line.contains("<bounds")) {
					Boundaries.update(line);
					// System.out.println(line);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public static HashMap<Long, Point> extractNodes(File file) {
		HashMap<Long, Point> nodes = new HashMap<>();
		int counter = 0;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.contains("<node")) {
					counter++;

					// System.out.println(line);
					Point p = new Point(line, true);
					nodes.put((long) p.id, p);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		//System.out.println("Total Nodes : " + counter);
		return nodes;
	}

	public static HashMap<Long, Road> extractWays(File file,
			HashMap<Long, Point> nodes) {
		HashMap<Long, Road> ways = new HashMap<Long, Road>();
		int counter = 0;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();

				if (line.contains("<way")) {
					counter++;
					Road road = new Road(line, true);
					//long roadId = OSM.extractStringFromVal(line, "id"));
					
					while (!line.contains("</way")) {
						line = scanner.nextLine();

						if (line.contains("<nd")) {
							long nodeIdRef = Long.parseLong(OSM
									.extractStringFromVal(line, "ref"));
							Point p = nodes.get(nodeIdRef);
							assert (p != null);
							road.points.add(p);
						}

						if (line.contains("<tag")) {
							String name = OSM.extractStringFromVal(line, "k");
							if (name.equalsIgnoreCase("name")) {
								road.name = OSM.extractStringFromVal(line, "v");
								// System.out.println("Road Name : " +
								// road.name);
							}
						}
					}

					road.build(road.points);

					ways.put(road.id, road);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		//System.out.println("Total Roads : " + counter);
		return ways;
	}

	public static ArrayList<Road> toArrayList(HashMap<Long, Road> roads) {
		ArrayList<Road> roadsArray = new ArrayList<>();

		for (Road road : roads.values()) {
			roadsArray.add(road);

		}
		return roadsArray;
	}

	public static ArrayList<Point> pointHashToArrayList(
			HashMap<Long, Point> points) {
		ArrayList<Point> pointsArray = new ArrayList<>();

		for (Point point : points.values()) {
			pointsArray.add(point);

		}
		return pointsArray;
	}

	public static ArrayList<Intersection> getIntersections() {
		
		
		return null;
	}

	
}
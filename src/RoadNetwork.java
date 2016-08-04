

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class RoadNetwork {
	ArrayList<Road> roads = new ArrayList<>();
	HashMap<Long, Point> allPoints = new HashMap<>();
	private Scanner scanner;
	public ArrayList<Intersection> intersections = new ArrayList<Intersection>();

	HashMap<Long, Road> tableOfRoads = null;
	HashMap<Long, Point> tableOfPoints = null;

	private void readTextFormat(File file) {
		Road road = new Road();
		ArrayList<Point> tempPoints = new ArrayList<Point>();

		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				Point p = new Point(line);
				Boundaries.update(p);
				tempPoints.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		road.build(tempPoints);
		roads.add(road);
	}

	public RoadNetwork(File file) {
		readTextFormat(file);
	}

	private void readOSMFormat(File file) {
		// extract boundaries
		OSM.extractBoundaries(file);

		// extract points
		allPoints = OSM.extractNodes(file);
		tableOfPoints = OSM.extractNodes(file);

		// extract ways: implement your code below
		roads = OSM.toArrayList(OSM.extractWays(file, allPoints));
		tableOfRoads = OSM.extractWays(file, tableOfPoints);
		//stitchIntersections();

	}

	public void printAllPoints() {
		for (long key : allPoints.keySet()) {
			System.out.println(key);
		}
	}

	public RoadNetwork(File file, boolean isOSM) {
		if (isOSM) {
			readOSMFormat(file);
		} else {
			readTextFormat(file);
		}
	}

	public void draw() {
		for (Road road : roads) {
			if (road.equals(roadWithMostPoints())) {
				// StdDraw.setPenColor(Color.MAGENTA);
				road.draw();
				StdDraw.setPenColor(Color.black);
				// StdDraw.setPenColor(Color.black);

			} else {
				road.draw();
			}
		}

	}

	public Road roadWithMostPoints() {
		Road road = new Road();
		int size = 0;
		for (Road r : roads) {

			// System.out.println("Road size " + r.points.size());
			if (r.points.size() > size) {
				size = r.points.size();
				road = r;
			}
		}

		return road;
	}

	public void dump() {
		for (Road road : roads) {
			road.dump();
		}
	}

	public Point findNearestPoint(Double x1, Double y1) {
		Point nearestPoint = new Point(x1 + 1, y1 + 1);
		for (Road road : roads) {
			for (Point point : road.points) {

				if (point.distance(x1, y1) < nearestPoint.distance(x1, y1)) {
					nearestPoint = point;
				}
			}
		}

		return nearestPoint;
	}

	//	private void stitchIntersections() {
	//        for (Road road : tableOfRoads.values()) {
	//            road.connect();
	//        }
	//    }

	public ArrayList<Point> findPath(ArrayList<Point> points, Point startPoint, Point endPoint) {
		if (startPoint.id == endPoint.id) {
			points.add(endPoint);
			return points;
		}
		else if (endPoint.p == null) {
			System.out.println("no Path");
		}
		else {
			points.add(endPoint);
			
			findPath(points, startPoint, endPoint.p);
		}
		
		return points;	
	}
	
	public void search(Point startPoint) {
		//Since queue is a interface
		Queue<Point> queue = new LinkedList<Point>();

		if(startPoint == null) {
			return;
		}

		startPoint.tag = true;
		startPoint.distance = 0;
		//Adds to end of queue
		queue.add(startPoint);

		while(!queue.isEmpty()) {
			//removes from front of queue
			Point r = queue.remove();
			
//			System.out.print("this point has amount of edges" + r.edges.size() + "\t");

			//Visit child first before grandchild
			for(Point n: r.getNeighbors()) {
				
				
				if(n.tag == false) {
					n.distance = r.distance + 1;
					n.p = r;
					queue.add(n);
					n.tag = true;
				}
			}
		}		
		return;
	}
	

}
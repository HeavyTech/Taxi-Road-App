

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class App {
//Arguments 100Nodes.osm map.png report.txt 0.2 0.9 0.5 0.3 0.85 0.95 0.95 0.1
	public static void main(String[] args) {
		final String roadFilename = args[0];
		final String imageFilename = args[1];
		final String reportFilename = args[2];
		final Double x1 = Double.parseDouble(args[3]);
		final Double y1 = Double.parseDouble(args[4]);
		final Double x2 = Double.parseDouble(args[5]);
		final Double y2 = Double.parseDouble(args[6]);
		final Double x3 = Double.parseDouble(args[7]);
		final Double y3 = Double.parseDouble(args[8]);
		final Double x4 = Double.parseDouble(args[9]);
		final Double y4 = Double.parseDouble(args[10]);
		final boolean isOSM = true;
		double runTimeTaxi1 = 0.0;
		double runTimeTaxi2 = 0.0;
		RoadNetwork roadNetwork = new RoadNetwork(new File(roadFilename), isOSM);

		// System.out.println("Argument 1: " + x1 + " " + y1);
		// System.out.println("Argument 2: " + x2 + " " + y2);
		StdDraw.clear(Color.LIGHT_GRAY);
		// StdDraw.picture(0.5, 0.5, "pic.png", 1, 1);

		roadNetwork.draw();

		// Starting Points
		Point startPoint = roadNetwork.findNearestPoint(x1, y1);
		StdDraw.setPenColor(Color.YELLOW);
		StdDraw.filledCircle(startPoint.Xs(), startPoint.Ys(), 0.015);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(startPoint.Xs() + 0.03, startPoint.Ys() + 0.03, "Taxi1 ");

		// Second Starting Point
		Point Taxi = roadNetwork.findNearestPoint(x3, y3);
		StdDraw.setPenColor(Color.YELLOW);
		StdDraw.filledCircle(Taxi.Xs(), Taxi.Ys(), .015);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(Taxi.Xs() + .07, Taxi.Ys() + 0.03, "Taxi 2");

		// End point
		Point endPoint = roadNetwork.findNearestPoint(x2, y2);
		StdDraw.setPenColor(Color.RED);
		StdDraw.filledCircle(endPoint.Xs(), endPoint.Ys(), 0.015);
		StdDraw.text(endPoint.Xs() + 0.09, endPoint.Ys() + -0.03, "Customer ");
		// System.out.println("End Point: " + endPoint);

		// System.out.println("Road with most Points : " +
		// roadNetwork.roadWithMostPoints().points.size());
		Point finalDestination = roadNetwork.findNearestPoint(x4, y4);
		StdDraw.setPenColor(34, 139, 34);
		StdDraw.filledCircle(finalDestination.Xs(), finalDestination.Ys(),
				0.015);
		StdDraw.text(finalDestination.Xs() + -0.06, finalDestination.Ys()
				+ -0.04, "Final Destination ");
		StdDraw.text(finalDestination.Xs() + -0.06,
				finalDestination.Ys() + 0.04, "$$$ ");
		roadNetwork.search(endPoint);

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("Taxi2.txt")));
			double startTime = System.currentTimeMillis();

			// roadNetwork.search(Taxi);

			ArrayList<Point> pathPoints = roadNetwork.findPath(
					new ArrayList<Point>(), endPoint, Taxi);
			Road taxi = new Road();
			taxi.build(pathPoints);
			StdDraw.setPenColor(Color.magenta);
			taxi.draw();

			double stopTime = System.currentTimeMillis();
			runTimeTaxi2 = (stopTime - startTime);

			out.println("S " + startPoint);
			out.println("E " + "" + endPoint);
			out.println(endPoint);
			for (Point p : pathPoints) {
				out.println(p);
			}
			out.print("Total time from Start point to End Point is : ");
			out.println(runTimeTaxi2 / 1000 + " seconds. ");

			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("Taxi1.txt")));
			double startTime = System.currentTimeMillis();

			// roadNetwork.search(endPointPoint);

			ArrayList<Point> pathPoints = roadNetwork.findPath(
					new ArrayList<Point>(), endPoint, startPoint);
			Road path = new Road();
			path.build(pathPoints);

			StdDraw.setPenColor(Color.blue);
			path.draw();

			double stopTime = System.currentTimeMillis();
			runTimeTaxi1 = (stopTime - startTime);

			// System.out.println("runtime: " + runTimeTaxi1);
			out.println("S " + startPoint);
			out.println("E " + "" + endPoint);
			out.println(endPoint);
			for (Point p : pathPoints) {
				out.println(p);
			}
			out.print("Total time from Start point to End Point is : ");
			out.println(runTimeTaxi1 / 1000 + " seconds. ");

			out.close();
			try {
				PrintWriter out1 = new PrintWriter(new BufferedWriter(
						new FileWriter("SatisfiedCustomer.txt")));
				double startTime1 = System.currentTimeMillis();
				roadNetwork.search(finalDestination);
				if (runTimeTaxi1 < runTimeTaxi2) {

					ArrayList<Point> pathPoints1 = roadNetwork.findPath(
							new ArrayList<Point>(), endPoint, finalDestination);
					path.points.addAll(pathPoints1);
					Road end = new Road();

					// end.build(pathPoints1);
					// path.points.addAll(pathPoints1);
					end.build(path.points);
					StdDraw.setPenColor(Color.blue);
					end.draw();
					double endTime1 = System.currentTimeMillis();

					double time1 = (endTime1 - startTime1);
					// System.out.println("time 1 " + time1);
					double total = runTimeTaxi1 + time1;
					// System.out.println(total + "<==");
					out1.println("Taxi One total time:  " + total + " Seconds");
				} else if (runTimeTaxi1 > runTimeTaxi2) {

					ArrayList<Point> pathPoints1 = roadNetwork.findPath(
							new ArrayList<Point>(), endPoint, finalDestination);
					path.points.addAll(pathPoints1);
					Road end = new Road();

					// end.build(pathPoints1);
					// path.points.addAll(pathPoints1);
					end.build(path.points);
					StdDraw.setPenColor(Color.magenta);
					end.draw();
					double endTime1 = System.currentTimeMillis();
					double time1 = (endTime1 - startTime1);
					// System.out.println("time1 taxi2" + time1);
					// System.out.println("jfbf" + runTimeTaxi2);
					double total = runTimeTaxi2 + time1;
					// System.out.println(total+ "<---");
					out1.println("Taxi two total time:  " + total + " Seconds");

					out1.close();
				} else {
					System.out.println("Taxi lost gps signal");
				}

				out1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println("Runtime tax1 :" + runTimeTaxi1);
		//
		// System.out.println("Runtime tax2 :" + runTimeTaxi2);
		StdDraw.save(imageFilename);
	}
}
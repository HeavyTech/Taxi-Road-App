

import java.util.ArrayList;

public class Segment {
	Point p1;
	Point p2;
	static int id = 0;
	double A;
	double B;
	double C;

	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		A = p2.Yr() - p1.Yr();
		B = p1.Xr() - p2.Xr();
		C = A * p1.Xr() + B * p1.Yr();
	}

	ArrayList<Point> discretize(int K) {
		ArrayList<Point> points = new ArrayList<>();

		double xStepSize = (p2.Xr() - p1.Xr()) / K;
		for (int i = 0; i < K; i++) {
			double x = p1.Xr() + i * xStepSize;
			double y = (C - A * x) / B;
			points.add(new Point(i, x, y));
		}

		return points;
	}

	public void dump() {
		System.out.println("segment: ");
		p1.dump();
		p2.dump();
	}

	public void draw() {
		StdDraw.setPenRadius(0.01);
		p1.draw();
		p2.draw();
		StdDraw.setPenRadius(0.002);
		StdDraw.line(p1.Xs(), p1.Ys(), p2.Xs(), p2.Ys());

	}
	public String toString() {
		String out = "";
		return out;
	}
}
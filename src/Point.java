

import java.util.ArrayList;

public class Point extends GeometricObject {
    long id;
    double x, y;
    boolean tag = false;
    ArrayList<Segment> edges = new ArrayList<>();
    int distance;
    Point p;
    
    ArrayList<Point> getNeighbors() {
    	ArrayList<Point> neighbors = new ArrayList<Point>();
    	
    	for (Segment edge : edges) {
    		if (edge.p1 != this) {
    			
    			neighbors.add(edge.p1);
    		}
    		if (edge.p2 != this) {
    			
    			neighbors.add(edge.p2);
    		}
    	}
    	
    	return neighbors;
    }

    public Point() { }

    public Point(long id, double x, double y) {
        this.id = id; this.x = x; this.y = y;
    }
    public Point( double x, double y) {
        this.x = x; this.y = y;
    }
    public double distance(double x1, double y1){
    	 double distance = 
                 Math.sqrt(Math.pow(Xs()-x1, 2) + Math.pow(Ys()-y1, 2));
    	 
    	return distance;
    	
    }

    double Xr() { return x; }
    double Yr() { return y; }

    double Xs() { 
        return (Xr() - Boundaries.xmin)/(Boundaries.xmax - Boundaries.xmin);
    }

    double Ys() { 
        return (Yr() - Boundaries.ymin)/(Boundaries.ymax - Boundaries.ymin);
    }

    public Point(String line) {
    }

    public Point(String line, boolean isOSM) {
        if (isOSM) {
            this.id = Long.parseLong( OSM.extractStringFromVal(line, "id"));
            this.x =  Double.parseDouble( OSM.extractStringFromVal(line, "lon"));
            this.y =  Double.parseDouble( OSM.extractStringFromVal(line, "lat"));
        }
    }

    public String toString() {
        return "P " + id + " " + Xs() + " " + Ys();
    }
    
    public void draw() {
       // StdDraw.filledRectangle(Xs(), Ys(), 0.01, 0.01);
//    	StdDraw.setPenColor(Color.BLUE);
        StdDraw.point(Xs(), Ys());
       // StdDraw.setPenColor(Color.DARK_GRAY);
        
    }

    public void drawDot() {
        StdDraw.filledCircle(Xs(), Ys(), 0.0005);
    }

    public double getArea() {
        return 0;
    }

    public void dump() {
        System.out.printf("p[%ld] = (%f,%f) => (%f,%f)\n", id, 
                Xr(), Yr(), Xs(), Ys());
    }	
}
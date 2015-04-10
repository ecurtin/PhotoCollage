import java.lang.Math;

public class Point{

	public double x = 0.0;
	public double y = 0.0;
 
	// CREATE
	Point () {

	}
	
	Point (double px, double py) {
		x = px;
		y = py;
	}


	// MODIFY
	Point setTo(double px, double py) {
		x = px;
 		y = py;
		return this;
	}
 
	Point setTo(Point p) {
		x = p.x;
		y = p.y;
		return this;
	}
 
	// p.add(u,v): P += <u,v>
	Point add(double u, double v) {
		x += u;
		y += v;
		return this;
	}
	            
	Point addPoint(Point p) {
		x += p.x;
		y += p.y;
		return this;
	}
               
// adds s * P
	Point addWeightedPoint(double s, Point p)  {
		x += s * p.x;
		y += s * p.y;
		return this;
	}
        

// p.add(vec): P += vec
	Point addVector(Vector2D vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}               

// p.add(s,vec): P += svec
	Point addWeightedVector(double s, Vector2D vec) {
		x += s * vec.x;
		y += s * vec.y;
		return this;
	}         

 // transalte by ratio s towards P
	Point translateTowards(double s, Point p) {
		x += s * (p.x - x);
		y += s * (p.y - y);
		return this;
	}

	Point scale(double u, double v) {
		x * = u;
		y * = v;
		return this;
	}

// p.scale(s): P * = s
	Point scale(double s) {
		x * = s;
		y * = s;
		return this;
	}                 

// p.scale(s,C): scales wrt C: P = L(C,P,s);
	Point scale(double s, Point C) {
		x * = C.x + s * (x - C.x);
		y * = C.y + s * (y - C.y);
		return this;
	}  

   // p.rotate(a): rotate P around origin by angle a in radians
	Point rotate(double a) {
		double dx = x, dy = y, c = Math.cos(a), s = Math.sin(a);
		x = c * dx + s * dy;
		y = - s * dx + c * dy;
		return this;
	}

  // p.rotate(a,g): rotate P around g by angle a in radians
	Point rotate(double a, Point g) {
		double dx = x - g.x, dy = y - g.y, c = Math.cos(a), s = Math.sin(a);
		x = g.x + c * dx + s * dy;
		y = g.y - s * dx + c * dy;
		return this;
	}

  	// fast rotate s = Math.sin(a); t = tan(a/2);
	Point rotate(double s, double t, Point g) {
		double dx = x - g.x; 
		double dy = y - g.y;

		dx -= dy * t;
		dy += dx * s;
		dx -= dy * t;
		x = g.x + dx;
		y = g.y + dy;

		return this;
	}

	boolean equals(Point p) {
		return x == p.x && y == p.y;
	}

	String toString() {
		return "(" + x + ", " + y + ")";
	}
}
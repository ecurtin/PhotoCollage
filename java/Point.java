public class Point{

	public float x = 0.0;
	public float y = 0.0;
 
	// CREATE
	Point () {

	}
	
	Point (float px, float py) {
		x = px;
		y = py;
	}


	// MODIFY
	Point setTo(float px, float py) {
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
	Point add(float u, float v) {
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
	Point addWeightedPoint(float s, Point p)  {
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
	Point addWeightedVector(float s, Vector2D vec) {
		x += s * vec.x;
		y += s * vec.y;
		return this;
	}         

 // transalte by ratio s towards P
	Point translateTowards(float s, Point p) {
		x += s * (p.x - x);
		y += s * (p.y - y);
		return this;
	}

	Point scale(float u, float v) {
		x * = u;
		y * = v;
		return this;
	}

// p.scale(s): P * = s
	Point scale(float s) {
		x * = s;
		y * = s;
		return this;
	}                 

// p.scale(s,C): scales wrt C: P = L(C,P,s);
	Point scale(float s, Point C) {
		x * = C.x + s * (x - C.x);
		y * = C.y + s * (y - C.y);
		return this;
	}  

   // p.rotate(a): rotate P around origin by angle a in radians
	Point rotate(float a) {
		float dx = x, dy = y, c = cos(a), s = sin(a);
		x = c * dx + s * dy;
		y = - s * dx + c * dy;
		return this;
	}

  // p.rotate(a,g): rotate P around g by angle a in radians
	Point rotate(float a, Point g) {
		float dx = x - g.x, dy = y - g.y, c = cos(a), s = sin(a);
		x = g.x + c * dx + s * dy;
		y = g.y - s * dx + c * dy;
		return this;
	}

  	// fast rotate s = sin(a); t = tan(a/2);
	Point rotate(float s, float t, Point g) {
		float dx = x - g.x; 
		float dy = y - g.y;

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
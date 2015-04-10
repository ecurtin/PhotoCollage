import java.lang.Math;

public class Vector2D {

	public double x = 0.0;
	public double y = 0.0;

// CREATE
	Vector2D () {
	}

	Vector2D (double px, double py) {
		x = px;
		y = py;
	}


// MODIFY
	Vector2D setTo(double px, double py) {
		x = px;
		y = py;
		return this;
	}

	Vector2D setTo(Vector2D vec) {
		x = vec.x;
		y = vec.y;
		return this;
	}

	Vector2D zero() {
		x = 0;
		y = 0;
		return this;
	}
	Vector2D scaleBy(double u, double v) {
		x *= u;
		y *= v;
		return this;
	}

	Vector2D scaleBy(double f) {
		x *= f;
		y *= f;
		return this;
	}

	Vector2D reverse() {
		x = -x;
		y = -y;
		return this;
	}

	Vector2D divideBy(double f) {
		x /= f;
		y /= f;
		return this;
	}

	// Vector2D normalize() {
	// 	double n = sqrt(sq(x) + sq(y));
	// 	if (n>0.000001) {
	// 		x /= n;
	// 		y /= n;
	// 	}
	// 	return this;
	// }

	Vector2D add(double u, double v) {
		x += u;
		y += v;
		return this;
	}

	Vector2D add(Vector2D vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	Vector2D add(double s, Vector2D vec) {
		x += s * vec.x;
		y += s * vec.y;
		return this;
	}

	Vector2D rotateBy(double a) {
		double xx = x;
		double yy = y;
		x = xx * Math.cos(a) - yy * Math.sin(a);
		y = xx * Math.sin(a) + yy * Math.cos(a);
		return this;
	}

	Vector2D left() {
		double m = x;
		x = - y;
		y = m;
		return this;
	}
// turns 	Vector2Dtor left

// OUTPUT vecEC
	// Vector2D clone() {
	// 	return(new Vector2D(x,y));
	// }


// OUTPUT TEST MEASURE
	// double norm() {
	// 	return(sqrt(sq(x) + sq(y)));
	// }

	boolean isNull() {
		return((Math.abs(x) + Math.abs(y) < 0.000001));
	}

	double angle() {
		return(Math.atan2(y,x));
	}

	public String toString() {
		return "<" + x + ", " + y + ">";
	}

}
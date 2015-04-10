public class Vector2D {

	public float x = 0.0;
	public float y = 0.0;

// CREATE
	Vector2D () {
	}

	Vector2D (float px, float py) {
		x = px;
		y = py;
	}


// MODIFY
	Vector2D setTo(float px, float py) {
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
	Vector2D scaleBy(float u, float v) {
		x *= u;
		y *= v;
		return this;
	}

	Vector2D scaleBy(float f) {
		x *= f;
		y *= f;
		return this;
	}

	Vector2D reverse() {
		x = -x;
		y = -y;
		return this;
	}

	Vector2D divideBy(float f) {
		x /= f;
		y /= f;
		return this;
	}

	Vector2D normalize() {
		float n = sqrt(sq(x) + sq(y));
		if (n>0.000001) {
			x /= n;
			y /= n;
		}
		return this;
	}

	Vector2D add(float u, float v) {
		x += u;
		y += v;
		return this;
	}

	Vector2D add(Vector2D vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	Vector2D add(float s, Vector2D vec) {
		x += s * vec.x;
		y += s * vec.y;
		return this;
	}

	Vector2D rotateBy(float a) {
		float xx = x;
		float yy = y;
		x = xx * cos(a) - yy * sin(a);
		y = xx * sin(a) + yy * cos(a);
		return this;
	}

	Vector2D left() {
		float m = x;
		x = - y;
		y = m;
		return this;
	}
// turns 	Vector2Dtor left

// OUTPUT vecEC
	Vector2D clone() {
		return(new Vector2D(x,y));
	}


// OUTPUT TEST MEASURE
	float norm() {
		return(sqrt(sq(x) + sq(y)));
	}

	boolean isNull() {
		return((abs(x) + abs(y) < 0.000001));
	}

	float angle() {
		return(atan2(y,x));
	}

	String toString() {
		return "<" + x + ", " + y + ">";
	}

}
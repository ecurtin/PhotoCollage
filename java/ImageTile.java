import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.Random;

public class ImageTile {
	public BufferedImage image;
	public int originX;
	public int originY;
	public int destX;
	public int destY;
	public boolean show = true;
	public boolean emphasis = false;
	public double zoomFactor = 1.0;
	private Random random;

	public ImageTile(BufferedImage img, int originX, int originY) {
		image = img;
		this.originY = originY;
		this.originX = originX;
		random = new Random();
	}

	public boolean contains(int x, int y) {
		boolean ret = false;
		if( y > originY && y < image.getHeight() + originY 
			&&
			x > originX && x < image.getWidth() + originX) {
			ret = true;
		}
		return ret;
	}

	public void scatter(int maxDistance) {
		Vector2D vec = new Vector2D(0.0, (double) maxDistance);
		vec.scaleBy(random.nextDouble());
		vec.rotateBy(2 * Math.PI * random.nextDouble());
		int vecX = (int) Math.round(vec.x);
		int vecY = (int) Math.round(vec.y);

		this.destX = this.originX + vecX;
		this.destY = this.originY + vecY;
	}
}
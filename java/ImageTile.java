import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
	private AffineTransform affT;

	public ImageTile(BufferedImage img, int originX, int originY) {
		image = img;
		this.originY = originY;
		this.originX = originX;
		random = new Random();
		affT = new AffineTransform();
	}

	public ImageTile(BufferedImage img, int startX, int startY, int minScatter, int maxScatter) {
		image = img;
		random = new Random();
		affT = new AffineTransform();

		int x = -1;
		int y = -1;

		double distance = minScatter + (random.nextDouble() * (maxScatter - minScatter));
		Vector2D vec = new Vector2D(0.0, distance);
		vec.scaleBy(random.nextDouble());

		while(x < 0 || x > image.getWidth() || y < 0 || y > image.getHeight()) {
			vec.rotateBy(2 * Math.PI * random.nextDouble());
			int vecX = (int) Math.round(vec.x);
			int vecY = (int) Math.round(vec.y);
			x = startX + vecX;
			y = startY + vecY;
		}

		this.originX = x;
		this.originY = y;
	}

	public boolean contains(int x, int y) {
		boolean ret = false;
		if( y > originY && y < (image.getHeight() + originY) 
			&&
			x > originX && x < (image.getWidth() + originX)) {
			ret = true;
		}
		return ret;
	}

	public void scatter(int minScatter, int maxScatter) {
		double distance = minScatter + (random.nextDouble() * (maxScatter - minScatter));
		Vector2D vec = new Vector2D(0.0, distance);
		vec.scaleBy(random.nextDouble());
		vec.rotateBy(2 * Math.PI * random.nextDouble());
		int vecX = (int) Math.round(vec.x);
		int vecY = (int) Math.round(vec.y);

		this.destX = this.originX + vecX;
		this.destY = this.originY + vecY;
	}

	public void randomShow(double density) {
		double coin = random.nextDouble();
		if(coin >= (1 - density)) {
			this.show = true;
		}
		else {
			this.show = false;
		}
	}

	public void randomZoom(double minZoom, double maxZoom) {
		double zoom = minZoom + (random.nextDouble() * (maxZoom - minZoom));
		affT.scale(zoom, zoom);
	}

	public Graphics2D compositeTile(Graphics2D gBottom) {
		if(this.show == true) {
			AffineTransformOp affTOp = new AffineTransformOp(affT, AffineTransformOp.TYPE_BICUBIC);
			gBottom.drawImage(this.image, affTOp, this.destX, this.destY);
		}
		return gBottom;
	}

	public void emphasize(int ptX, int ptY, double maxZoom) {
		if(this.contains(ptX, ptY)) {
			this.show = true;
			this.emphasis = true;
			double zoom = 1 + (random.nextDouble() * (maxZoom -1));
			this.affT.scale(zoom, zoom);
			this.destX = this.originX;
			this.destY = this.originY;
		}
	}

	public void setDestinationCoordinates(int destX, int destY) {
		this.destX = destX;
		this.destY = destY;
	}


}
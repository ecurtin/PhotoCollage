import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.RescaleOp;

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

	public ImageTile(BufferedImage img, int startX, int startY, int tileWidth, int tileHeight, int minScatter, int maxScatter) {
		// System.out.println("Starting Method");
		// System.out.println("startX:    "+startX);
		// System.out.println("startY:    "+startY);
		// System.out.println("tileWidth: "+tileWidth);
		// System.out.println("tileHeight:"+tileHeight);
		// System.out.println("minScatter:"+minScatter);
		// System.out.println("maxScatter:"+maxScatter);
		random = new Random();
		affT = new AffineTransform();

		int x = 0;
		int y = 0;

		double distance = minScatter + (random.nextDouble() * (maxScatter - minScatter));
		Vector2D vec = new Vector2D(0.0, distance);
		vec.scaleBy(random.nextDouble());
		do{
			vec.rotateBy(2 * Math.PI * random.nextDouble());
			int vecX = (int) Math.round(vec.x);
			int vecY = (int) Math.round(vec.y);
			x = startX + vecX;
			y = startY + vecY;

		}while(
			x < 0 || 
			(x + tileWidth) > img.getWidth() || 
			y < 0 || 
			(y + tileHeight) > img.getHeight()
			);

		BufferedImage subImage = img.getSubimage(x, y, tileWidth, tileHeight);
		this.image = subImage;

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

	public void changeBrightness(float percentage) {
		RescaleOp op = new RescaleOp(percentage, 0, null);
    	this.image = op.filter(this.image, null);
	}

	public void randomBrightness() {
		float rand = random.nextFloat();
		float percentage = 0.96f + (rand * 0.6f);
		changeBrightness(percentage);
	}
}
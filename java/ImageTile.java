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

	public boolean contains(int x, int y) {
		boolean ret = false;
		if( y > originY && y < image.getHeight() + originY 
			&&
			x > originX && x < image.getWidth() + originX) {
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

	public void compositeTile(Graphics2D gBottom) {
		if(this.show == true) {
			AffineTransformOp affTOp = new AffineTransformOp(affT, AffineTransformOp.TYPE_BICUBIC);
			gBottom.drawImage(this.image, affTOp, this.destX, this.destY);
		}
	}

	public void emphasize(int ptX, int ptY) {
		if(array[i][j].contains(ptX, ptY)) {
			array[i][j].show = true;
			array[i][j].emphasis = true;
			zoom = 1 + (random.nextDouble() * (maxZoom -1));
			affT.scale(zoom, zoom);
			array[i][j].destX = array[i][j].originX;
			array[i][j].destY = array[i][j].originY;
		}
	}
}
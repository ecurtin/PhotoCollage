public class ImageTile {
	public BufferedImage image;
	public int originX;
	public int originY;
	public int destX;
	public int destY;
	public boolean show = true;
	public float zoomFactor = 1.0;

	public ImageTile(BufferedImage img, int originX, int originY) {
		image = img;
		this.originY = originY;
		this.originX = originX;
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
}
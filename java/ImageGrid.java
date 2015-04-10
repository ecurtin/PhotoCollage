import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class ImageGrid {
	BufferedImage mainImage;

	public ImageGrid(BufferedImage mainImage) {
		this.mainImage = mainImage;
	}

	public ImageTile[][] splitImage(int rows, int columns, int subImageWidth, int subImageHeight) {
		ImageTile[][] smallImages = new ImageTile[rows][columns];
		//int smallHeight = mainImage.getHeight() / rows;
		//int smallWidth = mainImage.getWidth() / columns;

		int heightDelta = mainImage.getHeight() / rows;
		int widthDelta = mainImage.getWidth() / cols;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
            	int originX = x * heightDelta;
            	int originY = y * widthDelta;
                smallImages[x][y] = new ImageTile(mainImage.getSubimage(originX, 
                														originY, 
                														subImageWidth, 
                														smallHeight),
                								  originX, 
                						 		  originY;
            }
        }

        return smallImages;
	}

	public ImageTile[][] randomScatter(ImageTile[][] array, int maxDistance) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				Vector2D vec = new Vector2D(0.0, (float) maxDistance);
				vec.scaleBy(Math.random.nextFloat());
				vec.rotateBy(2 * Math.PI * Math.random.nextFloat());
				int vecX = Math.round(vec.x);
				int vecY = Math.round(vec.y);

				array[i][j].destX = array[i][j].originX + vecX;
				array[i][j].destY = array[i][j].originY + vecY;
			}
		}
		return array;
	}

	public ImageTile[][] randomShow(ImageTile[][] array) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				float coin = Math.random.nextFloat();
				if(coin >= 0.5) {
					array[i][j].show = true;
				}
				else {
					array[i][j].show = false;
				}
			}
		}
		return array;
	}

	public ImageTile[][] assignRandomZoom(ImageTile[][] array, float maxZoom, float minZoom) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				  array[i][j].zoomFactor = minZoom + (Math.nextFloat() * (maxZoom - minZoom));
			}
		}
		return array;
	}

	public ImageTile[][] emphasizePoint(ImageTile[][] array, int emX, int emY, float maxZoom) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if(array[i][j].contains(emX, emY)) {
					array[i][j].show = true;
					array[i][j].zoomFactor = 1 + (Math.nextFloat() * (maxZoom -1));
				}
			}
		}
		return array;
	}

	public BufferedImage compositeTiles(BufferedImage bottom, ImageTile[][] tiles) {
		Graphics2D gBottom = bottom.createGraphics();
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if(array[i][j].contains(emX, emY)) {
					array[i][j].show = true;
					array[i][j].zoomFactor = 1 + (Math.nextFloat() * (maxZoom -1));
				}
			}
		}
		gBottom.dispose();
		return bottom;
	}

	public void writeOutArray(ImageTile[][] smallImages) {
		int count = 0;
		for (int x = 0; x < smallImages.length; x++) {
            for (int y = 0; y < smallImages[0].length; y++) {
                try {
                    ImageIO.write(smallImages[x][y].image, "png", new File("./output/tile-"
                            + (count++) + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	public BufferedImage compositeImage(BufferedImage bottom, BufferedImage top, int x, int y) {
		Graphics2D g = bottom.createGraphics();
		g.drawImage(top, x, y, null);
		g.dispose();
		return bottom;
	}
}
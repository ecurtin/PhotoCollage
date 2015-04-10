import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.Random;

public class ImageGrid {
	BufferedImage mainImage;
	Random random = new Random();

	public ImageGrid(BufferedImage mainImage) {
		this.mainImage = mainImage;
	}

	public void nullCheck(ImageTile[][] tiles) {
		for()
	}

	public ImageTile[][] splitImage(int rows, int columns, int subImageWidth, int subImageHeight) {
		int heightDelta = mainImage.getHeight() / rows;
		int widthDelta = mainImage.getWidth() / columns;

		// while((columns - 1) * widthDelta)

		ImageTile[][] smallImages = new ImageTile[rows][columns];
		//int smallHeight = mainImage.getHeight() / rows;
		//int smallWidth = mainImage.getWidth() / columns;



        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
            	int originX = x * heightDelta;
            	int originY = y * widthDelta;
            	if(originY + subImageHeight < mainImage.getHeight() &&
            	   originX + subImageWidth  < mainImage.getWidth() ) {
	                smallImages[y][x] = new ImageTile(mainImage.getSubimage(originX, 
														originY, 
														subImageWidth, 
														subImageHeight),
								  originX, 
						 		  originY);
            	}

            }
        }

        return smallImages;
	}

	public ImageTile[][] randomScatter(ImageTile[][] array, int maxDistance) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				Vector2D vec = new Vector2D(0.0, (double) maxDistance);
				vec.scaleBy(random.nextDouble());
				vec.rotateBy(2 * Math.PI * random.nextDouble());
				int vecX = (int) Math.round(vec.x);
				int vecY = (int) Math.round(vec.y);

				array[i][j].destX = array[i][j].originX + vecX;
				array[i][j].destY = array[i][j].originY + vecY;
			}
		}
		return array;
	}

	public ImageTile[][] randomShow(ImageTile[][] array) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				double coin = random.nextDouble();
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

	public ImageTile[][] assignrandomZoom(ImageTile[][] array, double maxZoom, double minZoom) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				  array[i][j].zoomFactor = minZoom + (random.nextDouble() * (maxZoom - minZoom));
			}
		}
		return array;
	}

	public ImageTile[][] emphasizePoint(ImageTile[][] array, int emX, int emY, double maxZoom) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if(array[i][j].contains(emX, emY)) {
					array[i][j].show = true;
					array[i][j].zoomFactor = 1 + (random.nextDouble() * (maxZoom -1));
				}
			}
		}
		return array;
	}

	public BufferedImage compositeTiles(BufferedImage bottom, ImageTile[][] tiles) {
		Graphics2D gBottom = bottom.createGraphics();
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				gBottom.drawImage(tiles[i][j].image, tiles[i][j].destX, tiles[i][j].destY, null);
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
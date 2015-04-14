import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class ImageGrid {
	BufferedImage mainImage;
	Random random = new Random();

	public ImageGrid(BufferedImage mainImage) {
		this.mainImage = mainImage;
	}

	public void nullCheck(ImageTile[][] tiles) {
		System.out.println("beginning null check");
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (tiles[i][j] == null) {
					System.out.println("Null: ["+i+"]["+j+"]\n");
				}
			}
		}
		System.out.println("Null check complete");
	}

	public ImageTile[][] splitImage(int rows, int columns, int subImageWidth, int subImageHeight) {
		int heightDelta = mainImage.getHeight() / rows;
		int widthDelta = mainImage.getWidth() / columns;

		while((((columns - 1) * widthDelta) + subImageWidth) > mainImage.getWidth()) {
			columns--;
			widthDelta = mainImage.getWidth() / columns;
			System.out.println("reducing columns");
		}

		while((((rows - 1) * heightDelta) + subImageHeight) > mainImage.getHeight()) {
			rows--;
			heightDelta = mainImage.getHeight() / rows;
			System.out.println("reducing rows");
		}

		ImageTile[][] smallImages = new ImageTile[rows][columns];
		//int smallHeight = mainImage.getHeight() / rows;
		//int smallWidth = mainImage.getWidth() / columns;
		// System.out.println("FINALLY: smallImages["+rows+"]["+columns+"]");


        for (int x = 0; x < smallImages.length; x++) {
            for (int y = 0; y < smallImages[0].length; y++) {
            	// System.out.println("smallImages["+x+"]["+y+"]");
            	int originY = x * heightDelta;
            	int originX = y * widthDelta;



            	// if(originY + subImageHeight < mainImage.getHeight() &&
            	//    originX + subImageWidth  < mainImage.getWidth() ) {
            	BufferedImage subImage = mainImage.getSubimage(originX, originY, subImageWidth, subImageHeight);
                smallImages[x][y] = new ImageTile(subImage, originX, originY);
            	// }

            }
        }
        nullCheck(smallImages);
        return smallImages;
	}

	public ImageTile[][] randomScatter(ImageTile[][] array, int maxDistance) {
		nullCheck(array);
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				array[i][j].scatter(maxDistance);
				// Vector2D vec = new Vector2D(0.0, (double) maxDistance);
				// vec.scaleBy(random.nextDouble());
				// vec.rotateBy(2 * Math.PI * random.nextDouble());
				// int vecX = (int) Math.round(vec.x);
				// int vecY = (int) Math.round(vec.y);

				// array[i][j].destX = array[i][j].originX + vecX;
				// array[i][j].destY = array[i][j].originY + vecY;
			}
		}
		return array;
	}

	public ImageTile[][] randomShow(ImageTile[][] array, double density) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
					array[i][j].randomShow(density);
			}
		}
		return array;
	}

	public ImageTile[][] assignRandomZoom(ImageTile[][] array, double maxZoom, double minZoom) {
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				  array[i][j].randomZoom(maxZoom, minZoom);
			}
		}
		return array;
	}

	public ImageTile[][] emphasizePoint(ImageTile[][] array, int emX, int emY, double maxZoom) {
		System.out.println("emphasizing Point: ("+emX +", "+emY+")");
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if(array[i][j].contains(emX, emY)) {
					array[i][j].show = true;
					array[i][j].emphasis = true;
					array[i][j].zoomFactor = 1 + (random.nextDouble() * (maxZoom -1));
					// array[i][j].zoomFactor = maxZoom;
					array[i][j].destX = array[i][j].originX;
					array[i][j].destY = array[i][j].originY;
				}
				// else if(array[i][j].emphasis == false) {
				// 	array[i][j].show = false;
				// }
			}
		}
		return array;
	}

	public BufferedImage compositeTilesNormal(BufferedImage bottom, ImageTile[][] tiles) {
		Graphics2D gBottom = bottom.createGraphics();
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				// if(tiles[i][j].show == true && tiles[i][j].emphasis == false) {
					
					
				// 	// affT.translate(tiles[i][j].destX, tiles[i][j].destY);

				// 	AffineTransformOp affTOp = new AffineTransformOp(affT, AffineTransformOp.TYPE_BICUBIC);

				// 	gBottom.drawImage(tiles[i][j].image, affTOp, tiles[i][j].destX, tiles[i][j].destY);
				// }

				tiles[i][j].compositeTile(gBottom);
			}
		}
		gBottom.dispose();
		return bottom;
	}

	public BufferedImage compositeTilesEmphasis(BufferedImage bottom, ImageTile[][] tiles) {
		Graphics2D gBottom = bottom.createGraphics();
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if(tiles[i][j].emphasis == true) {
					AffineTransform affT = new AffineTransform();
					affT.scale(tiles[i][j].zoomFactor, tiles[i][j].zoomFactor);
					// affT.translate(tiles[i][j].destX, tiles[i][j].destY);

					AffineTransformOp affTOp = new AffineTransformOp(affT, AffineTransformOp.TYPE_BILINEAR);

					gBottom.drawImage(tiles[i][j].image, affTOp, tiles[i][j].destX, tiles[i][j].destY);
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

	public void outputImage(BufferedImage image) {
	    try {
	        ImageIO.write(image, "png", new File("./output/HockneyCollage.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
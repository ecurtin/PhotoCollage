import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGrid {
	BufferedImage mainImage;

	public ImageGrid(String filename) {
		// read in image
		try {
            mainImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public BufferedImage[][] splitImage(int rows, int columns) {
		BufferedImage[][] smallImages = new BufferedImage[rows][columns];
		int smallHeight = mainImage.getHeight() / rows;
		int smallWidth = mainImage.getWidth() / columns;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                smallImages[x][y] = mainImage.getSubimage(x * smallWidth, y
                        * smallHeight, smallWidth, smallHeight);
            }
        }

        return smallImages;
	}

	public void writeOutArray(BufferedImage[][] smallImages) {
		int count = 0;
		for (int x = 0; x < smallImages.length; x++) {
            for (int y = 0; y < smallImages[0].length; y++) {
                try {
                    ImageIO.write(smallImages[x][y], "png", new File("./output/tile-"
                            + (count++) + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
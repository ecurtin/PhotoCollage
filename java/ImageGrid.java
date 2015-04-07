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
		int count = 0;
		int smallHeight = mainImage.getHeight() / rows;
		int smallWidth = mainImage.getWidth() / columns;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                smallImages[x][y] = mainImage.getSubimage(x * smallWidth, y
                        * smallHeight, smallWidth, smallHeight);
                try {
                    ImageIO.write(smallImages[x][y], "png", new File("tile-"
                            + (count++) + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return smallImages;
	}
}
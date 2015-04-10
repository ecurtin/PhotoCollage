import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HockneyCollage {
	public static void main(String[] args) {
		ImageGrid imageGrid;
		String filename;
		BufferedImage mainImage;

		// read in image
		try {
			filename = args[0];
			mainImage = ImageIO.read(new File(filename));
			imageGrid = new ImageGrid(mainImage);
			BufferedImage[][] grid = imageGrid.splitImage(5, 5);
			imageGrid.writeOutArray(grid);
        } catch (IOException e) {
            e.printStackTrace();
        }
		


	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
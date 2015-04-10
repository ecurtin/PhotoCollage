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
			ImageTile[][] grid = imageGrid.splitImage(100, 100, mainImage.getWidth()/50, mainImage.getHeight()/50);
			BufferedImage base = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			//imageGrid.writeOutArray(grid);
			imageGrid.randomScatter(grid, 20);
			imageGrid.compositeTiles(base, grid);
        } catch (IOException e) {
            e.printStackTrace();
        }
		


	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
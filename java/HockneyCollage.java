import java.awt.image.BufferedImage;

public class HockneyCollage {
	public static void main(String[] args) {
		ImageGrid imageGrid;
		String filename;
		
		filename = args[0];
		imageGrid = new ImageGrid(filename);
		BufferedImage[][] grid = imageGrid.splitImage(5, 5);
		imageGrid.writeOutArray(grid);
	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
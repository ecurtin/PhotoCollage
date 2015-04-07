public class HockneyCollage {
	public static void main(String[] args) {
		ImageGrid imageGrid;
		String filename;
		
		filename = args[0];
		imageGrid = new ImageGrid(filename);
		imageGrid.splitImage(5, 5);
	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
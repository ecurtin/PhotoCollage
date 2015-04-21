import java.awt.image.BufferedImage;
import java.util.Random;

/*
THIS CLASS SHOULD BE DEPRECATED
All the functionality in splitImage() should get transferred to ImageTile 
or taken out to the main method in HockneyCollage.
*/


public class ImageGrid {
	BufferedImage mainImage;
	Random random = new Random();

	public ImageGrid(BufferedImage mainImage) {
		this.mainImage = mainImage;
	}

	public ImageTile[][] splitImage(int rows, int columns, int subImageWidth, int subImageHeight) {
		int heightDelta = mainImage.getHeight() / rows;
		int widthDelta = mainImage.getWidth() / columns;

		while((((columns - 1) * widthDelta) + subImageWidth) > mainImage.getWidth()) {
			columns--;
			widthDelta = mainImage.getWidth() / columns;
			//System.out.println("reducing columns");
		}

		while((((rows - 1) * heightDelta) + subImageHeight) > mainImage.getHeight()) {
			rows--;
			heightDelta = mainImage.getHeight() / rows;
			//System.out.println("reducing rows");
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
        return smallImages;
	}
}
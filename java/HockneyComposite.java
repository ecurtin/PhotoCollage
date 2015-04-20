import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Color;

public class HockneyComposite {
	public static void main(String[] args) {
		String filename;
		BufferedImage mainImage = null;
		BufferedImage base = null;
		Random random = new Random();
		ImageTile[][] tiles;
			
		int baseR;
		int baseG;
		int baseB;
		int rows;
		int columns;
		int borderWidth;
		int maxScatter;
		int minScatter;
		double maxZoom;
		double minZoom;
		double density;
		int[] emphasisPoints;

		System.out.println("ARGS:");
			for(int i = 0; i < args.length; i++) {
				System.out.println("args["+ i +"] = " + args[i]);
			}
		System.out.println("\n");
		System.out.println("VARIABLES\n");

		filename = args[0];
		baseR = Integer.parseInt(args[1]);
		baseG = Integer.parseInt(args[2]);
		baseB = Integer.parseInt(args[3]);
		rows = Integer.parseInt(args[4]);
		columns = Integer.parseInt(args[5]);
		borderWidth = Integer.parseInt(args[6]);
		maxScatter = Integer.parseInt(args[7]);
		minScatter = Integer.parseInt(args[8]);
		maxZoom = Double.parseDouble(args[9]);
		minZoom = Double.parseDouble(args[10]);
		
		emphasisPoints = new int[args.length - 11];
		for(int i = 11; i < args.length; i++) {
			emphasisPoints[i - 11] = Integer.parseInt(args[i]); 
		}
		//System.out.println("emphasisPoints.lenght: "+emphasisPoints.length);

		System.out.println("filename     : "+ filename);
		System.out.println("border color : ("+baseR+", "+baseG+", "+baseB+")");
		System.out.println("rows:        : "+ rows);
		System.out.println("columns      : "+ columns);
		System.out.println("maxScatter   : "+ maxScatter);
		System.out.println("minScatter   : "+ minScatter);
		System.out.println("maxZoom      : "+ maxZoom);
		System.out.println("minZoom      : "+ minZoom);
		
		for(int i = 0; i < emphasisPoints.length; i = i+2) {
			//System.out.println("i: "+i);
			System.out.println("emphasisPt   : ("+ emphasisPoints[i] + ", "+ emphasisPoints[i+1] +")");
		}

		tiles = new ImageTile[rows][columns];

		try {
			mainImage = ImageIO.read(new File(filename));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		int tileWidth = mainImage.getWidth() / columns;
		int tileHeight = mainImage.getHeight() / rows;			

		int baseWidth = (columns * tileWidth) + ((columns + 1) * borderWidth);
		int baseHeight = (rows * tileHeight) + ((rows + 1) * borderWidth);

		base = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = base.createGraphics();
		g.setPaint(new Color(baseR, baseG, baseB));
		g.fillRect(0, 0, base.getWidth(), base.getHeight());

		int x = 0;
		int y = 0;
		int destX;
		int destY;

		for(int i  = 0; i < tiles.length; i++) {
			destY = borderWidth + (tileHeight + borderWidth) * i;
			y = tileHeight * i;

			for(int j = 0; j < tiles[0].length; j++) {
				destX = borderWidth + (tileWidth + borderWidth) * j;
				x = tileWidth * j;

				tiles[i][j] = new ImageTile(mainImage, x, y, minScatter, maxScatter);
				tiles[i][j].setDestinationCoordinates(destX, destY);
			}

		}

		Graphics2D mainImageGraphics = mainImage.createGraphics();
		for(int i  = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				mainImageGraphics = tiles[i][j].compositeTile(mainImageGraphics);
			}
		}
		mainImageGraphics.dispose();

		
	}
}
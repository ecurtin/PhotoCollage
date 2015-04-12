import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Color;

public class HockneyCollage {
	public static void main(String[] args) {
		ImageGrid imageGrid = null;
		String filename;
		BufferedImage mainImage = null;
		BufferedImage base = null;
		Random random = new Random();
		ImageTile[][][] tileLayers;
			
		int layers;
		int baseR;
		int baseG;
		int baseB;
		int maxTileWidth;
		int minTileWidth;
		int maxTileHeight;
		int minTileHeight;
		int maxScatter;
		int minScatter;
		double maxZoom;
		double minZoom;
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
		layers = Integer.parseInt(args[4]);
		maxTileWidth = Integer.parseInt(args[5]);
		minTileWidth = Integer.parseInt(args[6]);
		maxTileHeight = Integer.parseInt(args[7]);
		minTileHeight = Integer.parseInt(args[8]);
		maxScatter = Integer.parseInt(args[9]);
		minScatter = Integer.parseInt(args[10]);
		maxZoom = Double.parseDouble(args[11]);
		minZoom = Double.parseDouble(args[12]);
		
		emphasisPoints = new int[args.length - 13];
		for(int i = 13; i < args.length; i++) {
			emphasisPoints[i - 13] = Integer.parseInt(args[i]); 
		}
		//System.out.println("emphasisPoints.lenght: "+emphasisPoints.length);

		System.out.println("filename     : "+ filename);
		System.out.println("layers       : "+ layers);
		System.out.println("maxTileWidth : "+ maxTileWidth);
		System.out.println("minTileWidth : "+ minTileWidth);
		System.out.println("maxTileHeight: "+ maxTileHeight);
		System.out.println("minTileHeight: "+ minTileHeight);
		System.out.println("maxScatter   : "+ maxScatter);
		System.out.println("minScatter   : "+ minScatter);
		System.out.println("maxZoom      : "+ maxZoom);
		System.out.println("minZoom      : "+ minZoom);
		
		for(int i = 0; i < emphasisPoints.length; i = i+2) {
			//System.out.println("i: "+i);
			System.out.println("emphasisPt   : ("+ emphasisPoints[i] + ", "+ emphasisPoints[i+1] +")");
		}

		tileLayers = new ImageTile[layers][][];
		for(int i = 0; i < layers; i++) {
			tileLayers[i] = null;
		}
		
		try {
			mainImage = ImageIO.read(new File(filename));			
			imageGrid = new ImageGrid(mainImage);
			base = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = base.createGraphics();
			g.setPaint(new Color(baseR, baseG, baseB));
			g.fillRect(0, 0, base.getWidth(), base.getHeight());


			int widthDelta = (maxTileWidth - minTileWidth) / layers;
			int heightDelta = (maxTileHeight - minTileHeight) / layers;

			for(int layer = 0; layer < layers; layer++) {
				int thisLayersTileWidth = minTileWidth + (layer * widthDelta);
				int thisLayersTileHeight = minTileHeight + (layer * heightDelta);
				int thisLayersColumns = (mainImage.getWidth() / thisLayersTileWidth) * 2;
				int thisLayersRows = (mainImage.getHeight() / thisLayersTileHeight) * 2;
				//tileLayers[layer] = new ImageTile[thisLayersRows][thisLayersColumns];
				tileLayers[layer] = imageGrid.splitImage(thisLayersRows, thisLayersColumns, thisLayersTileWidth, thisLayersTileHeight);
				tileLayers[layer] = imageGrid.randomScatter(tileLayers[layer], (int) Math.round((minScatter + (random.nextDouble() * (maxScatter - minScatter)))));
				tileLayers[layer] = imageGrid.randomShow(tileLayers[layer]);
				// tileLayers[layer] = imageGrid.assignRandomZoom(tileLayers[layer], maxZoom, minZoom);
				for(int i = 0; i < emphasisPoints.length; i = i + 2) {
					tileLayers[layer] = imageGrid.emphasizePoint(tileLayers[layer], emphasisPoints[i], emphasisPoints[i + 1], maxZoom);
				}
			}


			//ImageTile[][] grid = imageGrid.splitImage(20, 20, mainImage.getWidth()/10, mainImage.getHeight()/10);
			// imageGrid.nullCheck(grid);
			//imageGrid.writeOutArray(grid);
			//imageGrid.randomScatter(grid, 10);
			for(int i = 0; i < tileLayers.length; i++) {
				base = imageGrid.compositeTiles(base, tileLayers[i]);
			}

			imageGrid.outputImage(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
		


	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
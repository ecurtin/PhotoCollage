import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Color;

/*
TODO: GET RID OF THE IMAGEGRID DEPENDENCIES
*/


public class HockneyCollage {
	public static void main(String[] args) {
		ImageGrid imageGrid = null;
		String filename;
		String outfile;
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
		double density;
		int[] emphasisPoints;

		System.out.println("ARGS:");
			for(int i = 0; i < args.length; i++) {
				System.out.println("args["+ i +"] = " + args[i]);
			}
		System.out.println("\n");
		System.out.println("VARIABLES\n");

		filename = args[0];
		outfile = args[1];
		baseR = Integer.parseInt(args[2]);
		baseG = Integer.parseInt(args[3]);
		baseB = Integer.parseInt(args[4]);
		layers = Integer.parseInt(args[5]);
		maxTileWidth = Integer.parseInt(args[6]);
		minTileWidth = Integer.parseInt(args[7]);
		maxTileHeight = Integer.parseInt(args[8]);
		minTileHeight = Integer.parseInt(args[9]);
		maxScatter = Integer.parseInt(args[10]);
		minScatter = Integer.parseInt(args[11]);
		maxZoom = Double.parseDouble(args[12]);
		minZoom = Double.parseDouble(args[13]);
		density = Double.parseDouble(args[14]);
		
		emphasisPoints = new int[args.length - 15];
		for(int i = 15; i < args.length; i++) {
			emphasisPoints[i - 15] = Integer.parseInt(args[i]); 
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
			System.out.println("emphasisPt   : ("+ emphasisPoints[i] + ", "+ emphasisPoints[i+1] +")");
		}

		tileLayers = new ImageTile[layers][][];
		for(int i = 0; i < layers; i++) {
			tileLayers[i] = null;
		}
	
		try{		
			mainImage = ImageIO.read(new File(filename));			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		imageGrid = new ImageGrid(mainImage);
		base = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = base.createGraphics();
		g.setPaint(new Color(baseR, baseG, baseB));
		g.fillRect(0, 0, base.getWidth(), base.getHeight());


		int widthDelta = (maxTileWidth - minTileWidth) / layers;
		int heightDelta = (maxTileHeight - minTileHeight) / layers;

		for(int layer = 0; layer < tileLayers.length; layer++) {
			int thisLayersTileWidth = minTileWidth + (layer * widthDelta);
			int thisLayersTileHeight = minTileHeight + (layer * heightDelta);
			int thisLayersColumns = (mainImage.getWidth() / thisLayersTileWidth) * 2;
			int thisLayersRows = (mainImage.getHeight() / thisLayersTileHeight) * 2;
			//tileLayers[layer] = new ImageTile[thisLayersRows][thisLayersColumns];
			tileLayers[layer] = imageGrid.splitImage(thisLayersRows, thisLayersColumns, thisLayersTileWidth, thisLayersTileHeight);
			
			for(int i = 0; i < tileLayers[0].length; i++) {
				for(int j = 0; j < tileLayers[0][0].length; j++){
					tileLayers[layer][i][j].scatter(minScatter, maxScatter);
					tileLayers[layer][i][j].randomShow(density);
					tileLayers[layer][i][j].randomZoom(minZoom, maxZoom);
					for(int k = 0; k < emphasisPoints.length; k = k + 2) {
						tileLayers[layer][i][j].emphasize(emphasisPoints[k], emphasisPoints[k + 1], maxZoom);
					}
					if(!tileLayers[layer][i][j].emphasis) {
						tileLayers[layer][i][j].compositeTile(g);
					}

				}
					
			}

		}
		//put emphasis layers on top
		for(int layer = 0; layer < tileLayers.length; layer++) {
			for(int i = 0; i < tileLayers[0].length; i++) {
				for(int j = 0; j < tileLayers[0][0].length; j++){
					if(tileLayers[layer][i][j].emphasis) {
						tileLayers[layer][i][j].compositeTile(g);
					}
				}
			}
		}


		g.dispose();

		try {
	        ImageIO.write(base, "png", new File(outfile));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }


	}

	// public void initializeObjects() {
	// 	imageGrid = new ImageGrid();
	// }
}
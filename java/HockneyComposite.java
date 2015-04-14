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
		int columns
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
		maxScatter = Integer.parseInt(args[6]);
		minScatter = Integer.parseInt(args[7]);
		maxZoom = Double.parseDouble(args[8]);
		minZoom = Double.parseDouble(args[9]);
		
		emphasisPoints = new int[args.length - 10];
		for(int i = 10; i < args.length; i++) {
			emphasisPoints[i - 10] = Integer.parseInt(args[i]); 
		}
		//System.out.println("emphasisPoints.lenght: "+emphasisPoints.length);

		System.out.println("filename     : "+ filename);
		System.out.println("border color : ("+baseR+", "+baseG+", "+baseB+")")
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
	}
}
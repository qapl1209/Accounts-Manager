package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.MainPanel;

public class GraphicsTools {

	public static AlphaComposite makeComposite(double alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, (float) alpha));
	}
	
	public static void drawCenteredString(int x, int y, Graphics g, Font f, String s, Color c) {
		int width = GraphicsTools.calculateTextWidth(s, f);
		g.setFont(f);
		g.setColor(c);
		g.drawString(s, x - width / 2, y - f.getSize() / 2);
	}
	
	public static int calculateTextWidth(String text, Font font) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(font);
		return fm.stringWidth(text);
	}
	public static int calculateCenteredX(String text, int center, Font font){
		return center - calculateTextWidth(text, font)/2;
	}

	public static void enableTextAntialiasing(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	//ty MadProgrammer
	public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();

        return rotated;
    }
	
	//combines two images
	//useful when combining images that are usually drawn together
	public static BufferedImage combineImages(BufferedImage a, BufferedImage b) {

		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(a.getWidth(), b.getWidth());
		int h = Math.max(a.getHeight(), b.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(a, 0, 0, null);
		g.drawImage(b, 0, 0, null);

		g.dispose();

		return combined;
	}
	
	//make a copy of an image
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	//darkening an image according to a float value
	public static BufferedImage darkenImage(double d, BufferedImage b) {
		BufferedImage output = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) output.getGraphics();
		
		g2d.drawImage(b, 0, 0, null);
		
		g2d.setComposite(makeComposite(1d - d));
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, b.getWidth(), b.getHeight());
		
	    return output;
	}
	
	//loads image from filepath
	//filepath is relative to this class
	public static BufferedImage loadImage(String filepath) {
		BufferedImage img = null;
		InputStream is;
		
		System.out.print("LOADING IMAGE: " + filepath);
		
		try {
			
			is = GraphicsTools.class.getResourceAsStream(filepath);
			img = ImageIO.read(is);
			
			System.out.println(" SUCCESS");
			
		} catch(IOException e) {
			System.out.println(" FAILED");
		}
		
		return img;
	}
	
	//loads images from spritesheet
	//goes from top left to bottom right, going horizontally first
	public static ArrayList<BufferedImage> loadAnimation(String filepath, int width, int height){
		
		ArrayList<BufferedImage> animation = new ArrayList<BufferedImage>();
		
		BufferedImage animationPng = GraphicsTools.loadImage(filepath);
		
		int spritesheetHeight = animationPng.getHeight();
		int spritesheetWidth = animationPng.getWidth();
		
		for(int i = 0; i < spritesheetHeight / height; i++) {
			for(int j = 0; j < spritesheetWidth / width; j++) {
				BufferedImage next = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = next.getGraphics();
				g.drawImage(animationPng, -(j * width), -(i * height), null);
				animation.add(next);
			}
			
		}
		
		return animation;
		
	}
}

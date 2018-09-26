package mario;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Personnage implements Valeurs{
	
	public int posX;
	public int posY;
	
	public int hauteurPerso;
	public int longueurPerso;
	
	
	public BufferedImage img;
	
	public Personnage() {
		this.posX = 0;
		this.posY = 0;
		
		try {
			img = ImageIO.read(new File("C:\\Users\\Dapawan\\eclipse-workspace\\IA\\src\\mario\\images\\perso_base.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.hauteurPerso = img.getHeight();
		this.longueurPerso = img.getWidth();
		
	}

}

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
	
	public Boolean isJumping;
	
	
	public BufferedImage img[][] = new BufferedImage[2][9];
	
	public Personnage() {
		this.posX = 0;
		this.posY = 0;
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		for(int a = 0; a <= 1; a ++)
		{
			for(int i = 1; i <= 8; i++)
			{
				try {
					img[a][i] = ImageIO.read(new File("C:\\Users\\Dapawan\\eclipse-workspace\\IA\\src\\mario\\images\\perso_" + imgStr[a] + i + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.hauteurPerso = img[0][1].getHeight();
		this.longueurPerso = img[0][1].getWidth();
		
	}
	
	public Personnage(Bloc bloc) {
		this.posX = bloc.posX;
		this.posY = bloc.posY - jumpY;
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		for(int a = 0; a <= 1; a ++)
		{
			for(int i = 1; i <= 8; i++)
			{
				try {
					img[a][i] = ImageIO.read(new File("C:\\Users\\Dapawan\\eclipse-workspace\\IA\\src\\mario\\images\\perso_" + imgStr[a] + i + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.hauteurPerso = img[0][1].getHeight();
		this.longueurPerso = img[0][1].getWidth();
		
	}

}

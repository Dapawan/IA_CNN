package mario;

import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Personnage implements Valeurs{
	
	public int posX;
	public int posY;
	
	public int hauteurPerso;
	public int longueurPerso;
	
	public Boolean isJumping;
	
	public int score;
	public boolean vie;
	
	
	public BufferedImage img[][] = new BufferedImage[2][9];
	
	public Personnage() {
		
		this.score = 0;
		this.vie = true;
		
		this.posX = 0;
		this.posY = 0;
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		for(int a = 0; a <= 1; a ++)
		{
			for(int i = 1; i <= 8; i++)
			{
				try {
					img[a][i] = ImageIO.read(new File("" + path + "images\\perso_" + imgStr[a] + i + ".png"));
				
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
				
		this.vie = true;
		
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		for(int a = 0; a <= 1; a ++)
		{
			for(int i = 1; i <= 8; i++)
			{
				try {
					img[a][i] = ImageIO.read(new File("" + path + "images\\perso_" + imgStr[a] + i + ".png"));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.hauteurPerso = img[0][1].getHeight();
		this.longueurPerso = img[0][1].getWidth();
		
		this.posX = bloc.posX;
		this.posY = bloc.posY - this.hauteurPerso - 2;
		
	}
	
	public void resetPosInit(Map map)
	{
		this.vie = true;
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		this.posX = map.listeBloc.get(0).posX;
		this.posY = map.listeBloc.get(0).posY - this.hauteurPerso - 2;;
	}

}

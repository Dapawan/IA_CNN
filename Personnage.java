package mario;

import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

import javax.imageio.ImageIO;

public class Personnage implements Valeurs,Comparator<Personnage>{
	
	public int posX;
	public int posY;
	
	public int hauteurPerso;
	public int longueurPerso;
	
	public Boolean isJumping;
	
	public int score;
	public int scoreOld;
	public Chrono chrono;
	public long timeOutSec;
	
	public boolean vie;
	
	//Ajout de son IA
	public CoucheNeuronale coucheNeuronale;
	
	public Direction direction;
	public Direction directionOld;
	
	public int compteurImg = 0;
	public int compteur = 0;
	
	public Gravite gravite;
	
	
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
	
	public Personnage(Bloc bloc,Map map) {
				
		if(isPlusieurIa == true)
		{
			this.coucheNeuronale = new CoucheNeuronale();
			this.chrono = new Chrono();
		}
		
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
		this.posY = bloc.posY - this.hauteurPerso - 10;
		
	}
	
	public void resetPosInit(Map map)
	{
		this.vie = true;
		//Évite de pouvoir sauter au début
		this.isJumping = true;
		
		this.posX = map.listeBloc.get(0).posX;
		this.posY = map.listeBloc.get(0).posY - this.hauteurPerso - 10;
	}

	@Override
	public int compare(Personnage o1, Personnage o2) {
		// TODO Auto-generated method stub
		return (o2.score - o1.score);
	}

}

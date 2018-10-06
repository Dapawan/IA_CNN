package mario;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map implements Valeurs{

	
	public boolean stopGame;
	public ArrayList<Bloc> listeBloc;
	public Personnage perso;
	
	private Gravite gravite;
	public int posXRelativeFenetre;
	
	public BufferedImage backgroundImg;
	
	
	public Map() {
		
		this.stopGame = false;
		//Init de la pos relative
		this.posXRelativeFenetre = 0;		
		
		//Init de l'img background
		/*
		try {
			this.backgroundImg = ImageIO.read(new File("" + path + backgroundStr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Bloc bloc_ = new Bloc(-1, -1);
		Bloc bloc_temp = bloc_;
		
		
		listeBloc = new ArrayList<>();
		int posY = hauteurFenetre - bloc_.hauteur - 100;
		
		int toUp = 0;
		
		for(int x = 0; x < longueurLevel; x+= bloc_.longueur)
		{
			if( (toUp == 1) && (posY > limitePalierUp) )
			{
				posY -= palierUp;
			}
			else if( (posY < (limitePalierDown - (2*Bloc.hauteurBloc)) ) )
			{
				posY += palierUp;
			}
			
			toUp = (int)(Math.random()*2);
			bloc_temp = new Bloc(x,posY);
			listeBloc.add(bloc_temp);
			
			//Ne crée qu'1 fois l'instance
			if(perso == null)
			{
				perso = new Personnage(bloc_temp);
			}
		}
		
		//Ajout du drapeau
		listeBloc.add(new Bloc((bloc_temp.posX + (bloc_temp.longueur / 2)),posY,true));
		
		//listeBloc.add(new Bloc(500,500));
		
		gravite = new Gravite(this);
		
		gravite.start();
		
	}
	
	//La synchro évite l'accès à plusieurs thread en même temps
	public synchronized void move(int x, int y)
	{
		if( (collision(x, y) == false) && (x >= 0) && (y >= 0) )
		{
			this.perso.posX = x;
			this.perso.posY = y;
			
			//Le score est la distance parcourue
			this.perso.score = x;
			
			//On bloque le déplacement à droite à ce seuil
			if(x >= stopMvGauche)
			{
				this.posXRelativeFenetre = stopMvGauche;
			}
			else
			{
				this.posXRelativeFenetre = x;
			}
		}
		
	}
	
	public boolean collision(int NewposX, int NewposY)
	{
		boolean result = false;
		Bloc latestBloc = null;
		for(Bloc bloc : listeBloc)
		{
			//Collision en bas à droite
			if( ( (( (NewposX + perso.longueurPerso) >= bloc.posX) && (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur) ) && 
					(((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) ) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				result = true;
			}
			//Collision en bas à gauche
			else if( ((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				result = true;
			}
			//Collision en haut à gauche
			else if(((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				result = true;
			}
			
			//Collision en haut à droite
			else if( ( (NewposX + perso.longueurPerso) >= bloc.posX) && ( (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				result = true;
			}
			if(result == true)
			{
				latestBloc = bloc;
				break;
			}
			
		}
		
		if( (result == true) && (latestBloc != null))
		{
			//On vérifie que c'est le flag
			if(latestBloc.isFlag == true)
			{
				//Stop game
				System.out.println("STOP GAME");
				this.stopGame = true;
			}
		}
		
		return result;
	}
	
	
}

package mario;

import java.util.ArrayList;

public class Map implements Valeurs{

	public ArrayList<Bloc> listeBloc;
	public Personnage perso;
	
	private Gravite gravite;
	public int posXRelativeFenetre;
	
	
	public Map() {
		
		
		//Init de la pos relative
		this.posXRelativeFenetre = 0;		
		
		
		Bloc bloc_ = new Bloc(-1, -1);
		Bloc bloc_temp;
		
		
		listeBloc = new ArrayList<>();
		int posY = hauteurFenetre - bloc_.hauteur - 100;
		
		int toUp = 0;
		
		for(int x = 0; x < longueurLevel; x+= bloc_.longueur)
		{
			if( (toUp == 1) && (posY > limitePalierUp) )
			{
				posY -= palierUp;
			}
			else if( (posY < limitePalierDown) )
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
		
		listeBloc.add(new Bloc(500,500));
		
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
		for(Bloc bloc : listeBloc)
		{
			//Collision en bas à droite
			if( ( (( (NewposX + perso.longueurPerso) >= bloc.posX) && (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur) ) && 
					(((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) ) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				return true;
			}
			//Collision en bas à gauche
			else if( ((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				return true;
			}
			//Collision en haut à gauche
			else if(((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				return true;
			}
			
			//Collision en haut à droite
			else if( ( (NewposX + perso.longueurPerso) >= bloc.posX) && ( (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	
}

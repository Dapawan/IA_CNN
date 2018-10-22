package mario;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map implements Valeurs{

	
	public boolean stopGame;
	public ArrayList<Bloc> listeBloc;
	//public ArrayList<Bloc> listeBlocTrieX;
	
	public Personnage perso;
	
	public ArrayList<Personnage> persoListe;
	
	public Intervalle intervalle = new Intervalle();
	public Intervalle intervalleTemp = new Intervalle();
	
	private Gravite gravite;
	public int posXRelativeFenetre;
	
	public BufferedImage backgroundImg;
	public int compteurGeneration;
	
	@SuppressWarnings("unused")
	public Map() {
		
		this.stopGame = false;
		//Init de la pos relative
		this.posXRelativeFenetre = 0;		
		
		//Init de l'img background
		
		try {
			this.backgroundImg = ImageIO.read(new File("" + path + backgroundStr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			
			//Ne cr�e qu'1 fois l'instance
			if(isPlusieurIa == false)
			{
				if(perso == null)
				{
					perso = new Personnage(bloc_temp,this);
				}
			}
			else
			{
				//On cr�e le nbr de perso par IA au m�me endroit
				if(persoListe == null) 
				{
					persoListe = new ArrayList<>();
					for(int i = 0; i < nbrIA; i++)
					{
						persoListe.add(new Personnage(bloc_temp, this));
					}
				}
			}
			
			
		}
		//On peut tout les 2 bloc faire un vide
		int isVide = 0;
		for(int i = 1; i < (listeBloc.size() - 1); i +=1)
		{
			isVide = (int)(Math.random() * 2);
			if(isVide == 1)
			{
				listeBloc.remove(i);
			}
		}
		
		//Ajout du drapeau
		listeBloc.add(new Bloc((bloc_temp.posX + (bloc_temp.longueur / 2)),posY,true));
		
		//listeBloc.add(new Bloc(500,500));

		/*gravite = new Gravite(this);
		
		gravite.start();*/
		
		
	
		
	}
	
	//La synchro �vite l'acc�s � plusieurs thread en m�me temps
		public synchronized boolean move(int x, int y,Personnage perso,Direction direction)
		{
			boolean result = false;
			if( (collision(x, y,perso,direction) == false) && (x >= 0) && (y >= 0) && (perso.vie == true))
			{
				perso.posX = x;
				perso.posY = y;
				
				//Le score est la distance parcourue
				perso.score = x;
				
				//On bloque le d�placement � droite � ce seuil
				if(x >= stopMvGauche)
				{
					this.posXRelativeFenetre = stopMvGauche;
				}
				else
				{
					this.posXRelativeFenetre = x;
				}
				
				/*
				 * D�placement r�ussit
				 */
				result = true;
			}
			if( (y >= hauteurFenetre) && (perso.vie == true))
			{
				perso.vie = false;
			}
			return result;
			
		}
	
	//La synchro �vite l'acc�s � plusieurs thread en m�me temps
	public synchronized void move(int x, int y)
	{
		if( (collision(x, y) == false) && (x >= 0) && (y >= 0) && (perso.vie == true))
		{
			this.perso.posX = x;
			this.perso.posY = y;
			
			//Le score est la distance parcourue
			this.perso.score = x;
			
			//On bloque le d�placement � droite � ce seuil
			if(x >= stopMvGauche)
			{
				this.posXRelativeFenetre = stopMvGauche;
			}
			else
			{
				this.posXRelativeFenetre = x;
			}
		}
		if( (y >= hauteurFenetre) && (perso.vie == true))
		{
			perso.vie = false;
		}
		
	}
	//La synchro �vite l'acc�s � plusieurs thread en m�me temps
	public synchronized void move(int x, int y, Personnage perso)
	{
		if( (collision(x, y,perso) == false) && (x >= 0) && (y >= 0) && (perso.vie == true))
		{
			perso.posX = x;
			perso.posY = y;
			
			//Le score est la distance parcourue
			perso.score = x;
			
			//On bloque le d�placement � droite � ce seuil
			if(x >= stopMvGauche)
			{
				this.posXRelativeFenetre = stopMvGauche;
			}
			else
			{
				this.posXRelativeFenetre = x;
			}
		}
		if( (y >= hauteurFenetre) && (perso.vie == true))
		{
			perso.vie = false;
		}
		
	}
		
	public boolean collision(int NewposX, int NewposY)
	{
		boolean result = false;
		Bloc latestBloc = null;
		for(Bloc bloc : listeBloc)
		{
			
			//Collision en bas � droite
			if( ( (( (NewposX + perso.longueurPerso) >= bloc.posX) && (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur) ) && 
					(((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) ) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				result = true;
			}
			//Collision en bas � gauche
			else if( ((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				//On touche le sol
				perso.isJumping = false;
				
				result = true;
			}
			//Collision en haut � gauche
			else if(((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				result = true;
			}
			
			//Collision en haut � droite
			else if( ( (NewposX + perso.longueurPerso) >= bloc.posX) && ( (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur))  && 
					((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
			{
				result = true;
			}
			if(result == true)
			{
				if((perso.isJumping == false) && (NewposY > bloc.posY))
				{
					perso.isJumping = true;
				}
				latestBloc = bloc;
				break;
			}
			
		}
		
		if( (result == true) && (latestBloc != null))
		{
			//On v�rifie que c'est le flag
			if(latestBloc.isFlag == true)
			{
				//Stop game
				System.out.println("STOP GAME");
				this.stopGame = true;
			}
		}
		
		
		
		return result;
	}
	
	
	
	/*
	 * On ne test que les blocs proches en x (r�duire le temps de boucle)
	 */

	public boolean collision(int NewposX, int NewposY, Personnage perso, Direction direction)
	{
		boolean result = false;
		Bloc latestBloc = null;

		int posXOld = perso.posX;
		int posYOld = perso.posY;
		
		//perso.directionOld = direction;
		
		for(Bloc bloc : listeBloc)
		{
			
			if( NewposX >= (bloc.posX - perso.longueurPerso) )
			{
				switch(direction)
				{
				case DOWN:
					//On regarde si collision sous les pieds
					intervalle.intervalleDebut = bloc.posX;
					intervalle.intervalleFin = bloc.posX + bloc.longueur;
					
					intervalleTemp.intervalleDebut = NewposX;
					intervalleTemp.intervalleFin = NewposX + perso.longueurPerso;
					
					
					if(intervalle.isIn(intervalleTemp) == true)
					{
						intervalle.intervalleDebut = bloc.posY;
						intervalle.intervalleFin = bloc.posY + bloc.hauteur;
						if( intervalle.isIn(NewposY + perso.hauteurPerso) == true)
						{
							perso.isJumping = false;
							if(perso.directionOld != direction)
							{
								//System.out.println("DOWN");
							}
							result = true;
							return result;
						}
					}
					
						break;
				case INIT:
					break;
				case LEFT:
					//On regarde si collision � gauche
					intervalle.intervalleDebut = bloc.posX;
					intervalle.intervalleFin = bloc.posX + bloc.longueur;
					
					intervalleTemp.intervalleDebut = NewposX;
					intervalleTemp.intervalleFin = NewposX + perso.longueurPerso;
					if(intervalle.isIn(intervalleTemp) == true)
					{
						intervalle.intervalleDebut = bloc.posY;
						intervalle.intervalleFin = bloc.posY + bloc.hauteur;
						
						intervalleTemp.intervalleDebut = NewposY;
						intervalleTemp.intervalleFin = NewposY + perso.hauteurPerso;
						if(intervalle.isIn(intervalleTemp) == true)
						{
							if(perso.directionOld != direction)
							{
								//System.out.println("LEFT");
							}
							result = true;
							return result;
						}
						
					}
					
					break;
				case RIGHT:
					//On regarde si collision � gauche
					intervalle.intervalleDebut = bloc.posX;
					intervalle.intervalleFin = bloc.posX + bloc.longueur;
					
					intervalleTemp.intervalleDebut = NewposX;
					intervalleTemp.intervalleFin = NewposX + perso.longueurPerso;
					if(intervalle.isIn(intervalleTemp) == true)
					{
						intervalle.intervalleDebut = bloc.posY;
						intervalle.intervalleFin = bloc.posY + bloc.hauteur;
						
						intervalleTemp.intervalleDebut = NewposY;
						intervalleTemp.intervalleFin = NewposY + perso.hauteurPerso;
						if(intervalle.isIn(intervalleTemp) == true)
						{
							if(perso.directionOld != direction)
							{
								//System.out.println("RIGHT");
							}
							result = true;
							return result;
						}
						
					}
					break;
				case UP:
					break;
				default:
					break;
				
				}
			}
		}
		
		
		
		return result;
		
	}
	
	
	/*
	 * On ne test que les blocs proches en x (r�duire le temps de boucle)
	 */
	public boolean collision(int NewposX, int NewposY, Personnage perso)
	{
		boolean result = false;
		Bloc latestBloc = null;
		for(Bloc bloc : listeBloc)
		{
			if( NewposX >= (bloc.posX - perso.longueurPerso)  && (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur) )
			{
				//Collision en bas � droite
				if( ( (( (NewposX + perso.longueurPerso) >= bloc.posX) && (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur) ) && 
						(((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) ) ) )
				{
					//On touche le sol
					perso.isJumping = false;
					
					result = true;
				}
				//Collision en bas � gauche
				else if( ((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
						((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
				{
					//On touche le sol
					perso.isJumping = false;
					
					result = true;
				}
				//Collision en haut � gauche
				else if(((NewposX) >= bloc.posX) && ((NewposX) <= (bloc.posX + bloc.longueur))  && 
						((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
				{
					result = true;
				}
				
				//Collision en haut � droite
				else if( ( (NewposX + perso.longueurPerso) >= bloc.posX) && ( (NewposX + perso.longueurPerso) <= (bloc.posX + bloc.longueur))  && 
						((NewposY) >= (bloc.posY)) && ( (NewposY) <= (bloc.posY + bloc.hauteur) ) )
				{
					result = true;
				}
				if(result == true)
				{
					if((perso.isJumping == false) && (NewposY > bloc.posY))
					{
						perso.isJumping = true;
					}
					latestBloc = bloc;
					break;
				}
			}
			
		}
		
		if( (result == true) && (latestBloc != null))
		{
			//On v�rifie que c'est le flag
			if(latestBloc.isFlag == true)
			{
				//Stop game
				System.out.println("STOP GAME");
				this.stopGame = true;
			}
		}
				
		return result;
	}
	
	public int nbrPersoAlive()
	{
		int result = 0;
		for(Personnage perso : persoListe)
		{
			if(perso.vie == true)
			{
				result++;
			}
		}
		return result;
	}
	
}

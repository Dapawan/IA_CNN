package mario;

import java.util.ArrayList;

public class Map implements Valeurs{

	public ArrayList<Bloc> listeBloc;
	public Personnage perso;
	
	private Gravite gravite;
	
	public Map() {
		
		Bloc bloc_ = new Bloc(-1, -1);
		
		perso = new Personnage();
		
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
			
			listeBloc.add(new Bloc(x,posY));
		}
		
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
		}
		
	}
	
	public boolean collision(int NewposX, int NewposY)
	{
		for(Bloc bloc : listeBloc)
		{
			if( ((NewposX >= bloc.posX) && ( (NewposX) <= (bloc.posX + bloc.longueur) ) ) &&
					(((NewposY + perso.hauteurPerso) >= bloc.posY) && ( (NewposY ) <= (bloc.posY + bloc.hauteur) ) ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	
}

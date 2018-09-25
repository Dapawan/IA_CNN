package mario;

import java.util.ArrayList;

public class Map implements Valeurs{

	public ArrayList<Bloc> listeBloc;
	public Personnage perso;
	
	private Gravite gravite;
	
	public Map() {
		
		
		perso = new Personnage();
		
		listeBloc = new ArrayList<>();
		int posY = hauteurFenetre - hauteurBloc;
		
		for(int x = 0; x < longueurLevel; x+= longueurBloc)
		{
			
			listeBloc.add(new Bloc(x,posY));
		}
		
		gravite = new Gravite(this);
		
		gravite.start();
		
	}
	
	//La synchro évite l'accès à plusieurs thread en même temps
	public synchronized void move(int x, int y)
	{
		if( collision(x, y) == false )
		{
			this.perso.posX = x;
			this.perso.posY = y;
		}
		
	}
	
	public boolean collision(int NewposX, int NewposY)
	{
		for(Bloc bloc : listeBloc)
		{
			if( ((NewposX >= bloc.posX) &&  ( (NewposX + longueurPerso) <= (bloc.posX + bloc.longueur) ) ) &&
					(((NewposY + hauteurPerso) >= bloc.posY) && ( (NewposY + hauteurPerso) <= (bloc.posY + bloc.hauteur) ) ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	
}

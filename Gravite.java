package mario;

public class Gravite extends Thread implements Valeurs{

	private Map map;
	
	private int compteur;
	
	
	public Gravite(Map map) {
		this.map = map;
		this.compteur = 0;
		//this.compteurMort = 0;
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		//Boucle du thread
		while(!isInterrupted())
		{
			if(isPlusieurIa == false)
			{
				
				if( (map.perso.isJumping == true) && (this.compteur <= compteurMax))
				{
					this.compteur++;
					map.move( map.perso.posX, (int)(map.perso.posY - 2) );
				}
				else if(map.perso.isJumping == false)
				{
					this.compteur = 0;
				}
				
				if(map.perso.vie == false)
				{
					map.perso.resetPosInit(map);
				}
				
				map.move( map.perso.posX, (int)(map.perso.posY + 1) );
			}
			else
			{
				int posX = 0;
				int posY = 0;
				/*
				if(this.compteurMort == nbrIA)
				{
					//On reset tous les IA
					for(int i = 0; i < nbrIA; i++)
					{
						perso = map.persoListe.get(i);
						
						perso.resetPosInit(map);
					}
				}
				else
				{*/
				for(Personnage perso : map.persoListe)
					
					if(perso.vie == true)
					{
						posX = perso.posX;
						posY = perso.posY;
						
						
						if( (perso.isJumping == true) && (perso.compteur <= compteurMax))
						{
							perso.compteur++;
							map.move( posX, (posY - 2) ,perso);
						}
						else if(perso.isJumping == false)
						{
							perso.compteur = 0;
						}
						
						posX = perso.posX;
						posY = perso.posY;
						
						map.move(posX, (posY + 1),perso);
					}
						
					
				
				
			}
			try {
				this.sleep((int)g);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
	}
}

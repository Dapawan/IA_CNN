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
			try {
				this.sleep((int)g*2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
	}
}

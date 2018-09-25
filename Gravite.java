package mario;

public class Gravite extends Thread implements Valeurs{

	private Map map;
	
	
	public Gravite(Map map) {
		this.map = map;
	}
	
	@Override
	public void run() {
		//Boucle du thread
		while(!isInterrupted())
		{
			map.move( map.perso.posX, (int)(map.perso.posY + g) );
			
			try {
				this.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

package Chess;

public class main implements Valeurs{
	
	private static Fenetre fenetre;
	private static Partie partie;
	private static GestionIA gestionIA;

	public static void main(String[] args) throws InterruptedException {

		
		partie = new Partie();
		gestionIA = new GestionIA();
		fenetre = new Fenetre(partie,gestionIA);
		

		while(true)
		{
			if(nbrUser > 0)
			{
				partie.start();
			}
			else
			{
				gestionIA.calcul();
			}
			
			if(nbrUser > 0)
			{
				Thread.sleep(100);
			}
			else
			{
				Thread.sleep(gestionIA.timeToWait);
			}
			
			
			fenetre.repaint();
		}
		

	}

}

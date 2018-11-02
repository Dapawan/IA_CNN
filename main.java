package Chess;

public class main {
	
	private static Fenetre fenetre;
	private static Partie partie;

	public static void main(String[] args) throws InterruptedException {

		
		partie = new Partie();
		fenetre = new Fenetre(partie);
		
		ReseauNeuronale reseau = new ReseauNeuronale();
		
		double entree[] = {1,1};
		double sortie[];
		
		sortie = reseau.result(entree);
		
		while(true)
		{
			partie.start();
			Thread.sleep(10);
			
			
			fenetre.repaint();
		}
		

	}

}

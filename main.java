package Chess;

public class main {
	
	private static Fenetre fenetre;

	public static void main(String[] args) throws InterruptedException {

		fenetre = new Fenetre();
		
		
		ReseauNeuronale reseau = new ReseauNeuronale();
		
		double entree[] = {1,1};
		double sortie[];
		
		sortie = reseau.result(entree);
		
		while(true)
		{
			Thread.sleep(10);
			
			
			fenetre.repaint();
		}
		

	}

}

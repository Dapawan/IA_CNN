package mario;

public class main implements Valeurs{

	private static Fenetre_ fenetre;
	
	private static GestionIA gestionIA;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		
		fenetre = new Fenetre_();
		
		if(isIa == true)
		{
			gestionIA = new GestionIA(fenetre);
		}
		
		while(fenetre.map.stopGame == false)
		{
			if(isIa == true)
			{
				gestionIA.start();
			}
			else
			{
				fenetre.gestionDeplacementClavier();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//fenetre.gestionDeplacementClavier();
			fenetre.repaint();
		}
		
		
		//GAME FINISHED
		if(isIa == true) 
		{
			System.out.println("****Résultats*****");
			System.out.println("");
			System.out.println(gestionIA.resultat.CoucheNeuronaleListe.get(0).toString());
		}
		
		
	}

}
